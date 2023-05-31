package visual;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.*;

public class Viewer extends Canvas{

    // ! Attributes
    private BufferedImage background;

    // ! Constructor
    public Viewer(BufferedImage background) {
        this.background = background;
    }
    // ! Methods

    public void drawBackground(Graphics g) {
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null); // imatge escalada
    }
    // ! Getters and Setters

    public void setBackground(BufferedImage background) {
        this.background = background;
    }

}
