package communications.frames;

/**
 * The FrameType enum represents the type of frame that can be sent over the
 * network. The available types are PING, PING_ACK, and MESSAGE.
 * 
 * @author Miquel A. Fuster
 */
public enum FrameType {
	 /**
     * Represents a PING frame used for network connectivity checking.
     */
    PING,
    
    /**
     * Represents a PING_ACK frame used as a response to a PING frame.
     */
    PING_ACK,
    
    /**
     * Represents a CLOSE frame used for closing a connection.
     */
    CLOSE,
    
    /**
     * Represents a MESSAGE frame used for exchanging messages.
     */
    MESSAGE
}
