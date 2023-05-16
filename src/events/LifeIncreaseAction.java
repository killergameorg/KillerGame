package events;

public class LifeIncreaseAction extends VisualObjectAction {
    private int lifeUpgrade;

    public int getUpgrade() {
        return lifeUpgrade;
    }

    public VisualObject getVisualObject(){
        return super.getVisualObject();
    }

    public LifeIncreaseAction(VisualObject visualObject, int upgrade) {
        super(visualObject);
        this.lifeUpgrade = upgrade;
    }

    @Override
    public String toString() {
        return "LifeIncreaseAction [visualObject=" + super.getVisualObject() + ",upgrade=" + lifeUpgrade + "]";
    }

}
