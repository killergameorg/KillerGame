package events;

public abstract class Event {

    private VisualObject firstObject;
    private VisualObject secondObject;

    public VisualObject getFirstObject() {
        return firstObject;
    }

    public VisualObject getSecondObject() {
        return secondObject;
    }

    public Event(VisualObject firstObject, VisualObject secondObject) {
        this.firstObject = firstObject;
        this.secondObject = secondObject;
    }

}