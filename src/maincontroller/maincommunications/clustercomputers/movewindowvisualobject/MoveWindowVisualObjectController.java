package maincontroller.maincommunications.clustercomputers.movewindowvisualobject;

import java.util.HashMap;

import events.MoveWindowVisualObjectAction;
import maincontroller.maincommunications.clustercomputers.ClusterCommunicationsController;
import maincontroller.maincommunications.clustercomputers.movewindowvisualobject.packages.PackageMoveWindowVisualObject;
import visual.Direction;
import visual.Position;
import visual.VisualObject;

public class MoveWindowVisualObjectController {

    // ! Attributes
    private ClusterCommunicationsController clusterCommunicationsController;

    private HashMap<Direction, Integer> idClusterDirection;

    // ! Constructor

    public MoveWindowVisualObjectController(ClusterCommunicationsController clusterCommunicationsController) {
        this.setClusterCommunicationsController(clusterCommunicationsController);

        this.setIdClusterDirection(new HashMap<Direction, Integer>());
    }

    // ! Methods

    public void loadIdClusterDirection(int idNewCluster) {

        if (idNewCluster == this.getMyId() - 1) {
            this.getIdClusterDirection().put(Direction.LEFT, idNewCluster);

        } else if (idNewCluster == this.getMyId() + 1) {
            this.getIdClusterDirection().put(Direction.RIGHT, idNewCluster);

        } else if (idNewCluster == this.getMyId() - 3) {
            this.getIdClusterDirection().put(Direction.UP, idNewCluster);

        } else if (idNewCluster == this.getMyId() + 3) {
            this.getIdClusterDirection().put(Direction.DOWN, idNewCluster);

        }

    }

    public void processActionMoveWindowVisualObject(MoveWindowVisualObjectAction moveWindowVisualObjectAction) {

        Integer idCLusterTo = this.getIdClusterDirection().get(
                moveWindowVisualObjectAction.getDirectionTo()

        );

        if (idCLusterTo == null) {
            this.killVisualObject(moveWindowVisualObjectAction.getVisualObject());

        } else {
            String ip = this.getIpById(idCLusterTo);

            this.sendPrivate(
                    ip,
                    new PackageMoveWindowVisualObject(
                            moveWindowVisualObjectAction.getDirectionFrom(),
                            moveWindowVisualObjectAction.getVisualObject()

                    )

            );

        }

        this.removeVisualObject(moveWindowVisualObjectAction.getVisualObject());

    }

    public void onIncomingMessage(
            String ip,
            PackageMoveWindowVisualObject packageMoveWindowVisualObject

    ) {

        VisualObject visualObject = packageMoveWindowVisualObject.getVisualObject();

        Position newPositionVisualObject = this.getNewPositionVisualObject(
                visualObject,
                packageMoveWindowVisualObject.getDirectionFrom()

        );

        this.addVisualObject(visualObject, newPositionVisualObject);

    }

    private Position getNewPositionVisualObject(
            VisualObject visualObject,
            Direction directionFrom

    ) {

        Position position = new Position(
                visualObject.getPosition().getxPos(),
                visualObject.getPosition().getyPos()

        );

        if (directionFrom == Direction.UP) {
            // TODO: Tengo dudas de si aquí deberías ser 0 o el size de la pantalla
            position.setyPos(0);

        } else if (directionFrom == Direction.DOWN) {
            // TODO: Misma duda que arriba, el departamento visual me tiene que decir el
            // TODO: método para coger el tamaño de la pantalla (Alto y ancho)
            position.setyPos(100);

        } else if (directionFrom == Direction.LEFT) {
            position.setxPos(0);

        } else if (directionFrom == Direction.RIGHT) {
            // TODO: El departamento visual me tiene que decir el método para coger el
            // TODO: tamaño de la pantalla (Alto y ancho)
            position.setxPos(100);

        }

        return position;

    }

    // ! Linking Methods

    private int getMyId() {
        return this.getClusterCommunicationsController().getMyId();
    }

    private void killVisualObject(VisualObject visualObject) {
        this.getClusterCommunicationsController().killVisualObject(visualObject);

    }

    private String getIpById(int id) {
        return this.getClusterCommunicationsController().getIpById(id);
    }

    private void sendPrivate(String ip, PackageMoveWindowVisualObject packageMoveWindowVisualObject) {
        this.getClusterCommunicationsController().sendPrivate(ip, packageMoveWindowVisualObject);
    }

    private void removeVisualObject(VisualObject visualObject) {
        this.getClusterCommunicationsController().removeVisualObject(visualObject);
    }

    private void addVisualObject(VisualObject visualObject, Position newPositionVisualObject) {
        this.getClusterCommunicationsController().addVisualObject(
                visualObject, newPositionVisualObject

        );
    }

    // ! Getters and Setters

    /**
     * @return the clusterCommunicationsController
     */
    public ClusterCommunicationsController getClusterCommunicationsController() {
        return clusterCommunicationsController;
    }

    /**
     * @param clusterCommunicationsController the clusterCommunicationsController to
     *                                        set
     */
    public void setClusterCommunicationsController(ClusterCommunicationsController clusterCommunicationsController) {
        this.clusterCommunicationsController = clusterCommunicationsController;
    }

    /**
     * @return the idClusterDirection
     */
    public HashMap<Direction, Integer> getIdClusterDirection() {
        return idClusterDirection;
    }

    /**
     * @param idClusterDirection the idClusterDirection to set
     */
    public void setIdClusterDirection(HashMap<Direction, Integer> idClusterDirection) {
        this.idClusterDirection = idClusterDirection;
    }

}
