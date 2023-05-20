package events;

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
<<<<<<< HEAD

    public LifeDecreaseAction(VisualObject visualObject,int lifeDowngrade) {
        super(visualObject);
        this.lifeDowngrade=lifeDowngrade;
    }

    @Override
    public String toString() {
        return "LifeDecreaseAction [visualObject=" + super.getVisualObject() + ", lifeDowngrade=" + lifeDowngrade + "]";
    }
=======
>>>>>>> 9498e42c0ff9cafb4f6eb3b67661e745096a4b5b
}
