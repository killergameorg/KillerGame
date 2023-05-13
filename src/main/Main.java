package main;

import clients.animation.AnimationController;
import clients.asteroids.AsteroidsController;
import clients.automessenger.Messenger;
import communications.ConnectionController;

public class Main {

	public static void main(String[] args) {
		startAsteroids();
	}

	@SuppressWarnings("unused")
	static private void startAnimation() {
		ConnectionController conn = new ConnectionController();
		AnimationController animationViewer = new AnimationController();
		animationViewer.startAnimation();
		animationViewer.setComm(conn);
		conn.setCommListener(animationViewer);
		conn.initialize();
	}

	@SuppressWarnings("unused")
	static private void startAutoMessenger() {
		ConnectionController conn = new ConnectionController();
		Messenger messenger = new Messenger();
		messenger.setComm(conn);
		conn.setCommListener(messenger);
		conn.initialize();
	}
	
	@SuppressWarnings("unused")
	static private void startAsteroids() {
		ConnectionController conn = new ConnectionController();
		AsteroidsController asteroids = new AsteroidsController();
		asteroids.setComm(conn);
		conn.setCommListener(asteroids);
		conn.initialize();
	}

}
