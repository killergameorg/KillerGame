package clients.animation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.Timer;

public class AnimationViewer extends JFrame {

	private static final long serialVersionUID = 7660754719681217972L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(AnimationViewer.class.getClass().getName());

	
	/** 
	 * @return Color
	 */
	private static Color generateRandomColor() {
		Random random = new Random();
		// Genera un valor aleatorio para cada componente RGB
		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);

		// Retorna un objeto Color con los valores aleatorios generados
		return new Color(red, green, blue);
	}

	private AnimationController controller;
	private Set<Ball> ballsList;
	private Dimension windowDimension;
	private Timer t;

	private int numBalls;

	public AnimationViewer(AnimationController controller, int numBalls) {
		this.controller = controller;
		this.numBalls = numBalls;
		windowDimension = new Dimension(400, 400);
		ballsList = new HashSet<>();

		Viewer canvas = new Viewer(ballsList);
		add(canvas);

		setSize(windowDimension);
		setTitle("Pelota Rebotando");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		t = new Timer(5, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.repaint();
			}
		});
	}

	@SuppressWarnings("unused")
	private void abortAnimation() {
		t.stop();
		ballsList.forEach(ball -> {
			ball.stop();
		});
		ballsList.clear();
	}

	public void ballOutOfLimits(Ball ball, Edge edge) {
		ball.stop();
		controller.ballOutOfLimits(ball, edge);
		ballsList.remove(ball);
	}

	private void createBall() {
		createBall((int) (Math.random() * this.getWidth()), (int) (Math.random() * this.getHeight()),
				(int) Math.round(Math.random() * 4) - 2, (int) Math.round(Math.random() * 4) - 2,
				generateRandomColor());
	}

	void createBall(int x, int y, int dx, int dy, Color color) {
		ballsList.add(new Ball(this, x, y, dx, dy, color));
	}

	public boolean downHasWall() {
		return controller.downHasWall();
	}

	public boolean leftHasWall() {
		return controller.leftHasWall();
	}

	public boolean rightHasWall() {
		return controller.rightHasWall();
	}

	public void startAnimation() {
		for (int i = 0; i < numBalls; ++i) {
			createBall();
		}
		t.start();
	}

	public boolean upHasWall() {
		return controller.upHasWall();
	}

}
