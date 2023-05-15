package visual_package;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class VisualObject {
    private int id;
    private BufferedImage skin;
    private Position position;
    private float life;
    private int accountId;

    public void drawObject(Graphics g) {}
    public void kill() {}
    public void decreaseLife() {}
}
