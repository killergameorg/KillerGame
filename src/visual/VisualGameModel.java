package visual;

import java.util.ArrayList;
import java.util.Random;

import maincontroller.gameinfo.Team;
import maincontroller.gameinfo.TeamName;

public class VisualGameModel {

    private VisualGameController visualGameController;
    
    private ArrayList<VisualObject> visualObjectsList;
    
    private PhysicsEngine physicsEngine;
    private Animation deadBulletAnim;
    private Animation spawnBulletAnim;
    private Animation deadShipAnim;

    private Random random;

    // * Constructor

    public VisualGameModel(VisualGameController visualGameController) {
        this.visualGameController = visualGameController;
        this.visualObjectsList = new ArrayList<>();

        this.physicsEngine = new PhysicsEngine();
        this.random  = new Random();
        
        this.deadBulletAnim = new Animation(false, VisualConstants.impactexplosionduration, visualGameController.getAssetsManager().getBulletExplosionAnimation());
        this.deadShipAnim = new Animation(false, VisualConstants.spaceshipexplosionduration, visualGameController.getAssetsManager().getSpaceShipExplosionAnimation());
        this.spawnBulletAnim = new Animation(false, VisualConstants.shootbulletduration, visualGameController.getAssetsManager().getLaserSparkleAnimation());
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
        visualObject.setIsAlive(false);
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
        visualObject.kill(visualGameController.getVisualGameView().getBufferStrategyGraphics());
        visualObjectsList.remove(visualObject);
    }

    public void createBullet(int acountId) {
        // ? CHECK SI ESTO FUNCIONA COMO TOCA
        VisualObject spaceShipParent = null;

        for (VisualObject visualObject : visualObjectsList) {
            if (visualObject.getAccountId() == acountId) {
                spaceShipParent = visualObject;
                break;
            }
        }

        Bullet newBullet = new Bullet(0, visualGameController.getAssetsManager().getBullet(),
                spaceShipParent.getPosition(), 1, acountId, this, 0, getDeadBulletAnim(), getSpawnBulletAnim(),
                VisualConstants.velocityBullet, spaceShipParent.getAngle());

        addToVisualObjectList(newBullet);
    }

    public void decreaseLife(VisualObject visualObject, float damage) {
        visualObject.decreaseLife(damage);
    }

    public SpaceShip createSpaceship(int accountId, Team team) {

        double maxX = (double) visualGameController.getVisualGameView().getScreenWidth();
        double maxY = (double) visualGameController.getVisualGameView().getScreenHeight();
        double randomX = random.nextDouble(maxX);
        double randomY = random.nextDouble(maxY);

        SpaceShip newSpaceShip = new SpaceShip(0,
                team.getTeamName() == TeamName.RED ? visualGameController.getAssetsManager().getSpaceShipA()
                        : visualGameController.getAssetsManager().getSpaceShipB(),
                new Position(randomX, randomY), 100, accountId, this, 0, getDeadBulletAnim(), getSpawnBulletAnim(),
                VisualConstants.velocitySpaceship, 0);

        addToVisualObjectList(newSpaceShip);

        return newSpaceShip;
    }

    // ! Method for manage spaceships between screens
    public void addVisualObject(VisualObject visualObject, Position position) {
        DynamicVisualObject aux = (DynamicVisualObject) visualObject;

        aux.setPosition(position);
        aux.setFuturePosition(null);

        addToVisualObjectList(visualObject);
    }
}
