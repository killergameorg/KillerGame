package events;

public class ExplosionAction extends VisualObjectAction {
    public ExplosionAction(VisualObject visualObject1) {
        super(visualObject1);
    }

    @Override
    public String toString() {
        return "ExplosionAction [visualObject=" + super.getVisualObject() + "]";
    }

}
