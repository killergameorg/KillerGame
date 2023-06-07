package maincontroller.notifications;

import java.util.ArrayList;

import events.Action;
import events.Colision;
import events.MoveWindowVisualObjectAction;
import maincontroller.MainGameModel;
import visual.Direction;
import visual.DynamicVisualObject;
import visual.Position;
import visual.VisualObject;

public class NotificationProcessorPositionUpdate implements NotificationProcessor {

    // ! Attributes
    private MainGameModel mainGameModel;

    // ! Constructor
    public NotificationProcessorPositionUpdate(MainGameModel mainGameModel) {
        this.setMainGameModel(mainGameModel);
    }

    // ! NotificationsProcessor methods
    @Override
    public ArrayList<Action> processNotification(VisualObject visualObject) {

        if (!(visualObject instanceof DynamicVisualObject)) {
            /*
             * This should never be true, the visual department should
             * not send an object other than DynamicVisualObject
             */
            throw new IllegalArgumentException("The visual object must be dynamic");
        }

        DynamicVisualObject dynamicVisualObject = (DynamicVisualObject) visualObject;

        ArrayList<Action> actions = new ArrayList<Action>();

        actions.addAll(this.checkCollision(dynamicVisualObject));
        actions.addAll(this.checkWindowLimits(dynamicVisualObject));

        return actions;
    }

    // ! Private methods
    /**
     * Check if a visual object collides with another visual object
     * 
     * @param visualObject The visual object to check the collision
     * @return ArrayList<Action> with all the actions to process after the check
     */
    private ArrayList<Action> checkCollision(DynamicVisualObject dynamicVisualObject) {
        ArrayList<Action> actions = new ArrayList<Action>();

        Position positionFuture = dynamicVisualObject.getFuturePosition();
        ArrayList<VisualObject> visualObjects = this.getVisualObjects();

        for (int i = 0; i < visualObjects.size(); i++) {

            // TODO: Check the collision using the size of the objects, i need to know the
            // TODO: size of the objects to do this (Ask to the visual department)
            if (!visualObjects.get(i).equals(dynamicVisualObject) &&
                    visualObjects.get(i).getPosition().getxPos() == positionFuture.getxPos() &&
                    visualObjects.get(i).getPosition().getyPos() == positionFuture.getyPos()) {

                Colision colision = new Colision(dynamicVisualObject, visualObjects.get(i));
                actions.addAll(this.processEvent(colision));
            }

        }

        return actions;
    }

    private ArrayList<Action> checkWindowLimits(DynamicVisualObject dynamicVisualObject) {
        ArrayList<Action> actions = new ArrayList<Action>();

        Position positionFuture = dynamicVisualObject.getFuturePosition();

        // TODO: El departamento Visual me tiene que decir el tamaño de la ventana
        if (positionFuture.getyPos() > 100) {

            actions.add(new MoveWindowVisualObjectAction(
                    dynamicVisualObject,
                    Direction.DOWN,
                    Direction.UP

            ));

        } else if (positionFuture.getyPos() < 0) {

            actions.add(new MoveWindowVisualObjectAction(
                    dynamicVisualObject,
                    Direction.UP,
                    Direction.DOWN

            ));

        } else if (positionFuture.getxPos() > 100) {

            actions.add(new MoveWindowVisualObjectAction(
                    dynamicVisualObject,
                    Direction.LEFT,
                    Direction.RIGHT

            ));

        } else if (positionFuture.getxPos() < 0) {

            actions.add(new MoveWindowVisualObjectAction(
                    dynamicVisualObject,
                    Direction.RIGHT,
                    Direction.LEFT

            ));

        }

        return actions;

    }

    private ArrayList<Action> processEvent(Colision colision) {
        return this.getMainGameModel().processEvent(colision);
    }

    /**
     * Get all VisualObjects from the MainGameController
     * 
     * @return ArrayList<VisualObject> with all the visual objects
     */
    private ArrayList<VisualObject> getVisualObjects() {
        return this.getMainGameModel().getVisualObjects();
    }

    // ! Getters and Setters
    /**
     * @return the mainGameModel
     */
    public MainGameModel getMainGameModel() {
        return mainGameModel;
    }

    /**
     * @param mainGameModel the mainGameModel to set
     */
    public void setMainGameModel(MainGameModel mainGameModel) {
        this.mainGameModel = mainGameModel;
    }

}
