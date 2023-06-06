package visual;

public class Position {
    private double xPos;
    private double yPos;

    // * Constructor

    public Position(double x, double y) {
        this.xPos = x;
        this.yPos = y;
    }

    // * Methods

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }
}