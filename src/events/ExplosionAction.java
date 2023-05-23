package events;

import visual.VisualObject;

/**
 * The ExplosionAction class represents an action that triggers an explosion
 * effect on a visual object.
 * It extends the {@link VisualObjectAction} class.
 */
public class ExplosionAction extends VisualObjectAction {
    /**
     * Creates a new instance of the ExplosionAction class with the specified
     * visual object.
     *
     * @param visualObject The visual object on which the explosion action will be
     *                     triggered.
     */
    public ExplosionAction(VisualObject visualObject) {
        super(visualObject);
    }
}
