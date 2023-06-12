package events;

import visual.VisualObject;

public class LifeDecreaseAction extends VisualObjectAction {
    private float lifeDowngrade;

    public LifeDecreaseAction(VisualObject visualObject,float lifeDowngrade) {
        super(visualObject);
        this.lifeDowngrade=lifeDowngrade;
    }

    public float getLifeDowngrade() {
        return lifeDowngrade;
    }

    public void setLifeDowngrade(float lifeDowngrade) {
        this.lifeDowngrade = lifeDowngrade;
    }


    public VisualObject getVisualObject(){
        return super.getVisualObject();
    }
}
