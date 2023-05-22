package clients.asteroids.controller;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import clients.asteroids.messages.AccelerateShipMessage;
import clients.asteroids.messages.RotateShipMessage;

public class ControlEmulator extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(ControlEmulator.class.getClass().getName());

	private Integer shipId;
	private ControlController controller;
	private Label lblShipId;
	
	public ControlEmulator(ControlController controller) {
		this.controller = controller;
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		add(new Label("Use arrow keys to control the ship"), c);
		add(new Label("<    ^    >"), c);
		lblShipId = new Label();
		setShipLabelText();
		add(lblShipId, c);
		
		addKeyListener(this);
		
		setTitle("Asteroids controller");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public void accelerateShip(boolean accelerate) {
		if(shipId != null) {
			var message = new AccelerateShipMessage();
			message.shipId = shipId;
			message.accelerate = accelerate;
			controller.sendShipControlMessage(message);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			accelerateShip(true);
			break;
		case KeyEvent.VK_LEFT:
			rotateCounterClockWiseShip();
			break;
		case KeyEvent.VK_RIGHT:
			rotateClockWiseShip();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			accelerateShip(false);
			break;
		case KeyEvent.VK_LEFT:
			stopRotation();
			break;
		case KeyEvent.VK_RIGHT:
			stopRotation();
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public void rotateClockWiseShip() {
		if(shipId != null) {
			var message = new RotateShipMessage();
			message.shipId = shipId;
			message.rotation = 1;
			controller.sendShipControlMessage(message);
		}
	}

	public void rotateCounterClockWiseShip() {
		if(shipId != null) {
			var message = new RotateShipMessage();
			message.shipId = shipId;
			message.rotation = -1;
			controller.sendShipControlMessage(message);
		}
	}

	public void setShipId(Integer shipId) {
		this.shipId = shipId;
		setShipLabelText();
	}

	public void stopRotation() {
		if(shipId != null) {
			var message = new RotateShipMessage();
			message.shipId = shipId;
			message.rotation = 0;
			controller.sendShipControlMessage(message);
		}
	}
	
	private void setShipLabelText() {
		String label = "Ship id: ";
		if(shipId == null) {
			label += "NO SHIP";
		} else {
			label += shipId;
		}
		lblShipId.setText(label);
	}

}
