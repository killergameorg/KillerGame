package clients.asteroids;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

public class Triangle implements Serializable {
	
	private static final long serialVersionUID = -5669477618299539693L;
	
	private Point[] vertices;
    private Color color;

    public Triangle(Point v1, Point v2, Point v3) {
        vertices = new Point[] {v1, v2, v3};
        color = Color.BLACK;
    }

    public int[] getXpoints() {
        int[] xpoints = new int[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            xpoints[i] = vertices[i].x;
        }
        return xpoints;
    }

    public int[] getYpoints() {
        int[] ypoints = new int[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            ypoints[i] = vertices[i].y;
        }
        return ypoints;
    }

    public int getNumVertices() {
        return vertices.length;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
