package clients.asteroids.messages;

import java.io.Serializable;

import clients.asteroids.screen.Edge;
import clients.asteroids.screen.Ship;

public class ShipScreenChangeMessage implements Serializable {

	private static final long serialVersionUID = 2872167710749645863L;

	public Edge from;
	public Ship ship;
	
}
