package events;

import visual.VisualObject;

/**
 * The Collision class represents a collision event between two visual objects.
 * It extends the {@link Event} class.
 */
public class Collision extends Event {
    /**
     * Creates a new instance of the Collision class with the visual objects
     * involved in the collision.
     *
     * @param firstObject  The first visual object involved in the collision.
     * @param secondObject The second visual object involved in the collision.
     */
    public Collision(VisualObject firstObject, VisualObject secondObject) {
        super(firstObject, secondObject);
    }
}
