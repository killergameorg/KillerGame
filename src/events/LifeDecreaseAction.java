package events;

import visual.VisualObject;

/**
 * The LifeDecreaseAction class represents an action that decreases the life
 * of a visual object by a certain amount.
 * It extends the {@link VisualObjectAction} class.
 */
public class LifeDecreaseAction extends VisualObjectAction {

    // Attributes
    private float lifeDowngrade;

    // Constructor
    public LifeDecreaseAction(VisualObject visualObject,float lifeDowngrade) {
        super(visualObject);
        this.lifeDowngrade = lifeDowngrade;
    }

    // Getters and Setters
    public float getLifeDowngrade() {
        return lifeDowngrade;
    }

    public void setLifeDowngrade(float lifeDowngrade) {
        this.lifeDowngrade = lifeDowngrade;
    }

    public VisualObject getVisualObject() {
        return super.getVisualObject();
    }
}
