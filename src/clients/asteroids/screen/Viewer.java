package clients.asteroids.screen;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Set;

public class Viewer extends Canvas {

	private static final long serialVersionUID = -4775828920748550930L;

	private Set<Ship> shipList;

	public Viewer(Set<Ship> shipList) {
		this.shipList = shipList;
	}

	@Override
	public void paint(Graphics g) {
		// Dibuja en el canvas de respaldo
		Image offscreen = createImage(getWidth(), getHeight());
		Graphics offscreenGraphics = offscreen.getGraphics();
		offscreenGraphics.setColor(getBackground());
		offscreenGraphics.fillRect(0, 0, getWidth(), getHeight());
		shipList.forEach(ship -> {
			ship.paint(offscreenGraphics);
		});

		// Dibuja el canvas de respaldo en el canvas visible
		g.drawImage(offscreen, 0, 0, null);
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}
}
