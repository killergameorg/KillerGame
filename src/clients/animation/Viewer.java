package clients.animation;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Set;

public class Viewer extends Canvas {

	private static final long serialVersionUID = 580963772543020837L;

	private Set<Ball> ballsList;

	public Viewer(Set<Ball> ballsList) {
		this.ballsList = ballsList;
	}

	@Override
	public void paint(Graphics g) {
		// Dibuja en el canvas de respaldo
		Image offscreen = createImage(getWidth(), getHeight());
		Graphics offscreenGraphics = offscreen.getGraphics();
		offscreenGraphics.setColor(getBackground());
		offscreenGraphics.fillRect(0, 0, getWidth(), getHeight());
		ballsList.forEach(b -> {
			offscreenGraphics.setColor(b.getColor());
			b.paint(offscreenGraphics);
		});

		// Dibuja el canvas de respaldo en el canvas visible
		g.drawImage(offscreen, 0, 0, null);
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

}
