package clients.asteroids;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.Timer;

import clients.asteroids.messages.AccelerateShipMessage;
import clients.asteroids.messages.RotateShipMessage;

public class AsteroidsViewer extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(AsteroidsViewer.class.getClass().getName());
	
	private AsteroidsController controller;
	private Set<Ship> shipList;
	private Timer t;
	
	int id;
	
	private int msRefresh;
	
	public AsteroidsViewer(AsteroidsController controller, int msRefresh) {
		this.controller = controller;
		this.msRefresh = msRefresh;
		
		setSize(600, 600);
		
		shipList = new HashSet<>();
		
		Viewer canvas = new Viewer(shipList);
		add(canvas);
		addKeyListener(this);
		setTitle("Asteroids clon");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		t = new Timer(msRefresh, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.repaint();
			}
		});
	}
		
	private void createShip() {
		createShip(getWidth()/2, getHeight()/2, 0, 0, 0);
	}
	
	void createShip(double x, double y, double dx, double dy, double rotation) {
		shipList.add(new Ship(controller, x, y, dx, dy, rotation, Color.BLACK, getWidth(), getHeight(), msRefresh));
	}
	
	public boolean downHasWall() {
		return controller.downHasWall();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		Optional<Ship> myShip = shipList.stream().filter(ship -> ship.id == id).findFirst();
        switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
        	if(myShip.isPresent()) {
        		myShip.get().doAcceleration(true);
        	} else {
        		AccelerateShipMessage message = new AccelerateShipMessage();
        		message.shipId = id;
        		message.accelerate = true;
        		controller.sendShipControlMessage(message);
        	}
            break;
        case KeyEvent.VK_LEFT:
        	if(myShip.isPresent()) {
        		myShip.get().rotateCounterClockWise();
        	} else {
        		RotateShipMessage message = new RotateShipMessage();
        		message.shipId = id;
        		message.rotation = -1;
        		controller.sendShipControlMessage(message);
        	}
            break;
        case KeyEvent.VK_RIGHT:
        	if(myShip.isPresent()) {
        		myShip.get().rotateClockWise();
        	} else {
        		RotateShipMessage message = new RotateShipMessage();
        		message.shipId = id;
        		message.rotation = 1;
        		controller.sendShipControlMessage(message);
        	}
            break;
        }
    }

	@Override
	public void keyReleased(KeyEvent e) {
		Optional<Ship> myShip = getShip(id);
        switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
        	if(myShip.isPresent()) {
        		myShip.get().doAcceleration(false);
        	} else {
        		AccelerateShipMessage message = new AccelerateShipMessage();
        		message.shipId = id;
    			message.accelerate = false;
        		controller.sendShipControlMessage(message);
        	}
            break;
        case KeyEvent.VK_LEFT:
        	if(myShip.isPresent()) {
        		myShip.get().stopRotation();
        	} else {
        		RotateShipMessage message = new RotateShipMessage();
        		message.shipId = id;
        		message.rotation = 0;
        		controller.sendShipControlMessage(message);
        	}
            break;
        case KeyEvent.VK_RIGHT:
        	if(myShip.isPresent()) {
        		myShip.get().stopRotation();
        	} else {
        		RotateShipMessage message = new RotateShipMessage();
        		message.shipId = id;
        		message.rotation = 0;
        		controller.sendShipControlMessage(message);
        	}
            break;
        }
    }

	public void keyTyped(KeyEvent e) {}

	public boolean leftHasWall() {
		return controller.leftHasWall();
	}

	public boolean rightHasWall() {
		return controller.rightHasWall();
	}
	
    public void shipOutOfLimits(Ship ship, Edge edge) {
		controller.shipOutOfLimits(ship, edge);
		ship.stopAnimation();
		shipList.remove(ship);
	}

    public void startAnimation() {
		createShip();
		t.start();
	}

    public boolean upHasWall() {
		return controller.upHasWall();
	}

	public Optional<Ship> getShip(int shipId) {
		Optional<Ship> myShip = shipList.stream().filter(ship -> ship.id == shipId).findFirst();
		return myShip;
	}

}
