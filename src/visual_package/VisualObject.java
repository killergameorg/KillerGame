package visual_package;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class VisualObject {
    private int id;
    private BufferedImage skin;
    private Position position;
    private float life;
    private int accountId;

    private VisualGameModel visualGameModel;

    //visualmodel

    public VisualObject(int id, BufferedImage skin, Position position, float life, int accountId) {
        this.id = id;
        this.skin = skin;
        this.position = position;
        this.life = life;
        this.accountId = accountId;
    }



    public VisualGameModel getVisualGameModel() {
        return visualGameModel;
    }



    public void drawObject(Graphics g) {}
    public void kill() {}
    public void decreaseLife() {}
}
