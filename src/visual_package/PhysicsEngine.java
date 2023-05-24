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

    /**
     * Se suman los vectores, se usa cuando cualquier objeto coge velocidad o se
     * desplaza
     * 
     * @param pe
     * @return
     */
    /*public Position addPosition(Position p, Position v) {
        return new Position(p.getX() + v.getX(), p.getY() + v.getY());
    }*/
    public Position addPosition(Position p, double vel) {
        return new Position(p.getX() + vel , p.getY() + vel);
    }
    public Position calculatePath(Position p, double angle) {
        return new Position(p.getxPos + (vConstants.SHIP_ACC * Math.sin(angle)), p.getyPos - (vConstants.SHIP_ACC * Math.cos(angle)));
    }

    /**
     * Se restan los vectores, se usa al frenar un objeto
     * 
     * @param pe
     * @return
     */
    public PhysicsEngine subtractVector(PhysicsEngine pe) {
        return new PhysicsEngine(x - pe.getX(), y - pe.getY());
    }

    /**
     *
     * @param value
     * @return
     */
    public PhysicsEngine scale(double value) {
        return new PhysicsEngine(x * value, y * value);
    }

    /**
     *
     * @param value
     * @return
     */
    public PhysicsEngine limit(double value) {
        if (getMagnitude() > value) {
            return this.normalize().scale(value);
        }
        return this;
    }

    /**
     * La normalizacion es la transformacion del vector en unitario,
     * se usa para frenar el movimiento
     * 
     * @return
     */
    public PhysicsEngine normalize() {
        double magnitude = getMagnitude();
        return new PhysicsEngine(x / magnitude, y / magnitude);
    }

    /**
     * Coge la magnitud del vector, es una forma de calcular posiciones
     * 
     * @return
     */
    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Establecer la direcciónd elos objetos en movimiento ede forma angular
     * 
     * @param angle
     * @return
     */

    /*
     * public PhysicsEngine setDirection(double angle) {
     * double magnitude = getMagnitude();
     * return new PhysicsEngine(Math.cos(angle)*magnitude,
     * Math.sin(angle)*magnitude);
     * }
     */

    /**
     * Establecer la direcciónd elos objetos en movimiento ede forma angular
     * 
     * @param angle
     * @return
     */

    public double setDirection(Direction direction, double angle) {
        if (direction == Direction.LEFT) {
            return angle -= vConstants.ANGLE_ROTATION; 
        } else if (direction == Direction.RIGHT) {
            return angle += vConstants.ANGLE_ROTATION;
        }
        return;
    }

}
