package events;

import visual.Direction;
import visual.DynamicVisualObject;

public class MoveWindowVisualObjectAction extends VisualObjectAction {

    // ! Attributes
    private Direction directionFrom;
    private Direction directionTo;

    // ! Constructor
    public MoveWindowVisualObjectAction(
            DynamicVisualObject dynamicVisualObject,
            Direction directionFrom,
            Direction directionTo

    ) {
        super(dynamicVisualObject);

        this.setDirectionFrom(directionFrom);
        this.setDirectionTo(directionTo);
    }

    /**
     * @return the directionFrom
     */
    public Direction getDirectionFrom() {
        return directionFrom;
    }

    /**
     * @param directionFrom the directionFrom to set
     */
    public void setDirectionFrom(Direction directionFrom) {
        this.directionFrom = directionFrom;
    }

    /**
     * @return the directionTo
     */
    public Direction getDirectionTo() {
        return directionTo;
    }

    /**
     * @param directionTo the directionTo to set
     */
    public void setDirectionTo(Direction directionTo) {
        this.directionTo = directionTo;
    }

    // ! Getters and Setters

}
