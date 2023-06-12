package events;

import visual.VisualObject;

public abstract class VisualObjectAction extends Action {

    private VisualObject visualObject;

    public VisualObjectAction(VisualObject visualObject) {
        this.visualObject = visualObject;
    }

    public VisualObject getVisualObject() {
        return visualObject;
    }

    public void setVisualObject(VisualObject visualObject) {
        this.visualObject = visualObject;
    }
}
