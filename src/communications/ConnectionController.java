package communications;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import communications.frames.CloseFrame;
import communications.frames.Frame;
import communications.frames.MessageFrame;
import communications.frames.PingAckFrame;

/**
 * The `ConnectionController` class is responsible for managing network connections with peers in a peer-to-peer network.
 * 
 * @author Miquel A. Fuster
 */
public class ConnectionController {

	/**
     * This private record is used to represent a connection with another peer.
     */
	private record ConnectionData(Socket socket, ObjectInputStream inStream, ObjectOutputStream outStream) {
		@Override
		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}

			if (!(other instanceof ConnectionData)) {
				return false;
			}

			return this.socket.getInetAddress().getHostAddress()
					.equals(((ConnectionData) other).socket.getInetAddress().getHostAddress());
		}
	}

	/** The logger for this class. */
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(ConnectionController.class.getClass().getName());

	/** The name of the properties file containing the configuration for this class. */
	private static final String FILE_PROPERTIES = "connections.properties";
	
	/** The port on which the server listens for incoming connections. */
	private int serverPort;

	/** The listener that receives incoming messages and connection status notifications. */
	private P2PCommListener commListener;
	
	/** A map of connected peers and their connection data. */
	private Map<String, ConnectionData> connectedPeers;
	
	/** A map of the last message ID received from each peer. */
	private Map<String, Long> peerMessageControl;
	
	/** A list of IP addresses to reconnect to if the connection is lost. */
	private List<String> reconnectionPeers;
	
	/** The local IP address of this node. */
	private String localIp;

	/** The next message ID to assign to an outgoing message. */
	private long messageId;

	/**
     * Initializes a new instance of the `ConnectionController` class.
     */
	public ConnectionController() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(FILE_PROPERTIES)));

			serverPort = Integer.valueOf(properties.getProperty("server_port"));
			reconnectionPeers = new ArrayList<>();
			
			String peersIp = properties.getProperty("peers");
			if (peersIp != null) {
				for (String ip : peersIp.split(",")) {
					reconnectionPeers.add(ip);
				}
			}

			connectedPeers = new Hashtable<>();
			peerMessageControl = new Hashtable<>();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
     * Adds a new connection with the specified socket to the list of connected peers.
     * 
     * @param socket The socket to add the connection for.
     * @throws IOException If an I/O error occurs while adding the connection.
     */
	void addConnection(Socket socket) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

		ConnectionData connection = new ConnectionData(socket, in, out);
		String remoteIp = socket.getInetAddress().getHostAddress();
		if (!connectedPeers.containsKey(remoteIp)) {
			setLocalIp(socket.getLocalAddress().getHostAddress());
			connectedPeers.put(remoteIp, connection);
			// Inicia un hilo para recibir objetos entrantes del cliente destino
			Thread hilo = new Thread(new ConnectionManager(this, getLocalIp(), remoteIp, in));
			hilo.start();
			if (commListener != null)
				commListener.onNewConnection(socket.getInetAddress().getHostAddress());
		} else {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
	}

	/**
     * Closes the connection with the peer at the specified IP address.
     * 
     * @param ip The IP address of the peer to close the connection with.
     */
	void closePeer(String ip) {
		killConnection(ip, true);
	}

	/**
     * Attempts to establish a connection with the peer at the specified IP address.
     * 
     * @param ip The IP address of the peer to connect to.
     * @throws IOException If an I/O error occurs while attempting to connect.
     */
	private void connect(String ip) throws IOException {
		addConnection(new Socket(ip, serverPort));
	}

	/**
     * Checks whether a message with the specified ID has already been received from the peer at the specified IP address.
     * 
     * @param ip The IP address of the peer to check the message ID for.
     * @param messageId The message ID to check.
     * @return `true` if the message ID is new, `false` otherwise.
     */
	boolean controlPeerMessageId(String ip, long messageId) {
		if (!peerMessageControl.containsKey(ip) || peerMessageControl.get(ip) < messageId) {
			peerMessageControl.put(ip, messageId);
			return true;
		}
		return false;
	}

	/**
     * Generates a new message frame to be sent to the peer at the specified IP address.
     * 
     * @param ip The IP address of the peer to generate the message frame for.
     * @return The new message frame.
     */
	private Frame generateMessageFrame(String ip) {
		Frame frame = new MessageFrame();
		frame.setHeader(getMessageId(), getLocalIp(), ip);
		return frame;
	}


	/**
	 * Returns the local IP address used for this connection.
	 * @return The local IP address.
	 */
	private String getLocalIp() {
		return localIp;
	}

	/**
	 * 	Returns a new message ID and increments the message counter.
	 * 	@return The new message ID.
	 */
	private synchronized long getMessageId() {
		return messageId++;
	}

	/**
	 * 	Initializes the ConnectionController by starting the server thread and the reconnection thread.
	 */
	public void initialize() {
		Thread hiloServidor;
		try {
			hiloServidor = new Thread(new ServerManager(this, serverPort));
			hiloServidor.start();
		} catch (IOException e) {
			LOGGER.warning("[FATAL] No se puede crear el socket de servidor. La aplicación se va a cerrar.");
			e.printStackTrace();
			System.exit(-1);
		}

		Thread hiloReconector = new Thread(() -> {
			while (true) {
				if (reconnectionPeers.size() > connectedPeers.size()) {
					reconnectionPeers.forEach(ip -> {
						try {
							if (!connectedPeers.containsKey(ip)) {
								connect(ip);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		});
		hiloReconector.start();
	}

	/**
	 * Closes the connection with the specified IP address.
	 * @param ip The IP address of the connection to close.
	 */
	void killConnection(String ip, boolean intentional) {
		if (connectedPeers.containsKey(ip)) {
			ConnectionData peer = connectedPeers.get(ip);
			try {
				peer.socket.close();
			} catch (IOException e) {
			}
			connectedPeers.remove(ip);
			resetPeerMessageId(ip);
		}
		if (commListener != null) {
			if (intentional) {
				reconnectionPeers.remove(ip);
				commListener.onConnectionClosed(ip);
			} else {
				commListener.onConnectionLost(ip);
			}
		}
	}

	/**
	 * Pushes a message received from a peer to the communication listener.
	 * @param sourceIp The IP address of the peer that sent the message.@
	 * param payload The message payload.
	 */
	void pushMessage(String sourceIp, Object payload) {
		if (commListener != null)
			commListener.onIncomingMessage(sourceIp, payload);
	}

	/**
	 * Resets the message ID counter for the specified IP address.
	 * @param ip The IP address to reset the message ID counter for.
	 */
	void resetPeerMessageId(String ip) {
		if (peerMessageControl.containsKey(ip)) {
			peerMessageControl.put(ip, -1L);
		}
	}

	/**
	 * Sends a response to a ping message.
	 * @param peerIp The IP address of the peer that sent the ping.
	 */
	void responsePing(String peerIp) {
		Frame response = new PingAckFrame();
		response.setHeader(getMessageId(), getLocalIp(), peerIp);
		send(response);
	}

	/**
	 * Sends a message to the specified IP address.
	 * @param frame The message to send.
	 */
	void send(Frame frame) {
		String ip = frame.getTargetIp();
		if (connectedPeers.containsKey(ip)) {
			try {
				sendMessage(connectedPeers.get(ip), frame);
			} catch (IOException e) {
				e.printStackTrace();
				killConnection(ip, false);
			}
		} else {
			connectedPeers.forEach((ipPeer, conn) -> {
				try {
					sendMessage(conn, frame);
				} catch (IOException e) {
					e.printStackTrace();
					killConnection(ipPeer, false);
				}
			});
		}
	}

	/**
	 * Sends a disconnection advise to the peer.
	 * Creates a CloseFrame and sets its header with the message id, local IP and "*" as the remote IP. Then sends it.
	 */
	public void sendDisconnectionAdvise() {
		Frame frame = new CloseFrame();
		frame.setHeader(getMessageId(), getLocalIp(), "*");
		send(frame);
	}

	/**
	 * Sends a flood message to all connected peers.
	 * Generates a message frame with "*" as the remote IP, sets its payload to the given message and sends it.
	 * @param message the message to send
	 */
	public void sendFlood(Object message) {
		Frame frame = generateMessageFrame("*");
		frame.setPayload(message);
		send(frame);
	}

	/**
	 * Sends a message to a specific peer.
	 * Writes the given frame object to the ObjectOutputStream of the given connection.
	 * @param connection the connection to send the message through
	 * @param frame the frame to send
	 * @throws IOException if there is an error writing the frame to the ObjectOutputStream
	 */
	private void sendMessage(ConnectionData connection, Frame frame) throws IOException {
		ObjectOutputStream out = connection.outStream;
		out.writeObject(frame);
		out.flush();
	}

	/**
	 * Sends a private message to a specific peer.
	 * Generates a message frame with the given IP as the remote IP, sets its payload to the given message and sends it.
	 * @param ip the IP address of the destination peer
	 * @param message the message to send
	 */
	public void sendPrivate(String ip, Object message) {
		Frame frame = generateMessageFrame(ip);
		frame.setPayload(message);
		send(frame);
	}

	/**
	 * Sets the P2P communication listener for this node and notifies it of all currently connected peers.
	 * @param commListener the listener to set
	 */
	public void setCommListener(P2PCommListener commListener) {
		this.commListener = commListener;
		connectedPeers.forEach((peerIp, connection) -> {
			commListener.onNewConnection(peerIp);
		});
	}

	/**
	 * Sets the local IP address used for this connection.
	 * @param localIp The local IP address to set.
	 */
	private void setLocalIp(String ip) {
		if (localIp == null || !localIp.equals(ip)) {
			localIp = ip;
		}
	}

}
