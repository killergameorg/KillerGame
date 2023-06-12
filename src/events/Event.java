package events;

import visual.VisualObject;

/**
 * The Event class represents an event which involves two visual
 * objects.
 */
public abstract class Event {

    // Attributes
    private VisualObject firstObject;
    private VisualObject secondObject;

    // Constructor
    public Event(VisualObject firstObject, VisualObject secondObject) {
        this.firstObject = firstObject;
        this.secondObject = secondObject;
    }

    // Getters and Setters
    public VisualObject getFirstObject() {
        return firstObject;
    }
    
    public VisualObject getSecondObject() {
        return secondObject;
    }

}
