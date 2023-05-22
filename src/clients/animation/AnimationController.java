package clients.animation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import clients.animation.messages.BallMessage;
import clients.animation.messages.IdMessage;
import communications.ConnectionController;
import communications.P2PCommListener;

public class AnimationController implements P2PCommListener {

	private int id;

	private ConnectionController comm;
	private AnimationViewer viewer;
	private String[] neighbour;

	public AnimationController() {
		neighbour = new String[4];

		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File("animation.properties")));
			int numBalls = Integer.parseInt(properties.getProperty("num_balls"));
			id = Integer.parseInt(properties.getProperty("id"));
			int msRefresh = Integer.parseInt(properties.getProperty("ms_refresh"));
			int ballMaxSpeed = Integer.parseInt(properties.getProperty("ball_max_speed"));
			viewer = new AnimationViewer(this, numBalls, ballMaxSpeed, msRefresh);
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

	public void ballOutOfLimits(Ball ball, Edge edge) {
		String ip = neighbour[edge.ordinal()];
		BallMessage message = new BallMessage();

		message.ball = ball;
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
		if (message instanceof BallMessage) {
			BallMessage ballMessage = (BallMessage) message;

			Edge from = ballMessage.from;
			Ball ball = ballMessage.ball;

			int x = ball.getX();
			int y = ball.getY();

			switch (from) {
			case UP:
				y = 0;
				break;
			case RIGHT:
				x = viewer.getWidth() - ball.getDiameter();
				break;
			case DOWN:
				y = viewer.getHeight() - ball.getDiameter();
				break;
			case LEFT:
				x = 0;
				break;
			}

			viewer.createBall(x, y, ball.getSpeedX(), ball.getSpeedY(), ball.getColor());
		} else if (message instanceof IdMessage) {
			IdMessage idMessage = (IdMessage) message;
			int peerId = idMessage.id;
			addNeighbour(ip, peerId);
		}
	}

	@Override
	public void onNewConnection(String ip) {
		IdMessage idMessage = new IdMessage();
		idMessage.id = id;
		comm.sendPrivate(ip, idMessage);
	}

	private void removeNeighbour(String ip) {
		for (int i = 0; i < neighbour.length; ++i) {
			if (neighbour[i] != null && neighbour[i].equals(ip)) {
				neighbour[i] = null;
				break;
			}
		}
	}

	public boolean rightHasWall() {
		return neighbour[Edge.RIGHT.ordinal()] == null;
	}

	public void setComm(ConnectionController comm) {
		this.comm = comm;
	}

	public void startAnimation() {
		viewer.startAnimation();
	}

	public boolean upHasWall() {
		return neighbour[Edge.UP.ordinal()] == null;
	}
}
