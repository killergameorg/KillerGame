package events;

import visual.VisualObject;

/**
 * The Collision class represents a collision event between two visual objects.
 * It extends the {@link Event} class.
 */
public class Collision extends Event {
    // Constructor
    public Collision(VisualObject firstObject, VisualObject secondObject) {
        super(firstObject, secondObject);
    }
}
