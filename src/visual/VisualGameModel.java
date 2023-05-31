package visual;

import java.util.ArrayList;

import visual_package.VisualObject;

public class VisualGameModel {

    private VisualGameController visualGameController;

    private ArrayList<VisualObject> visualObjectsList;


    private PhysicsEngine physicsEngine;
    private Animation deadBulletAnim;
    private Animation spawnBulletAnim;
    private Animation deadShipAnim;

    // * Constructor

    public VisualGameModel(VisualGameController visualGameController) {
        this.visualGameController = visualGameController;
        this.visualObjectsList = new ArrayList<>();


        this.physicsEngine = new PhysicsEngine();
        // todo animations


    }

    // * Getters

    public ArrayList<VisualObject> getVisualObjectsList() {
        return visualObjectsList;
    }


    public VisualGameController getVisualGameController() {
        return visualGameController;
    }

    public PhysicsEngine getPhysicsEngine() {
        return physicsEngine;
    }

    public Animation getDeadBulletAnim() {
        return deadBulletAnim;
    }

    public Animation getSpawnBulletAnim() {
        return spawnBulletAnim;
    }

    public Animation getDeadShipAnim() {
        return deadShipAnim;
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
        visualObject.calculateNewPosition();
    }

    public void updateObjectPosition(VisualObject visualObject) {
        visualObject.updatePosition();
    }

    public void rotateObject(VisualObject visualObject, Direction direction) {
        visualObject.updateRotation(direction);
    }

    public void killObject(VisualObject visualObject) {
        visualObject.kill();
    }

    public void createBullet(int acountId) {
        VisualObject spaceShip;

        for (VisualObject visualObject : visualObjectsList) {
            if (visualObject.getAccountId() == acountId) {
                spaceShip = visualObject;
                break;
            }
        }

        //todo
        Bullet newBullet = new Bullet(null, visualGameController.getAssetsManager().getBullet(), spaceShip.getPosition(), 1, acountId, this, null, getDeadBulletAnim(), getSpawnBulletAnim(), acountId, acountId, acountId, acountId, physicsEngine)
    }

    public void decreaseLife(VisualObject visualObject, float) {

    }

    public SpaceShip createSpaceship(int accountId) {

    }

    // ! Method for manage spaceships between screens
    public void addVisualObject(VisualObject visualObject, Position position) {

    }
}
