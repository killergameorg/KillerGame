package maincontroller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import events.Action;
import events.Colision;
import events.EventsGameController;
import events.ExplosionAction;
import lobby.MasterOrder;
import lobby.lobbyController.LobbyController;
import lobby.lobbyModel.GameRules;
import visual.Ship;
import visual.Direction;
import visual.Maps;
import visual.NotificationMsg;
import visual.Position;
import visual.VisualGameController;
import visual.VisualObject;

public class MainGameController {
    // TODO: Ordenar todos los métodos según sus departamentos

    // ! Attributes
    private MainGameModel mainGameModel;

    private LobbyController lobbyController;
    private EventsGameController eventsGameController;
    private VisualGameController visualGameController;

    // ! Constructor
    public MainGameController(String pathConfigurationFile) throws FileNotFoundException, IOException {

        this.setMainGameModel(new MainGameModel(this, pathConfigurationFile));

        this.setLobbyController(new LobbyController());
        this.setEventsGameController(new EventsGameController());
        this.setVisualGameController(new VisualGameController(this));

    }

    // ! MainGame

    public void initializeConnectionController() {
        this.getMainGameModel().initializeConnectionController();
    }

    public int getConfigurationFileId() {
        return this.getMainGameModel().getConfigurationFileController().getId();
    }

    // ! LobbyGame

    public void applyingToMaster() {
        this.tryApplyingToMaster();
    }

    private void tryApplyingToMaster() {
        this.getMainGameModel().tryApplyingToMaster();
    }

    public void setSlave() {
        this.getLobbyController().setSlave();
    }

    public void setMaster() {
        this.getLobbyController().setMaster();
    }

    public void startLobby() {
        this.getLobbyController().startLobby();
    }

    public void sendMessageToLobby(MasterOrder order) {
        this.getLobbyController().reciveMasterMsg(order);
    }

    public void sendPlayerCountToLobby(int playerCount) {
        this.getLobbyController().setPlayerCount(playerCount);
    }

    public void startGame(GameRules gameRules) {
        this.getMainGameModel().startGame(gameRules);
    }

    public void notifyNumberOfMobiles(int numberOfMobiles) {
        this.getLobbyController().setPlayerCount(numberOfMobiles);
    }

    public boolean iAmMaster() {
        return this.getLobbyController().iAmMaster();
    }

    // ! VisualGame

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
    public Ship createVisualObjectControllableSpaceShip(Long accountId) {
        return this.getVisualGameController().createSpaceship(accountId);
    }

    /**
     * Create a Bullet with the given accountId
     * 
     * @param accountId The id of the account that owns the bullet to create
     */
    public void createVisualObjectBullet(int accountId) {
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
    public void moveForwardVisualObject(int idAccount) {
        this.getVisualGameController().moveObject(this.getVisualObjectById(idAccount));
    }

    /**
     * Rotate a visual object in the given direction (LEFT or RIGHT)
     * 
     * @param visualObject The visual object to rotate
     * @param direction    The direction to rotate the visual object (LEFT or RIGHT)
     */
    public void rotateVisualObject(
            int idAccount,
            Direction direction

    ) {
        this.getVisualGameController().rotateObject(
                this.getVisualObjectById(idAccount),
                direction

        );
    }

    /**
     * Decrease the life of a visual object
     * 
     * @param visualObject  The visual object to decrease the life
     * @param lifeDowngrade The amount of life to decrease from the visual object
     */
    public void decreaseLifeVisualObject(VisualObject visualObject,
            float lifeDowngrade) {
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
     * @throws Exception
     */
    public void notifyMessage(NotificationMsg notificationMsg) throws Exception {
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

    // ! EventsGame
    public void setGameRules(GameRules gameRules) {
        this.getEventsGameController().setGameRules(gameRules);
    }

    public ArrayList<Action> processEvent(Colision colision) {
        return this.getEventsGameController().processEvent(colision);
    }

    public Maps getMap() {
        return this.getEventsGameController().getMap();
    }

    // TODO: Mirar de hacerlo en el Modelo o en otro sitio
    private VisualObject getVisualObjectById(int id) {
        ArrayList<VisualObject> visualObjects = this.getVisualObjects();
        VisualObject visualObject = null;
        int i = 0;
        while (visualObject != null && i < visualObjects.size()) {
            if (visualObjects.get(i).getAccountId() == id) {
                visualObject = visualObjects.get(i);
            }
            i++;
        }
        return visualObject;
    }

    // ! Getters and Setters

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

    /**
     * @return the lobbyController
     */
    public LobbyController getLobbyController() {
        return lobbyController;
    }

    /**
     * @param lobbyController the lobbyController to set
     */
    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

}
