package visual;

import java.util.ArrayList;

import visual_package.VisualObject;

public class VisualGameModel {

    private VisualGameController visualGameController;

    private ArrayList<VisualObject> visualObjectsList;

    // * Constructor

    public VisualGameModel(VisualGameController visualGameController) {
        this.visualGameController = visualGameController;

        this.visualObjectsList = new ArrayList<>();
    }

    // * Getters

    public ArrayList<VisualObject> getVisualObjectsList() {
        return visualObjectsList;
    }

    

    // * Methods

    public void notifyToVGC(NotificationMsg notificationMsg) {
        visualGameController.notifyToMGC(notificationMsg);
    }

    public void addToVisualObjectList(VisualObject visualObject) {
        visualObjectsList.add(visualObject);
    }

    public void removeFromVisualObjectList(VisualObject visualObject) {
        visualObjectsList.remove(visualObject);
    }

    public void moveObject(VisualObject visualObject) {

    }

    public void updateObjectPosition(VisualObject visualObject) {

    }

    public void rotateObject(VisualObject visualObject, float) {

    }

    public void killObject(VisualObject visualObject) {

    }

    public void createBullet(int acountId) {

    }

    public void decreaseLife(VisualObject visualObject, float) {

    }

    public SpaceShip createSpaceship(int accountId) {

    }

    // ! Method for manage spaceships between screens
    public void addVisualObject(VisualObject visualObject, Position position) {

    }
}
