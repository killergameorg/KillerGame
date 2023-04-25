package clients.animation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Set;
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
	
	private transient Dimension dimension;
	private transient Animation controller;
	private transient Set<WallPosition> wallPosition;
	private transient Timer timer;
    
    public Ball(Animation controller, int x, int y, int dx, int dy, Dimension dimension, Set<WallPosition> wallPosition) {
    	this.controller = controller;
    	this.x = x;
    	this.y = y;
    	this.dx = dx;
    	this.dy = dy;
    	this.dimension = dimension;
    	this.wallPosition = wallPosition;
    	diameter = 30;
    	timer = new Timer(5, this);
    	timer.start();
    }
    
    public Ball(Animation controller, Dimension dimension, Set<WallPosition> wallPosition) {
    	this(
			controller,
			(int)(Math.random()*dimension.width),
			(int)(Math.random()*dimension.height),
			Math.random() >= 0.5? 1 : -1,
			Math.random() >= 0.5? 1 : -1,
			dimension,
			wallPosition);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		x += dx;
        y += dy;

        if(x < 0) {
        	if(wallPosition.contains(WallPosition.LEFT)) {
        		dx = -dx;
        	} else {
        		controller.ballOutOfLimits(this);
        	}
        } else if(x > dimension.width - diameter) {
        	if(wallPosition.contains(WallPosition.RIGHT)) {
        		dx = -dx;
        	} else {
        		controller.ballOutOfLimits(this);
        	}
        }
        
        if(y < 0) {
        	if(wallPosition.contains(WallPosition.UP)) {
        		dy = -dy;
        	} else {
        		controller.ballOutOfLimits(this);
        	}
        } else if(y > dimension.height - diameter) {
    		if(wallPosition.contains(WallPosition.DOWN)) { 
    			dy = -dy;
			}  else {
				controller.ballOutOfLimits(this);
        	}
    	}
	}
	
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
    
    public int getSpeedX() {
    	return dx;
    }
    
    public int getSpeedY() {
    	return dy;
    }
    
    public int getDiameter() {
    	return diameter;
    }
    
	public void paint(Graphics g) {
	    g.fillOval(x, y, diameter, diameter);
	}
	
	@Override
	public String toString() {
		return "p("+ x + "," + y + ") | v(" + dx + "," + dy + ")";
	}
	
	public void stop() {
    	timer.stop();
    }
	
}
