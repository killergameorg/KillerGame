package events;

public class DestructionAction extends VisualObjectAction {
    public DestructionAction(VisualObject visualObject) {
        super(visualObject);
    }

    @Override
    public String toString() {
        return "DestructionAction [visualObject=" + super.getVisualObject() + "]";
    }

}
