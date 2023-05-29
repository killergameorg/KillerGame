package visual;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class VisualObject implements Runnable{
    protected int id;
    protected BufferedImage skin;
    protected Position position;
    protected float life;
    protected int accountId;
    private VisualGameModel visualGameModel;
    protected int playerNumber;
    protected Animation deadAnim;
    protected Animation spawnAnim;
    protected double width;
    protected double height;


    public VisualObject(int id, BufferedImage skin, Position position, float life, int accountId, VisualGameModel vgm,int playerNumber,Animation deadAnim, Animation spawnAnim, double width, double height) {
        this.id = id;
        this.skin = skin;
        this.position = position;
        this.life = life;
        this.accountId = accountId;
        this.visualGameModel = vgm;
        this.playerNumber = playerNumber;
        this.deadAnim = deadAnim;
        this.spawnAnim = spawnAnim;
        this.width = width;
        this.height = height;
    }
    public void update(){}
    public void drawObject(Graphics g) {}
    public void kill() {}
    public void decreaseLife() {}
}
