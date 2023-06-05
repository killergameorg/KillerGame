package visual;

import java.awt.*;
import java.awt.image.BufferedImage;
import maincontroller.gameinfo.Team;

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
    private double angle;
    private Team team;

    // * Constructor

    public VisualObject(int id, BufferedImage skin, Position position, float life, int accountId,
            VisualGameModel visualGameModel, int playerNumber, Animation deadAnim, Animation spawnAnim, double angle, Team team) {

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
        this.angle = angle;
        this.team = team;

    }

    // * Getters

    public int getId() {
        return id;
    }

    public double getWidth() {
        double res = getSkin().getWidth();
        return res;
    }

    public double getHeight() {
        return (double) getSkin().getHeight();
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

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    // * Methods

    public void update() {
    }

    public void drawObject(Graphics g) {
    }

    public abstract void updatePosition();

    public abstract void updateRotation(Direction direction);

    public abstract void calculateNewPosition();

    public void kill() {
        // todo
        // deadAnim.play(this, );
        setIsAlive(false);
    }


    public void decreaseLife(float damage) {
        this.setLife(this.getLife()-damage);
    }

    @Override
    public void run() {
    }

}
