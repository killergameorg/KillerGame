package visual;

import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.Graphics;
import visual.PhysicsEngine.Direction;
import visual.VisualObject;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class DynamicVisualObject extends VisualObject implements Runnable {

    private double velocity;
    private Position futurePosition;
    private double angle;

    // * Constructor

    public DynamicVisualObject(int id, BufferedImage skin, Position position, float life, int accountId,
            VisualGameModel visualGameModel, int playerNumber, Animation deadAnim, Animation spawnAnim,
            double velocity, double angle) {
        super(id, skin, position, life, accountId, visualGameModel, playerNumber, deadAnim, spawnAnim);
        this.velocity = velocity;
        this.angle = angle;
    }

    // * Getters & Setters

    public Position getFuturePosition() {
        return futurePosition;
    }

    public void setFuturePosition(Position futPos) {
        this.futurePosition = futPos;
    }

    public double getVelocity() {
        return this.velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    // * Methods

    public void updatePosition() {
        this.position = this.futurePosition;
        this.futurePosition = null;
    }

    public void updateRotation(Direction direction) {
        this.angle = getVisualGameModel().getPhysicsEngine().setDirection(direction, getAngle(),
                VisualConstants.velocityRotation);
    }

    public void calculateNewPosition() {
        this.futurePosition = getVisualGameModel().getPhysicsEngine().calculatePosition(getPosition(), getAngle(),
                getVelocity());
    }

    @Override
    public void drawObject(Graphics g) {
        AffineTransform at = AffineTransform.getTranslateInstance(this.getPosition().getxPos(),
                this.getPosition().getyPos());
        at.rotate(Math.toRadians(this.getAngle()));

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(this.skin, at, null);
    }

    @Override
    public void run() {
        while (getIsAlive()) {
            if (futurePosition != null) {
                getVisualGameModel().notifyToVGC(new NotificationMsg(NotificationType.positionUpdate, this));
            }
        }
    }
}