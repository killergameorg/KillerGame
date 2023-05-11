package clients.asteroids.messages;

import java.io.Serializable;

import clients.asteroids.Edge;
import clients.asteroids.Ship;

public class ShipMessage implements Serializable {

	private static final long serialVersionUID = 2872167710749645863L;

	public Edge from;
	public Ship ship;
	
}
