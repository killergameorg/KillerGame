package events;

public class DestructionAction extends VisualObjectAction {
    public DestructionAction(VisualObject visualObject1) {
        super(visualObject1);
    }

    @Override
    public String toString() {
        return "DestructionAction [visualObject=" + super.getVisualObject() + "]";
    }

}
