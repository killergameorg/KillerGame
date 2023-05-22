package events;

public class LifeIncreaseAction extends VisualObjectAction {
    private int lifeUpgrade;

    public LifeIncreaseAction(VisualObject visualObject, int upgrade) {
        super(visualObject);
        this.lifeUpgrade = upgrade;
    }

    public int getUpgrade() {
        return lifeUpgrade;
    }

    public VisualObject getVisualObject(){
        return super.getVisualObject();
    }

}
