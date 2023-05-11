package clients.asteroids;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ShipController implements KeyListener{

	private int shipId;
	private AsteroidsViewer viewer;
	
	public ShipController(AsteroidsViewer viewer) {
		this.viewer = viewer;
		shipId = viewer.getNewShip();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
        	viewer.accelerateShip(shipId, true);
            break;
        case KeyEvent.VK_LEFT:
        	viewer.rotateCounterClockWiseShip(shipId);
            break;
        case KeyEvent.VK_RIGHT:
        	viewer.rotateClockWiseShip(shipId);
            break;
        }
    }

	@Override
	public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
        	viewer.accelerateShip(shipId, false);
            break;
        case KeyEvent.VK_LEFT:
        	viewer.stopRotation(shipId);
            break;
        case KeyEvent.VK_RIGHT:
        	viewer.stopRotation(shipId);
            break;
        }
    }

	@Override
	public void keyTyped(KeyEvent e) { }
}
