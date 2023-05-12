package clients.asteroids;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

public class Triangle implements Serializable {
	
	private static final long serialVersionUID = -5669477618299539693L;
	
	private int[] xpoints;
	private int[] ypoints;
    private Color color;

    public Triangle(Point v1, Point v2, Point v3) {
    	xpoints = new int[3];
    	ypoints = new int[3];
    	
    	xpoints[0] = v1.x;
    	ypoints[0] = v1.y;
    	
    	xpoints[1] = v2.x;
    	ypoints[1] = v2.y;
    	
    	xpoints[2] = v3.x;
    	ypoints[2] = v3.y;
    	
        color = Color.BLACK;
    }

    public int[] getXpoints() {
        return xpoints;
    }

    public int[] getYpoints() {
        return ypoints;
    }

    public int getNumVertices() {
        return xpoints.length;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
