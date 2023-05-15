package visual_package;

public class VisualGameController {

    private MainGameController mainGameController;

    private VisualGameView visualGameView;
    private VisualGameModel visualGameModel;

    // * Constructor

    public VisualGameController(MainGameController mainGameController) {
        this.mainGameController = mainGameController;

        this.visualGameModel = new VisualGameModel(this);
        this.visualGameView = new VisualGameView();
    }

    // * Getters & Setters

    public VisualGameView getVisualGameView() {
        return visualGameView;
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

    // * Methods

    public void notifyToMGC(NotificationMsg notificationMsg) {

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
