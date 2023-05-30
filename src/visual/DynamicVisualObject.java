package visual;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.Graphics;
import visual.PhysicsEngine.Direction;
import visual_package.VisualObject;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class DynamicVisualObject extends VisualObject implements Runnable {


    private double velocity;
    private Position futPos;
    private double angle;
    private PhysicsEngine pEngine;

    // * Constructor

    public DynamicVisualObject(int id, BufferedImage skin, Position position, float life, int accountId, VisualGameModel vgm,int playerNumber,Animation deadAnim, Animation spawnAnim, double width, double height, double velocity, double angle, PhysicsEngine pEngine) {
        super(id, skin, position, life, accountId, vgm, playerNumber, deadAnim, spawnAnim, width, height);
        this.velocity = velocity;
        this.angle = angle;
        this.width = width;
        this.height = height;
        this.pEngine = pEngine;
    }

    // * Getters & Setters

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

    public double getMaxVelocity() {
        return this.maxVelocity;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getAcceleration() {
        return this.acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    // * Methods

    public void updatePosition() {
        this.position = this.futPos;
        this.futPos = null;
    }

    public void updateRotation(Direction direction) {
        this.angle = this.pEngine.setDirection(direction, this.angle, getVisualGameModel().ge);
    }

    public void calculateNewPosition() {
        this.futPos = this.pEngine.addPosition(this.position, this.angle, this.velocity);
    }

    @Override
    public void drawObject(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform originalTransform = g2d.getTransform();
        g2d.translate(position.getxPos(), position.getyPos());
        g2d.rotate(angle);
        g2d.drawImage(this.skin, at, null);
    }

}