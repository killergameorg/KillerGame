package visual;

public class PhysicsEngine {

    // * Methods

    public Position calculatePosition(Position position, double angle, double velocity) {
        return new Position(position.getxPos() + (velocity * Math.sin(angle)),
                position.getyPos() - (velocity * Math.cos(angle)));
    }

    public double setDirection(Direction direction, double angle, double rotationVelocity) {
        double resAngle = 0;
        if (direction == Direction.LEFT) {
            resAngle = angle - rotationVelocity;
        } else if (direction == Direction.RIGHT) {
            resAngle = angle + rotationVelocity;
        }
        return resAngle;
    }

}
