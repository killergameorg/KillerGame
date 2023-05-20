package clients.asteroids.screen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import clients.asteroids.messages.AccelerateShipMessage;
import clients.asteroids.messages.IdScreenMessage;
import clients.asteroids.messages.IdShipMessage;
import clients.asteroids.messages.RequestShipIdMessage;
import clients.asteroids.messages.RotateShipMessage;
import clients.asteroids.messages.ShipScreenChangeMessage;
import communications.ConnectionController;
import communications.P2PCommListener;

public class ScreenController implements P2PCommListener{

	private ConnectionController comm;
	private ScreenViewer viewer;
	private String[] neighbour;
	

	private int id;
	private int team;

	public ScreenController() {
		neighbour = new String[4];

		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File("asteroids_screen.properties")));
			id = Integer.parseInt(properties.getProperty("id"));
			team = Integer.parseInt(properties.getProperty("team"));
			int msRefresh = Integer.parseInt(properties.getProperty("ms_refresh"));
			viewer = new ScreenViewer(this, msRefresh);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

	private void addNeighbour(String ip, int peerId) {
		int neighbourIndex;

		if (peerId < id - 1) {
			neighbourIndex = Edge.UP.ordinal();
		} else if (peerId == id + 1) {
			neighbourIndex = Edge.RIGHT.ordinal();
		} else if (peerId > id + 1) {
			neighbourIndex = Edge.DOWN.ordinal();
		} else {
			neighbourIndex = Edge.LEFT.ordinal();
		}

		neighbour[neighbourIndex] = ip;
	}

	public boolean downHasWall() {
		return neighbour[Edge.DOWN.ordinal()] == null;
	}

	public boolean leftHasWall() {
		return neighbour[Edge.LEFT.ordinal()] == null;
	}

	@Override
	public void onConnectionClosed(String ip) {
		removeNeighbour(ip);
	}

	@Override
	public void onConnectionLost(String ip) {
		removeNeighbour(ip);
	}

	@Override
	public void onIncomingMessage(String ip, Object message) {
		if (message instanceof ShipScreenChangeMessage) {
			var m = (ShipScreenChangeMessage) message;
			var from = m.from;
			var ship = m.ship;
			double x = ship.x;
			double y = ship.y;
			switch (from) {
			case UP:
				y = 0;
				break;
			case RIGHT:
				x = viewer.getWidth();
				break;
			case DOWN:
				y = viewer.getHeight();
				break;
			case LEFT:
				x = 0;
				break;
			}
			viewer.createShip(ship.id, x, y, ship.dx, ship.dy, ship.da, ship.getColor());
		} else if (message instanceof AccelerateShipMessage) {
			var m = (AccelerateShipMessage) message;
			Optional<Ship> ship = viewer.getShip(m.shipId);
			if(ship.isPresent()) {
				ship.get().doAcceleration(m.accelerate);
			}
		} else if (message instanceof RotateShipMessage) {
			var m = (RotateShipMessage) message;
			Optional<Ship> ship = viewer.getShip(m.shipId);
			if(ship.isPresent()) {
				switch(m.rotation) {
				case -1:
					ship.get().rotateCounterClockWise();
					break;
				case 0:
					ship.get().stopRotation();
					break;
				case 1:
					ship.get().rotateClockWise();
					break;
				}
			}
		} else if (message instanceof RequestShipIdMessage) {
			var m = (RequestShipIdMessage) message;
			if(m.team == team) {
				var petitionaryId = m.petitionaryId;
				var shipId = viewer.getNewShip();
				var idShipMessage = new IdShipMessage();
				idShipMessage.petitonaryId = petitionaryId;
				idShipMessage.shipId = shipId;
				comm.sendFlood(idShipMessage);
			}
		} else if (message instanceof IdScreenMessage) {
			IdScreenMessage m = (IdScreenMessage) message;
			int peerId = m.id;
			addNeighbour(ip, peerId);
		}
	}

	@Override
	public void onNewConnection(String ip) {
		var idScreenMessage = new IdScreenMessage();
		idScreenMessage.id = id;
		comm.sendPrivate(ip, idScreenMessage);
	}

	private void removeNeighbour(String ip) {
		for (var i = 0; i < neighbour.length; ++i) {
			if (neighbour[i] != null && neighbour[i].equals(ip)) {
				neighbour[i] = null;
				break;
			}
		}
	}

	public boolean rightHasWall() {
		return neighbour[Edge.RIGHT.ordinal()] == null;
	}

	public void sendShipControlMessage(Object message) {
		comm.sendFlood(message);
	}

	public void setComm(ConnectionController comm) {
		this.comm = comm;
	}

	public void shipOutOfLimits(Ship ship, Edge edge) {
		var ip = neighbour[edge.ordinal()];
		var message = new ShipScreenChangeMessage();
		message.ship = ship;
		switch (edge) {
		case UP:
			message.from = Edge.DOWN;
			break;
		case RIGHT:
			message.from = Edge.LEFT;
			break;
		case DOWN:
			message.from = Edge.UP;
			break;
		case LEFT:
			message.from = Edge.RIGHT;
			break;
		}
		comm.sendPrivate(ip, message);
	}
	
	public boolean upHasWall() {
		return neighbour[Edge.UP.ordinal()] == null;
	}

}
