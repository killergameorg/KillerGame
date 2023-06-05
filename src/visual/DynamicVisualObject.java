package visual;

import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.Graphics;
import visual.Direction;
import visual.VisualObject;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class DynamicVisualObject extends VisualObject {

    private double velocity;
    private Position futurePosition;
    
    // * Constructor

    public DynamicVisualObject(int id, BufferedImage skin, Position position, float life, int accountId,
            VisualGameModel visualGameModel, int playerNumber, Animation deadAnim, Animation spawnAnim,
            double velocity, double angle) {
        super(id, skin, position, life, accountId, visualGameModel, playerNumber, deadAnim, spawnAnim, angle);
        this.velocity = velocity;
        
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

    // * Methods

    @Override
    public void updatePosition() {
        setPosition(futurePosition);
        this.futurePosition = null;
    }

    @Override
    public void updateRotation(Direction direction) {
        double angle = getVisualGameModel().getPhysicsEngine().setDirection(direction, getAngle(),
                VisualConstants.rotationVelocity);
        setAngle(angle);
    }

    @Override
    public void calculateNewPosition() {
        this.futurePosition = getVisualGameModel().getPhysicsEngine().calculatePosition(getPosition(), getAngle(),
                getVelocity());
    }

    /* @Override
    public void drawObject(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = g2d.getTransform();
        g2d.translate(position.getxPos(), position.getyPos());
        g2d.rotate(angle);
        g2d.drawImage(this.skin, at, null);
    } */

    @Override
    public void run() {
        while (getIsAlive()) {
            if (futurePosition != null) {
                getVisualGameModel().notifyToVGC(new NotificationMsg(NotificationType.positionUpdate, this));
            }
        }
    }
}