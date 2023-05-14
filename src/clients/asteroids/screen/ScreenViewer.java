package clients.asteroids.screen;

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

public class ScreenViewer extends JFrame {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(ScreenViewer.class.getClass().getName());
	
	private static Color generateRandomColor() {
		Random random = new Random();
		// Genera un valor aleatorio para cada componente RGB
		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);

		// Retorna un objeto Color con los valores aleatorios generados
		return new Color(red, green, blue);
	}
	
	private ScreenController controller;
	private Set<Ship> shipList;
	
	private Timer t;
	
	private int msRefresh;
	
	public ScreenViewer(ScreenController controller, int msRefresh) {
		this.controller = controller;
		this.msRefresh = msRefresh;
		
		setSize(600, 600);
		
		shipList = new HashSet<>();
		
		Viewer canvas = new Viewer(shipList);
		add(canvas);
		setTitle("Asteroids clon");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		t = new Timer(msRefresh, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.repaint();
			}
		});
		t.start();
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
	
	public void shipOutOfLimits(Ship ship, Edge edge) {
		ship.stopAnimation();
		controller.shipOutOfLimits(ship, edge);
		shipList.remove(ship);
	}
	
	public boolean upHasWall() {
		return controller.upHasWall();
	}
	
}
