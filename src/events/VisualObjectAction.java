package events;

/**
 * The VisualObjectAction class is an abstract subclass of {@link Action} that
 * represents actions performed on a visual object.
 * It provides a common structure and functionality for visual object specific
 * actions.
 */

public abstract class VisualObjectAction extends Action {

    private VisualObject visualObject;

    /**
     * Constructs a new VisualObjectAction object with the specified visual object.
     *
     * @param visualObject the visualObject associated with the actions
     */
    public VisualObjectAction(VisualObject visualObject) {
        this.visualObject = visualObject;
    }

    /**
     * Returns the team associated with the actions.
     *
     * @return the team associated with the actions
     */
    public VisualObject getVisualObject() {
        return visualObject;
    }

    public void setVisualObject(VisualObject visualObject) {
        this.visualObject = visualObject;
    }
}
