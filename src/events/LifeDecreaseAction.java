package events;

import visual.VisualObject;

public class LifeDecreaseAction extends VisualObjectAction {
    private int lifeDowngrade;

    public LifeDecreaseAction(VisualObject visualObject,int lifeDowngrade) {
        super(visualObject);
        this.lifeDowngrade=lifeDowngrade;
    }

    public int getLifeDowngrade() {
        return lifeDowngrade;
    }

    public void setLifeDowngrade(int lifeDowngrade) {
        this.lifeDowngrade = lifeDowngrade;
    }


    public VisualObject getVisualObject(){
        return super.getVisualObject();
    }
}
