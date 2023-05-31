package visual;

import maincontroller.MainGameController;

public class VisualGameController {

    private MainGameController mainGameController;

    private VisualGameView visualGameView;
    private VisualGameModel visualGameModel;

    private AssetsManager assetsManager;

    // * Constructor

    public VisualGameController(MainGameController mainGameController) {
        this.mainGameController = mainGameController;

        this.visualGameModel = new VisualGameModel(this);
        this.visualGameView = new VisualGameView(this);

        this.assetsManager = new AssetsManager();
    }

    // * Getters & Setters

    public VisualGameView getVisualGameView() {
        return visualGameView;
    }

    public MainGameController getMainGameController() {
        return mainGameController;
    }

    public void setVisualGameView(VisualGameView visualGameView) {
        this.visualGameView = visualGameView;
    }

    public VisualGameModel getVisualGameModel() {
        return visualGameModel;
    }

    public void setVisualGameModel(VisualGameModel visualGameModel) {
        this.visualGameModel = visualGameModel;
    }

    
    public AssetsManager getAssetsManager() {
        return assetsManager;
    }

    // * Methods

    public void notifyToMGC(NotificationMsg notificationMsg) {
        // todo
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
