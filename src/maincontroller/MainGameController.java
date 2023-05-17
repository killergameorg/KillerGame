package maincontroller;

import java.io.FileNotFoundException;
import java.io.IOError;
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
import visual.ControllableSpaceShip;
import visual.Direction;
import visual.DynamicVisualObject;
import visual.NotificationMsg;
import visual.NotificationType;
import visual.Position;
import visual.VisualGameController;
import visual.VisualObject;

public class MainGameController {
    // TODO: Move all private methods to Model

    // ! Attributes
    private ConfigurationFileController configurationFileController;

    private LobbyGameController lobbyGameController;
    private EventsGameController eventsGameController;
    private VisualGameController visualGameController;

    private GameState gameState;
    private ArrayList<Account> accounts;

    // ! Constructor
    public MainGameController(String pathConfigurationFile) throws FileNotFoundException, IOException {
        this.setConfigurationFileController(new ConfigurationFileController(pathConfigurationFile));

        this.setGameState(GameState.UNDEFINED);
        this.setAccounts(new ArrayList<Account>());
    }

    // ! Methods

    // * Configuration File
    public int getConfigurationFileId() {
        return this.getConfigurationFileController().getId();
    }

    // * Lobby
    public void applyingToMaster() {
        // TODO
    }

    public void startGame(GameRules gameRules, ArrayList<Account> accounts) {
        this.setAccounts(accounts);
        // TODO
    }

    // * Visual
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
        ControllableSpaceShip controllableSpaceShip = this.getVisualGameController().createSpaceship(accountId);
        return controllableSpaceShip;
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

        VisualObject visualObject = notificationMsg.getVisualObject();
        NotificationType notificationType = notificationMsg.getNotificationType();

        ArrayList<Action> actions = new ArrayList<Action>();
        HashMap<NotificationType, Runnable> notificationMap = new HashMap<NotificationType, Runnable>();

        notificationMap.put(
                NotificationType.POSITION_UPDATE,
                () -> actions.addAll(this.notifyMessagePositionUpdate(visualObject)));

        // Can add more notifications here if needed in the future

        notificationMap.get(notificationType).run();

        if (actions.size() == 0) {
            this.updateVisualObjectPosition(visualObject);
        } else {
            this.processActions(actions);
        }

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
     * Process a MessagePositionUpdate checking all possibles outcomes
     * 
     * @param visualObject The visual object to check
     * @return ArrayList<Action> with all the actions to process after the check
     */
    private ArrayList<Action> notifyMessagePositionUpdate(VisualObject visualObject) {
        ArrayList<Action> actions = new ArrayList<Action>();

        actions.addAll(this.checkCollision(visualObject));

        return actions;
    }

    /**
     * Check if a visual object collides with another visual object
     * 
     * @param visualObject The visual object to check the collision
     * @return ArrayList<Action> with all the actions to process after the check
     */
    private ArrayList<Action> checkCollision(VisualObject visualObject) {
        ArrayList<Action> actions = new ArrayList<Action>();

        if (!(visualObject instanceof DynamicVisualObject)) {
            /*
             * This should never be true, the visual department should
             * not send an object other than DynamicVisualObject
             */
            throw new IllegalArgumentException("The visual object must be dynamic");

        } else {
            Position positionFuture = ((DynamicVisualObject) visualObject).getPositionFuture();
            ArrayList<VisualObject> visualObjects = this.getVisualObjects();

            for (int i = 0; i < visualObjects.size(); i++) {

                // TODO: Check the collision using the size of the objects, i need to know the
                // TODO: size of the objects to do this (Ask to the visual department)
                if (!visualObjects.get(i).equals(visualObject) &&
                        visualObjects.get(i).getPosition().equals(positionFuture)) {
                    EventCollision eventCollision = new EventCollision(visualObject, visualObjects.get(i));
                    actions.addAll(this.getEventsGameController().processEvent(eventCollision));
                }

            }

        }

        return actions;
    }

    /**
     * Process a list of actions to execute
     * 
     * @param actions The list of actions to execute
     */
    private void processActions(ArrayList<Action> actions) {

        for (int i = 0; i < actions.size(); i++) {
            Action action = actions.get(i);

            if (action instanceof VisualObjectsActions) {
                this.processActionVisualObject((VisualObjectsActions) action);
            } else if (action instanceof TeamActions) {
                this.processActionTeam((TeamActions) action);
            }

        }
    }

    /**
     * Process an action of a visual object (life decrease, explosion, etc)
     * 
     * @param visualObjectsActions The action to process
     */
    private void processActionVisualObject(VisualObjectsActions visualObjectsActions) {
        boolean canMove = true;

        if (visualObjectsActions instanceof LifeDecreaseAction) {
            this.processActionLifeDecrease((LifeDecreaseAction) visualObjectsActions);
        } else if (visualObjectsActions instanceof ExplosionAction) {
            this.processActionExplosion((ExplosionAction) visualObjectsActions);

            canMove = false; // The object can't move
        }

        if (canMove) {
            this.updateVisualObjectPosition(visualObjectsActions.getVisualObject());
        }
    }

    /**
     * Process an action of a team (game win, etc)
     * 
     * @param teamActions The action to process
     */
    private void processActionTeam(TeamActions teamActions) {
        if (teamActions instanceof GameWinAction) {
            this.processActionGameWin((GameWinAction) teamActions);
        }
    }

    /**
     * Process an action of life decrease of a visual object
     * 
     * @param lifeDecreaseAction The action to process
     */
    private void processActionLifeDecrease(LifeDecreaseAction lifeDecreaseAction) {
        this.decreaseLifeVisualObject(
                lifeDecreaseAction.getVisualObject(),
                lifeDecreaseAction.getLifeDowngrade()

        );
    }

    /**
     * Process an action of explosion of a visual object
     * 
     * @param explosionAction The action to process
     */
    private void processActionExplosion(ExplosionAction explosionAction) {
        // TODO: Ask to the visual department about the kill visual object
        this.killVisualObject(explosionAction.getVisualObject());
    }

    private void processActionGameWin(GameWinAction gameWinAction) {
        // TODO
    }

    // ! Getters and Setters
    /**
     * @return the gameState
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * @param gameState the gameState to set
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * @return the accounts
     */
    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    /**
     * @param accounts the accounts to set
     */
    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

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
     * @return the configurationFileController
     */
    public ConfigurationFileController getConfigurationFileController() {
        return configurationFileController;
    }

    /**
     * @param configurationFileController the configurationFileController to set
     */
    public void setConfigurationFileController(ConfigurationFileController configurationFileController) {
        this.configurationFileController = configurationFileController;
    }

}
