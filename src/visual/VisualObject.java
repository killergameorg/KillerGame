package visual;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class VisualObject implements Runnable {

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

    // * Constructor

    public VisualObject(int id, BufferedImage skin, Position position, float life, int accountId,
            VisualGameModel visualGameModel, int playerNumber, Animation deadAnim, Animation spawnAnim, double width,
            double height) {
        this.id = id;
        this.skin = skin;
        this.position = position;
        this.life = life;
        this.accountId = accountId;
        this.visualGameModel = visualGameModel;
        this.playerNumber = playerNumber;
        this.deadAnim = deadAnim;
        this.spawnAnim = spawnAnim;
        this.width = width;
        this.height = height;
    }

    // * Getters

    public int getId() {
        return id;
    }

    public BufferedImage getSkin() {
        return skin;
    }

    public Position getPosition() {
        return position;
    }

    public float getLife() {
        return life;
    }

    public int getAccountId() {
        return accountId;
    }

    public VisualGameModel getVisualGameModel() {
        return visualGameModel;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Animation getDeadAnim() {
        return deadAnim;
    }

    public Animation getSpawnAnim() {
        return spawnAnim;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    // * Methods

    public void update() {
    }

    public void drawObject(Graphics g) {
    }

    public void kill() {
    }

    public void decreaseLife() {
        this.setLife(this.getLife()-quantity);
    }
}
