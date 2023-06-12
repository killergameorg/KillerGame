package maincontroller.maincommunications.clustercomputers.movewindowvisualobject.packages;

import maincontroller.maincommunications.clustercomputers.packages.PackageClusterCommunications;
import visual.Direction;
import visual.VisualObject;

public class PackageMoveWindowVisualObject implements PackageClusterCommunications {

    // ! Attributes
    private Direction directionFrom;
    private VisualObject visualObject;

    // ! Constructor
    public PackageMoveWindowVisualObject(Direction directionFrom, VisualObject visualObject) {
        this.setDirectionFrom(directionFrom);
        this.setVisualObject(visualObject);
    }

    // ! Getters and Setters

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
     * @return the visualObject
     */
    public VisualObject getVisualObject() {
        return visualObject;
    }

    /**
     * @param visualObject the visualObject to set
     */
    public void setVisualObject(VisualObject visualObject) {
        this.visualObject = visualObject;
    }

}
