package visual_package;

public abstract class DynamicVisualObject extends VisualObject implements Runnable {

    private PhysicsEngine velocity;
    private Position futPos;
    private double angle;
    private double maxVelocity;
    private Integer width;
    private Integer height;
    private PhysicsEngine acceleration;

    // * Constructor

    public DynamicVisualObject(PhysicsEngine velocity, double angle, double maxVelocity, Integer width, Integer height,
            PhysicsEngine acceleration) {
        this.velocity = velocity;
        this.angle = angle;
        this.maxVelocity = maxVelocity;
        this.width = width;
        this.height = height;
        this.acceleration = acceleration;
    }

    // * Getters & Setters

    public PhysicsEngine getVelocity() {
        return this.velocity;
    }

    public void setVelocity(PhysicsEngine velocity) {
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

    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public PhysicsEngine getAcceleration() {
        return this.acceleration;
    }

    public void setAcceleration(PhysicsEngine acceleration) {
        this.acceleration = acceleration;
    }

    // * Methods

    public void updatePosition() {
        this.position = this.futPosition;
    }

    public void updateRotation(Direction direction) {
        this.position = this.position.setDirection(this.angle);
    }

    public void calculateNewPosition() {
        this.futPosition = this.position.addVector(this.position);
    }

}