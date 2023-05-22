package communications;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * A class to manage a server that listens for incoming connections on a given port and 
 * delegates them to a ConnectionController.
 * 
 * @author Miquel A. Fuster
 */
class ServerManager implements Runnable {

	/** The logger for this class. */
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(ServerManager.class.getClass().getName());
	
	/** A flag to indicate if the server is currently running. */
	private boolean alive;
	
	/** The ConnectionController that will handle incoming connections. */
	private ConnectionController controller;
	
	/** The ServerSocket that the server will listen on for incoming connections. */
	private ServerSocket serverSocket;

	/**
	 * Constructs a ServerManager with a given ConnectionController and server port.
	 * 
	 * @param controller the ConnectionController that will handle incoming connections
	 * @param serverPort the port that the server will listen on for incoming connections
	 * @throws IOException if an I/O error occurs when opening the server socket
	 */
	public ServerManager(ConnectionController controller, int serverPort) throws IOException {
		serverSocket = new ServerSocket(serverPort);
		this.controller = controller;
		alive = true;
	}

	/**
	 * The main loop that listens for incoming connections and delegates them to the 
	 * ConnectionController.
	 */
	public void run() {
		try {
			while (alive) {
				Socket socket = serverSocket.accept();
				controller.addConnection(socket);
			}
		} catch (IOException e) {
			LOGGER.warning("Ocurrió en la conexión del servidor con un cliente.");
		}
	}

	/**
	 * Stops the server and closes the ServerSocket.
	 */
	void stop() {
		alive = false;
		try {
			serverSocket.close();
		} catch (IOException e) {
			// Fallo en el cierre del socket. No hacer nada, el GC se encargará de él. 
		}
	}

}
