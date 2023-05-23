package events;

import visual.VisualObject;

/**
 * The LifeIncreaseAction class represents an action that increases the life
 * of a visual object by a certain amount.
 * It extends the {@link VisualObjectAction} class.
 */
public class LifeIncreaseAction extends VisualObjectAction {
    private int lifeUpgrade;

    /**
     * Creates a new instance of the LifeIncreaseAction class with the specified
     * visual object and life upgrade value.
     *
     * @param visualObject The visual object whose life will be increased.
     * @param upgrade      The amount by which the life of the visual object will
     *                     be increased.
     */
    public LifeIncreaseAction(VisualObject visualObject, int upgrade) {
        super(visualObject);
        this.lifeUpgrade = upgrade;
    }

    /**
     * Retrieves the life upgrade value.
     *
     * @return The life upgrade value.
     */
    public int getUpgrade() {
        return lifeUpgrade;
    }

    /**
     * Retrieves the visual object associated with the action.
     *
     * @return The visual object associated with the action.
     */
    public VisualObject getVisualObject() {
        return super.getVisualObject();
    }

}
