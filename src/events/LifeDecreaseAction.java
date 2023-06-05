package events;

import visual.VisualObject;

/**
 * The LifeDecreaseAction class represents an action that decreases the life
 * of a visual object by a certain amount.
 * It extends the {@link VisualObjectAction} class.
 */
public class LifeDecreaseAction extends VisualObjectAction {
    private float lifeDowngrade;

    public LifeDecreaseAction(VisualObject visualObject,float lifeDowngrade) {
        super(visualObject);
        this.lifeDowngrade = lifeDowngrade;
    }

    public float getLifeDowngrade() {
        return lifeDowngrade;
    }

    public void setLifeDowngrade(float lifeDowngrade) {
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
