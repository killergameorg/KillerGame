package communications;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A class to manage a server that listens for incoming connections on a given port and 
 * delegates them to a ConnectionController.
 * 
 * @author Miquel A. Fuster
 */
class ServerManager implements Runnable {

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
			e.printStackTrace();
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
		}
	}

}
