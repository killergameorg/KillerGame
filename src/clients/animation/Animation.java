package clients.animation;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.Timer;

import communications.ConnectionController;
import communications.P2PCommListener;

public class Animation extends JFrame implements P2PCommListener {

	private static final long serialVersionUID = 7660754719681217972L;
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(Animation.class.getClass().getName());
	
	private ConnectionController comm;
	private Set<WallPosition> wallPosition;
	private Set<Ball> ballsList;
	private Dimension windowDimension;
	private Timer t;
	private int numBalls;
	
    public Animation() {
    	windowDimension = new Dimension(400, 400);
    	wallPosition = new HashSet<>(4, 1);
    	ballsList = new HashSet<>();
    	
    	Canvas canvas = new Canvas() {
			private static final long serialVersionUID = -1477647394211129637L;

			@Override
    		public void paint(Graphics g) {
    			super.paint(g);
    			ballsList.forEach(b -> {
    				b.paint(g);
    			});
    		}
    	};
    	add(canvas);
    	
    	Properties properties = new Properties();
    	try {
			properties.load(new FileInputStream(new File("animation.properties")));
			String[] wallsProperties = {"wall_up", "wall_down", "wall_left", "wall_right"};
			for(int i=0; i<wallsProperties.length; ++i) {
				if(Boolean.parseBoolean(properties.getProperty(wallsProperties[i]))) {
					wallPosition.add(WallPosition.values()[i]);
				}
			}
			numBalls = Integer.parseInt(properties.getProperty("num_balls"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

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
    
    private void abortAnimation() {
		t.stop();
		ballsList.forEach(ball -> {
			ball.stop();
		});
		ballsList.clear();
	}
    
    public void ballOutOfLimits(Ball ball) {
		ball.stop();
		comm.sendFlood(ball);
		ballsList.remove(ball);
	}
    
    private void createBall() {
    	packBall(new Ball(this, windowDimension, wallPosition));
    }
    
	private void createBall(int x, int y, int dx, int dy) {
    	packBall(new Ball(this, x, y, dx, dy, windowDimension, wallPosition));
    }
    
	@Override
	public void onConnectionClosed(String ip) {
		abortAnimation();
	}

	@Override
	public void onConnectionLost(String ip) {
		abortAnimation();
	}

	@Override
	public void onIncomingMessage(String ip, Object message) {
		Ball ball = (Ball)message;
		int x = ball.getX();
		
		x += x<0? windowDimension.width - ball.getDiameter(): -windowDimension.width + ball.getDiameter();
		
		createBall(x, ball.getY(), ball.getSpeedX(), ball.getSpeedY());
	}

	@Override
	public void onNewConnection(String ip) {
		t.start();
		for(int i=0; i<numBalls; ++i) {
			createBall();
		}
	}

	private void packBall(Ball ball) {
    	ballsList.add(ball);
    }
	
	public void setComm(ConnectionController comm) {
		this.comm = comm;
	}
	
}
