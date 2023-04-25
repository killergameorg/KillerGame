package main;

import clients.animation.Animation;
import clients.automessenger.Messenger;
import communications.ConnectionController;

public class Main {
	
	public static void main(String[] args) {
		startAnimation();
	}
	
	@SuppressWarnings("unused")
	static private void startAnimation() {
		ConnectionController conn = new ConnectionController();
		Animation animation = new Animation(); 
		animation.setComm(conn);
		conn.setCommListener(animation);
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

}
