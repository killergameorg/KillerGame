package visual_package;

public class PhysicsEngine {
    private VisualConstants vConstants;

    // * Constructor

    public PhysicsEngine() {
        this.vConstants = new VisualConstants();
    }

    // * Getters & Setters

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    // * Methods

    public enum Direction {
        LEFT, RIGHT
    }

    public Position calculatePath(Position p, double angle, double v) {
        return new Position(p.getxPos + (v * Math.sin(angle)), p.getyPos - (v * Math.cos(angle)));
    }

    public double setDirection(Direction direction, double angle) {
        if (direction == Direction.LEFT) {
            return angle -= vConstants.ANGLE_ROTATION; 
        } else if (direction == Direction.RIGHT) {
            return angle += vConstants.ANGLE_ROTATION;
        }
        return;
    }

}
