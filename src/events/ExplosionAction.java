package events;

public class ExplosionAction extends VisualObjectAction {
    public ExplosionAction(VisualObject visualObject) {
        super(visualObject);
    }

    @Override
    public String toString() {
        return "ExplosionAction [visualObject=" + super.getVisualObject() + "]";
    }

}
