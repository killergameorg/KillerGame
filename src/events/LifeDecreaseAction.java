package events;

/**
 * The LifeDecreaseAction class represents an action that decreases the life
 * of a visual object by a certain amount.
 * It extends the {@link VisualObjectAction} class.
 */
public class LifeDecreaseAction extends VisualObjectAction {
    private int lifeDowngrade;

    /**
     * Creates a new instance of the LifeDecreaseAction class with the specified
     * visual object and life downgrade value.
     *
     * @param visualObject  The visual object whose life will be decreased.
     * @param lifeDowngrade The amount by which the life of the visual object will
     *                      be decreased.
     */
    public LifeDecreaseAction(VisualObject visualObject, int lifeDowngrade) {
        super(visualObject);
        this.lifeDowngrade = lifeDowngrade;
    }

    /**
     * Retrieves the life downgrade value.
     *
     * @return The life downgrade value.
     */
    public int getLifeDowngrade() {
        return lifeDowngrade;
    }

    public void setLifeDowngrade(int lifeDowngrade) {
        this.lifeDowngrade = lifeDowngrade;
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
