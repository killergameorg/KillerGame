package main;

import clients.animation.AnimationController;
import clients.automessenger.Messenger;
import communications.ConnectionController;

public class Main {

	public static void main(String[] args) {
		startAnimation();
	}

	@SuppressWarnings("unused")
	static private void startAnimation() {
		var comm = new ConnectionController();
		var animationViewer = new AnimationController();
		animationViewer.startAnimation();
		animationViewer.setComm(comm);
		comm.setCommListener(animationViewer);
		comm.initialize();
	}

	@SuppressWarnings("unused")
	static private void startAutoMessenger() {
		var comm = new ConnectionController();
		var messenger = new Messenger();
		messenger.setComm(comm);
		comm.setCommListener(messenger);
		comm.initialize();
	}
	
	@SuppressWarnings("unused")
	static private void startAsteroidsScreen() {
		var comm = new ConnectionController();
		var asteroids = new clients.asteroids.screen.ScreenController();
		asteroids.setComm(comm);
		comm.setCommListener(asteroids);
		comm.initialize();
	}
	
	@SuppressWarnings("unused")
	static private void startAsteroidsController() {
		var comm = new ConnectionController();
		var controller = new clients.asteroids.controller.ControlController();
		controller.setComm(comm);
		comm.setCommListener(controller);
		comm.initialize();
	}

}
