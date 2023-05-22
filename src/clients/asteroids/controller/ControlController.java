package clients.asteroids.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import clients.asteroids.messages.IdShipMessage;
import clients.asteroids.messages.RequestShipIdMessage;
import communications.ConnectionController;
import communications.P2PCommListener;

public class ControlController implements P2PCommListener{
	private ConnectionController comm;
	private ControlEmulator emulator;

	private int team;
	
	public ControlController() {
		emulator = new ControlEmulator(this);
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File("asteroids_controller.properties")));
			team = Integer.parseInt(properties.getProperty("team"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	@Override
	public void onConnectionClosed(String ip) {
		emulator.setShipId(null);
	}

	@Override
	public void onConnectionLost(String ip) {
		emulator.setShipId(null);
	}

	@Override
	public void onIncomingMessage(String ip, Object message) {
		if (message instanceof IdShipMessage) {
			emulator.setShipId(((IdShipMessage)message).shipId);
		}
	}

	@Override
	public void onNewConnection(String ip) {
		var message = new RequestShipIdMessage();
		message.petitionaryId = hashCode();
		message.team = team;
		comm.sendFlood(message);
	}

	public void sendShipControlMessage(Object message) {
		comm.sendFlood(message);
	}

	public void setComm(ConnectionController comm) {
		this.comm = comm;
	}

}
