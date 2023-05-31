package visual;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class VisualObject implements Runnable {

    private int id;
    private BufferedImage skin;
    private Position position;
    private float life;
    private int accountId;
    private VisualGameModel visualGameModel;
    private int playerNumber;
    private Animation deadAnim;
    private Animation spawnAnim;
    private boolean isAlive; 
    // * Constructor

    public VisualObject(int id, BufferedImage skin, Position position, float life, int accountId,
            VisualGameModel visualGameModel, int playerNumber, Animation deadAnim, Animation spawnAnim) {
        this.id = id;
        this.skin = skin;
        this.position = position;
        this.life = life;
        this.accountId = accountId;
        this.visualGameModel = visualGameModel;
        this.playerNumber = playerNumber;
        this.deadAnim = deadAnim;
        this.spawnAnim = spawnAnim;
        this.isAlive = true;
    }

    // * Getters

    public int getId() {
        return id;
    }

    public double getWidth() {
        return getSkin.getWidth();
    }

    public double getHeight() {
        return getSkin.getHeight();
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

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    // * Methods

    public void update() {
    }

    public void drawObject(Graphics g) {
    }

    public void kill() {
        deadAnim.play(this, );
        setIsAlive(false);
    }

    public void decreaseLife() {
    }

    @Override
    public void run() {
      
    }
    
}
