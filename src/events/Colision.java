package events;

public class Colision extends Event {
    public Colision(VisualObject firstObject,VisualObject secondObject){
        super(firstObject, secondObject);
    }
}
