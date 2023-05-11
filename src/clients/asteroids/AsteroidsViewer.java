package clients.asteroids;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.Timer;

import clients.asteroids.messages.AccelerateShipMessage;
import clients.asteroids.messages.RotateShipMessage;

public class AsteroidsViewer extends JFrame {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(AsteroidsViewer.class.getClass().getName());
	
	private static Color generateRandomColor() {
		Random random = new Random();
		// Genera un valor aleatorio para cada componente RGB
		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);

		// Retorna un objeto Color con los valores aleatorios generados
		return new Color(red, green, blue);
	}
	private AsteroidsController controller;
	private Set<Ship> shipList;
	
	private Timer t;
	
	private int msRefresh;
	
	public AsteroidsViewer(AsteroidsController controller, int msRefresh) {
		this.controller = controller;
		this.msRefresh = msRefresh;
		
		setSize(600, 600);
		
		shipList = new HashSet<>();
		
		Viewer canvas = new Viewer(shipList);
		add(canvas);
		addKeyListener(new ShipController(this));
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
	
	public void accelerateShip(int shipId, boolean accelerate) {
		Optional<Ship> myShip = shipList.stream().filter(ship -> ship.id == shipId).findFirst();
		if(myShip.isPresent()) {
    		myShip.get().doAcceleration(accelerate);
    	} else {
    		AccelerateShipMessage message = new AccelerateShipMessage();
    		message.shipId = shipId;
    		message.accelerate = accelerate;
    		controller.sendShipControlMessage(message);
    	}
	}
	
	int createShip(double x, double y, double dx, double dy, double rotation, Color color) {
		Ship ship = new Ship(this, x, y, dx, dy, rotation, color, getWidth(), getHeight(), msRefresh);
		ship.id = ship.hashCode();
		shipList.add(ship);
		return ship.hashCode();
	}
	
	void createShip(int id, double x, double y, double dx, double dy, double rotation, Color color) {
		Ship ship = new Ship(this, x, y, dx, dy, rotation, color, getWidth(), getHeight(), msRefresh);
		ship.id = id;
		shipList.add(ship);
	}

	public boolean downHasWall() {
		return controller.downHasWall();
	}

	public int getNewShip() {
		return createShip((int) (Math.random() * this.getWidth()), (int) (Math.random() * this.getHeight()), 0, 0, 0, generateRandomColor());
	}
	
    public Optional<Ship> getShip(int shipId) {
		Optional<Ship> myShip = shipList.stream().filter(ship -> ship.id == shipId).findFirst();
		return myShip;
	}

    public void keyTyped(KeyEvent e) {}

    public boolean leftHasWall() {
		return controller.leftHasWall();
	}

	public boolean rightHasWall() {
		return controller.rightHasWall();
	}
	
	public void rotateClockWiseShip(int shipId) {
		Optional<Ship> myShip = shipList.stream().filter(ship -> ship.id == shipId).findFirst();
		if(myShip.isPresent()) {
    		myShip.get().rotateClockWise();
    	} else {
    		RotateShipMessage message = new RotateShipMessage();
    		message.shipId = shipId;
    		message.rotation = 1;
    		controller.sendShipControlMessage(message);
    	}
	}

	public void rotateCounterClockWiseShip(int shipId) {
		Optional<Ship> myShip = shipList.stream().filter(ship -> ship.id == shipId).findFirst();
		if(myShip.isPresent()) {
    		myShip.get().rotateCounterClockWise();
    	} else {
    		RotateShipMessage message = new RotateShipMessage();
    		message.shipId = shipId;
    		message.rotation = -1;
    		controller.sendShipControlMessage(message);
    	}
	}
	
	public void shipOutOfLimits(Ship ship, Edge edge) {
		ship.stopAnimation();
		controller.shipOutOfLimits(ship, edge);
		shipList.remove(ship);
	}
	
	public void startAnimation() {
		t.start();
	}
	
	public void stopRotation(int shipId) {
		Optional<Ship> myShip = shipList.stream().filter(ship -> ship.id == shipId).findFirst();
		if(myShip.isPresent()) {
    		myShip.get().stopRotation();
    	} else {
    		RotateShipMessage message = new RotateShipMessage();
    		message.shipId = shipId;
    		message.rotation = 0;
    		controller.sendShipControlMessage(message);
    	}
	}
	
	public boolean upHasWall() {
		return controller.upHasWall();
	}
	
}
