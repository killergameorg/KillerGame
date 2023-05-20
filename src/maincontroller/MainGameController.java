package maincontroller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import events.Action;
import events.EventCollision;
import events.EventsGameController;
import events.ExplosionAction;
import events.GameWinAction;
import events.LifeDecreaseAction;
import events.TeamActions;
import events.VisualObjectsActions;
import lobby.GameRules;
import lobby.LobbyGameController;
import maincontroller.gameinfo.Account;
import maincontroller.notifications.NotificationProcessor;
import maincontroller.notifications.NotificationProcessorPositionUpdate;
import maincontroller.notifications.NotificationsManager;
import visual.ControllableSpaceShip;
import visual.Direction;
import visual.DynamicVisualObject;
import visual.NotificationMsg;
import visual.NotificationType;
import visual.Position;
import visual.VisualGameController;
import visual.VisualObject;

public class MainGameController {
    // TODO: Ordenar todos los métodos según sus departamentos

    // ! Attributes
    private MainGameModel mainGameModel;

    private LobbyGameController lobbyGameController;
    private EventsGameController eventsGameController;
    private VisualGameController visualGameController;

    // ! Constructor
    public MainGameController(String pathConfigurationFile) throws FileNotFoundException, IOException {

        this.setMainGameModel(new MainGameModel(pathConfigurationFile));

    }

    // ! ConfigurationFileController methods

    public int getConfigurationFileId() {
        return this.getMainGameModel().getConfigurationFileController().getId();
    }

    // ! LobbyGameController methods

    public void applyingToMaster() {
        // TODO
    }

    public void startGame(GameRules gameRules, ArrayList<Account> accounts) {
        this.getMainGameModel().startGame(gameRules, accounts);
    }

    // ! VisualGameController methods

    /**
     * Get all the visual objects in the game (spaceships, bullets, etc)
     * 
     * @return ArrayList<VisualObject> with all the visual objects in the game
     */
    public ArrayList<VisualObject> getVisualObjects() {
        return this.getVisualGameController().getVisualObjects();
    }

    /**
     * Create a ControllableSpaceShip with the given accountId
     * 
     * @param accountId The id of the account that owns the spaceship to create
     * @return The ControllableSpaceShip created
     */
    public ControllableSpaceShip createVisualObjectControllableSpaceShip(Long accountId) {
        return this.getVisualGameController().createSpaceship(accountId);
    }

    /**
     * Create a Bullet with the given accountId
     * 
     * @param accountId The id of the account that owns the bullet to create
     */
    public void createVisualObjectBullet(Long accountId) {
        this.getVisualGameController().createBullet(accountId);
    }

    /**
     * Add a visual object to the game in the given Position
     * 
     * @param visualObject The visual object to add to the game
     * @param position     The position where the visual object will be added
     */
    public void addVisualObject(VisualObject visualObject, Position position) {
        this.getVisualGameController().addVisualObject(visualObject, position);
    }

    /**
     * Move a visual object forward
     * 
     * @param visualObject The visual object to move forward
     */
    public void moveForwardVisualObject(VisualObject visualObject) {
        this.getVisualGameController().moveObject(visualObject);
    }

    /**
     * Rotate a visual object in the given direction (LEFT or RIGHT)
     * 
     * @param visualObject The visual object to rotate
     * @param direction    The direction to rotate the visual object (LEFT or RIGHT)
     */
    public void rotateVisualObject(VisualObject visualObject, Direction direction) {
        this.getVisualGameController().rotateObject(visualObject, direction);
    }

    /**
     * Decrease the life of a visual object
     * 
     * @param visualObject  The visual object to decrease the life
     * @param lifeDowngrade The amount of life to decrease from the visual object
     */
    public void decreaseLifeVisualObject(VisualObject visualObject, float lifeDowngrade) {
        this.getVisualGameController().decreaseLife(visualObject, lifeDowngrade);
    }

    /**
     * Kill a visual object
     * 
     * @param visualObject The visual object to kill
     */
    public void killVisualObject(VisualObject visualObject) {
        this.getVisualGameController().killObject(visualObject);
    }

    /**
     * Notify a message to the visual department (position update, etc)
     * 
     * @param notificationMsg The notification message to send to the visual
     *                        department
     */
    public void notifyMessage(NotificationMsg notificationMsg) {
        this.getMainGameModel().getNotificationsManager().processNotification(notificationMsg);
    }

    /**
     * Update the position of a visual object in the visual department
     * 
     * @param visualObject The visual object to update the position
     */
    public void updateVisualObjectPosition(VisualObject visualObject) {
        this.getVisualGameController().updateObjectPosition(visualObject);
    }

    /**
     * Process an action of explosion of a visual object
     * 
     * @param explosionAction The action to process
     */
    public void processActionExplosion(ExplosionAction explosionAction) {
        // TODO: Ask to the visual department about the kill visual object
        this.killVisualObject(explosionAction.getVisualObject());
    }

    public ArrayList<Action> processEvent(EventCollision eventCollision) {
        return this.getEventsGameController().processEvent(eventCollision);
    }

    // ! Getters and Setters
    /**
     * @return the lobbyGameController
     */
    public LobbyGameController getLobbyGameController() {
        return lobbyGameController;
    }

    /**
     * @param lobbyGameController the lobbyGameController to set
     */
    public void setLobbyGameController(LobbyGameController lobbyGameController) {
        this.lobbyGameController = lobbyGameController;
    }

    /**
     * @return the eventsGameController
     */
    public EventsGameController getEventsGameController() {
        return eventsGameController;
    }

    /**
     * @param eventsGameController the eventsGameController to set
     */
    public void setEventsGameController(EventsGameController eventsGameController) {
        this.eventsGameController = eventsGameController;
    }

    /**
     * @return the visualGameController
     */
    public VisualGameController getVisualGameController() {
        return visualGameController;
    }

    /**
     * @param visualGameController the visualGameController to set
     */
    public void setVisualGameController(VisualGameController visualGameController) {
        this.visualGameController = visualGameController;
    }

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
