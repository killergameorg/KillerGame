package maincontroller;

public class Joystick {

    // ! Attributes
    private boolean left;
    private boolean right;
    private boolean thrust;
    private boolean fire;

    // ! Constructor
    public Joystick() {

    }

    // ! Getters and Setters
    /**
     * @return the left
     */
    public boolean isLeft() {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(boolean left) {
        this.left = left;
    }

    /**
     * @return the right
     */
    public boolean isRight() {
        return right;
    }

    /**
     * @param right the right to set
     */
    public void setRight(boolean right) {
        this.right = right;
    }

    /**
     * @return the thrust
     */
    public boolean isThrust() {
        return thrust;
    }

    /**
     * @param thrust the thrust to set
     */
    public void setThrust(boolean thrust) {
        this.thrust = thrust;
    }

    /**
     * @return the fire
     */
    public boolean isFire() {
        return fire;
    }

    /**
     * @param fire the fire to set
     */
    public void setFire(boolean fire) {
        this.fire = fire;
    }
}
