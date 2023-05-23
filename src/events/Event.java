package events;

/**
 * The Event class represents an event which involves two visual
 * objects.
 */
public abstract class Event {

    private VisualObject firstObject;
    private VisualObject secondObject;

    /**
     * Creates a new instance of the Event class with the specified visual
     * objects.
     *
     * @param firstObject  The first visual object involved in the event.
     * @param secondObject The second visual object involved in the event.
     */

    public Event(VisualObject firstObject, VisualObject secondObject) {
        this.firstObject = firstObject;
        this.secondObject = secondObject;
    }

    /**
     * Retrieves the first visual object involved in the event.
     *
     * @return The first visual object.
     */
    public VisualObject getFirstObject() {
        return firstObject;
    }

    /**
     * Retrieves the first visual object involved in the event.
     *
     * @return The first visual object.
     */
    public VisualObject getSecondObject() {
        return secondObject;
    }

}
