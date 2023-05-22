package clients.animation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.logging.Logger;

import javax.swing.Timer;

public class Ball implements Serializable, ActionListener {

	private static final long serialVersionUID = -4238822915986025242L;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(Ball.class.getClass().getName());

	private int x;
	private int y;
	private int dx;
	private int dy;
	private int diameter;
	private int red;
	private int green;
	private int blue;
	
	private transient AnimationViewer animationController;
	private transient Timer timer;

	public Ball(AnimationViewer animationController, int x, int y, int dx, int dy, Color color, int msRefresh) {
		this.animationController = animationController;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		red = color.getRed();
		green = color.getGreen();
		blue = color.getBlue();
		
		diameter = 30;
		timer = new Timer(msRefresh, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Obtener las dimensiones del contenedor
		int containerWidth = animationController.getWidth();
		int containerHeight = animationController.getHeight();

		// Actualizar la posición de la bola según la velocidad
		x += dx;
		y += dy;

		// Verificar si la bola ha colisionado con las paredes laterales
		if (x < 0) {
			if (animationController.leftHasWall()) {
				// Colisión con la pared izquierda
				x = 0;
				dx = -dx; // Invertir la dirección horizontal
			} else {
				// Informa al controlador que la bola salió por la izquierda
				animationController.ballOutOfLimits(this, Edge.LEFT);
			}
		} else if (x + diameter > containerWidth) {
			if (animationController.rightHasWall()) {
				// Colisión con la pared derecha
				x = containerWidth - diameter;
				dx = -dx; // Invertir la dirección horizontal
			} else {
				// Informa al controlador que la bola salió por la derecha
				animationController.ballOutOfLimits(this, Edge.RIGHT);
			}
		}

		// Verificar si la bola ha colisionado con las paredes superior e inferior
		if (y < 0) {
			if (animationController.upHasWall()) {
				// Colisión con la pared superior
				y = 0;
				dy = -dy; // Invertir la dirección vertical
			} else {
				// Informa al controlador que la bola salió por la parte superior
				animationController.ballOutOfLimits(this, Edge.UP);
			}
		} else if (y + diameter > containerHeight) {
			if (animationController.downHasWall()) {
				// Colisión con la pared inferior
				y = containerHeight - diameter;
				dy = -dy; // Invertir la dirección vertical
			} else {
				// Informa al controlador que la bola salió por la parte inferior
				animationController.ballOutOfLimits(this, Edge.DOWN);
			}
		}
	}

	public Color getColor() {
		return new Color(red, green, blue);
	}

	public int getDiameter() {
		return diameter;
	}

	public int getSpeedX() {
		return dx;
	}

	public int getSpeedY() {
		return dy;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void paint(Graphics g) {
		g.fillOval(x, y, diameter, diameter);
	}

	public void stop() {
		timer.stop();
	}

	@Override
	public String toString() {
		return "p(" + x + "," + y + ") | v(" + dx + "," + dy + ")";
	}

}
