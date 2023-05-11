package clients.asteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Ship extends Triangle implements ActionListener {
	
	private static final long serialVersionUID = 2672537035090797204L;

	private Triangle propulsor;
    private boolean accelerating;
    private int rotationDirection;
	double x, y, dx, dy, da;
    int id;
    
    private transient AsteroidsController controller;
    private transient Timer t;
    private transient int windowWidth, windowHeight;
    
    public Ship(AsteroidsController controller, double x, double y, double dx, double dy, double da, Color color, int windowWith, int windowHeight, int msRefresh) {
    	super(new Point(0, -20), new Point(10, 10), new Point(-10, 10));
    	this.controller = controller;
    	this.x = x;
    	this.y = y;
    	this.dx = dx;
    	this.dy = dy;
    	this.da = da;
    	this.setColor(color);
    	this.windowWidth = windowWith;
    	this.windowHeight = windowHeight;
    	
        propulsor = new Triangle(new Point(0, 10), new Point(5, 20), new Point(-5, 20));
        propulsor.setColor(Color.RED);
        
        t = new Timer(msRefresh, this);
        t.start();
    }

    @Override
	public void actionPerformed(ActionEvent e) {
		if (accelerating) {
            double acceleration = 0.1;
            dx += acceleration * Math.sin(da);
            dy -= acceleration * Math.cos(da);
        }
        if (rotationDirection < 0) {
            da -= 0.05;
        }
        if (rotationDirection > 0) {
            da += 0.05;
        }
        x += dx;
        y += dy;
        
     // Verificar si la bola ha colisionado con las paredes laterales
     		if (x < 0) {
     			if (controller.leftHasWall()) {
     				// Colisión con la pared izquierda
     				x = 0;
     				dx = -dx/2; // Invertir la dirección horizontal
     			} else {
     				// Informa al controlador que la bola salió por la izquierda
     				controller.shipOutOfLimits(this, Edge.LEFT);
     			}
     		} else if (x  > windowWidth) {
     			if (controller.rightHasWall()) {
     				// Colisión con la pared derecha
     				x = windowWidth;
     				dx = -dx/2; // Invertir la dirección horizontal
     			} else {
     				// Informa al controlador que la bola salió por la derecha
     				controller.shipOutOfLimits(this, Edge.RIGHT);
     			}
     		}

     		// Verificar si la bola ha colisionado con las paredes superior e inferior
     		if (y < 0) {
     			if (controller.upHasWall()) {
     				// Colisión con la pared superior
     				y = 0;
     				dy = -dy/2; // Invertir la dirección vertical
     			} else {
     				// Informa al controlador que la bola salió por la parte superior
     				controller.shipOutOfLimits(this, Edge.UP);
     			}
     		} else if (y > windowHeight) {
     			if (controller.downHasWall()) {
     				// Colisión con la pared inferior
     				y = windowHeight;
     				dy = -dy/2; // Invertir la dirección vertical
     			} else {
     				// Informa al controlador que la bola salió por la parte inferior
     				controller.shipOutOfLimits(this, Edge.DOWN);
     			}
     		}
	}
    
    public void doAcceleration(boolean accelerate) {
    	this.accelerating = accelerate;
    }
    
    public Triangle getPropulsor() {
        return propulsor;
    }
    
    public int getRotation() {
    	return rotationDirection;
    }
    
    public boolean isAccelerating() {
    	return this.accelerating;
    }
    
    public void paint(Graphics g) {
    	Graphics2D g2d = (Graphics2D)g;
        g2d.translate(x, y);
        g2d.rotate(da);
        g2d.setColor(getColor());
        g2d.fillPolygon(getXpoints(), getYpoints(), getNumVertices());
        if (accelerating) {
            g2d.setColor(getPropulsor().getColor());
            g2d.fillPolygon(getPropulsor().getXpoints(), getPropulsor().getYpoints(), getPropulsor().getNumVertices());
        }
    }
    
    public void rotateClockWise() {
    	rotationDirection = 1;
    }
    
    public void rotateCounterClockWise() {
    	rotationDirection = -1;
    }
    
    public void stopAnimation() {
		t.stop();
	}

	public void stopRotation() {
    	rotationDirection = 0;
    }
}
