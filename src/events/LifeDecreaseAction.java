package events;

public class LifeDecreaseAction extends VisualObjectAction {
    private int lifeDowngrade;

    public int getLifeDowngrade() {
        return lifeDowngrade;
    }

    public void setLifeDowngrade(int lifeDowngrade) {
        this.lifeDowngrade = lifeDowngrade;
    }


    public VisualObject getVisualObject(){
        return super.getVisualObject();
    }

    public LifeDecreaseAction(VisualObject visualObject1,int lifeDowngrade) {
        super(visualObject1);
        this.lifeDowngrade=lifeDowngrade;
    }

    @Override
    public String toString() {
        return "LifeDecreaseAction [visualObject=" + super.getVisualObject() + ", lifeDowngrade=" + lifeDowngrade + "]";
    }
}
