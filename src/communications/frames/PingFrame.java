package communications.frames;

/**
 * Represents a ping frame that extends the {@link Frame} class. The
 * {@code PingFrame} class represents a specific type of frame that is used to
 * check the availability of a network device. It inherits from the
 * {@code Frame} class and provides a specialized implementation for the
 * {@link FrameType#PING} type. Instances of this class are created using the
 * default constructor, which sets the frame type to {@link FrameType#PING}.
 * 
 * @see Frame
 * @author Miquel A. Fuster
 */
public class PingFrame extends Frame {

	/**
	 * The serialVersionUID for version control of serialized objects.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code PingFrame} instance with the frame type set to
	 * {@link FrameType#PING}.
	 */
	public PingFrame() {
		super(FrameType.PING);
		super.setPayload("");
	}

	/**
	 * There is no payload for a ping frame, so this method does nothing.
	 * 
	 * @param payload ignored payload
	 */
	public void setPayload(String payload) {
		// Theres no need of payload in a Ping
	}

}
