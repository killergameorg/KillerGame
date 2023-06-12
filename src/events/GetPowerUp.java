package events;

import visual.VisualObject;

public class GetPowerUp extends Event {
    public GetPowerUp(VisualObject firstObject, VisualObject secondObject) {
        super(firstObject, secondObject);
    }
}
