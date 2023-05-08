package communications.frames;

/**
 * Represents a message frame that extends the {@link Frame} class. The {@code CloseFrame}
 * class represents a specific type of frame that can be used to signal the close of a
 * network connection between devices. It inherits from the {@code Frame} class and provides
 * a specialized implementation for the {@link FrameType#CLOSE} type.
 * Instances of this class are created using the default constructor, which sets the frame
 * type to {@link FrameType#CLOSE} and sets the payload to an empty string.
 * 
 * @see Frame
 * @author Miquel A. Fuster
*/
public class CloseFrame extends Frame {

	private static final long serialVersionUID = 1916770907617445458L;

	/**
     * Constructs a new CloseFrame with no payload.
     */
	public CloseFrame() {
		super(FrameType.CLOSE);
		super.setPayload("");
	}

	/**
	 * There is no payload for a ping acknowledgement frame, so this method does
	 * nothing.
	 * 
	 * @param payload ignored payload
	 */
	public void setPayload(String payload) {
		// Theres no need of payload in a Ping
	}

}
