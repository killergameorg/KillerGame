package communications;

import java.io.IOException;
import java.io.ObjectInputStream;

import communications.frames.Frame;

/**
 * ConnectionManager class that implements the Runnable interface.
 * This class manages the connection with a remote peer and receives the incoming Frames.
 * It handles the received frames according to the frame type and sends them to the ConnectionController.
 * 
 * @author Miquel A. Fuster
 */
class ConnectionManager implements Runnable {

	/** Flag to indicate if the ConnectionManager is active. */
	private boolean active;
	
	/** The ConnectionController object that manages the connections. */
	private ConnectionController controller;
	
	/** The ObjectInputStream to read incoming Frames. */
	private ObjectInputStream in;
	
	/** The local IP address. */
	private String localIp;
	
	/** The remote IP address. */
	private String remoteIp;

	/**
     * Constructor for ConnectionManager class.
     * 
     * @param controller The ConnectionController object that manages the connections.
     * @param localIp The local IP address.
     * @param remoteIp The remote IP address.
     * @param in The ObjectInputStream to read incoming Frames.
     */
	public ConnectionManager(ConnectionController controller, String localIp, String remoteIp, ObjectInputStream in) {
		this.localIp = localIp;
		this.remoteIp = remoteIp;
		this.in = in;
		this.controller = controller;
		active = true;
	}

	/**
     * Handles the incoming Frame and sends it to the ConnectionController.
     * 
     * @param frame The incoming Frame.
     */
	private void handleFrame(Frame frame) {
		if (controller.controlPeerMessageId(frame.getSourceIp(), frame.getId())) {
			switch (frame.getFrameType()) {
			case MESSAGE:
				// El paquete es nuestro. Lo matamos
				if (frame.getSourceIp().equals(localIp))
					return;
				// El paquete va para nosotros o es un flood. Enviar el payload y la remoteIp de
				// origen al controlador para tratarlo.
				if (frame.getTargetIp().equals(localIp) || frame.getTargetIp().equals("*")) {
					controller.pushMessage(frame.getSourceIp(), frame.getPayload());
				}
				// Reenviar si no es para nosotros
				if (!frame.getTargetIp().equals(localIp)) {
					controller.send(frame);
				}
				break;
			case PING:
				controller.responsePing(frame.getSourceIp());
				break;
			case PING_ACK:
				break;
			case CLOSE:
				controller.closePeer(frame.getSourceIp());
				break;
			default:
				break;
			}
		}
	}

	/**
     * The method that is executed when the ConnectionManager is started as a thread.
     */
	@Override
	public void run() {
		try {
			while (active) {
				Object objetoRecibido = in.readObject();
				if (objetoRecibido instanceof Frame) {
					Frame frame = (Frame) objetoRecibido;
					handleFrame(frame);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			controller.killConnection(remoteIp, false);
			active = false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
     * Stops the ConnectionManager thread and closes the ObjectInputStream.
     */
	void stop() {
		active = false;
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
