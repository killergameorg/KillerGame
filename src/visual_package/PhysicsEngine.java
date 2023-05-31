package visual_package;

public class PhysicsEngine {

    private double x, y;

    // * Constructor

    public PhysicsEngine(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public PhysicsEngine() {
        x = 0;
        y = 0;
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
    public PhysicsEngine addVector(P) {
        return new PhysicsEngine(x + pe.getX(), y + pe.getY());
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

    public PhysicsEngine setDirection(Direction direction) {
        double angle;
        if (direction == Direction.LEFT) {
            angle = Math.PI; // Ángulo de 180 grados (izquierda)
        } else {
            angle = 0; // Ángulo de 0 grados (derecha)
        }

        double magnitude = getMagnitude();
        return new PhysicsEngine(Math.cos(angle) * magnitude, Math.sin(angle) * magnitude);
    }

}
