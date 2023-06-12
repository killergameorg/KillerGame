package events;

import visual.VisualObject;

/**
 * The VisualObjectAction class is an abstract subclass of {@link Action} that
 * represents actions performed on a visual object.
 * It provides a common structure and functionality for visual object specific
 * actions.
 */

public abstract class VisualObjectAction extends Action {

    // Attributes
    private VisualObject visualObject;

    // Constructor
    public VisualObjectAction(VisualObject visualObject) {
        this.visualObject = visualObject;
    }

    // Getters and Setters
    public VisualObject getVisualObject() {
        return visualObject;
    }

    public void setVisualObject(VisualObject visualObject) {
        this.visualObject = visualObject;
    }
}
