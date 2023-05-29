package visual_package;

import java.awt.Graphics;

import visual_package.PhysicsEngine.Direction;

public abstract class DynamicVisualObject extends VisualObject implements Runnable {

    private double velocity;
    private Position futPos;
    private double angle;
    private int width;
    private int height;
    private double acceleration;

    // * Constructor

    public DynamicVisualObject(double velocity, double angle, int width, int height,
            double acceleration) {
        this.velocity = velocity;
        this.angle = angle;
        this.width = width;
        this.height = height;
        this.acceleration = acceleration;
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
        this.angle = this.pEngine.setDirection(direction, this.angle);
    }

    public void calculateNewPosition() {
        this.futPos = this.pEngine.addPosition(this.position, this.angle, this.velocity);
    }

    @Override
    public void drawObject(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform originalTransform = g2d.getTransform();
        g2d.translate(Position.getxPos, Position.getyPos);
        g2d.rotate(angle);
        g2d.drawImage(skin, at, null);
    }

}