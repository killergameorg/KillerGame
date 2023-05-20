package maincontroller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import events.Action;
import events.EventCollision;
import events.ExplosionAction;
import lobby.GameRules;
import maincontroller.gameinfo.Account;
import maincontroller.gameinfo.GameState;
import maincontroller.notifications.NotificationsManager;
import visual.VisualObject;

public class MainGameModel {

    // ! Attributes
    private MainGameController mainGameController;

    private ConfigurationFileController configurationFileController;
    private NotificationsManager notificationsManager;

    private GameState gameState;
    private ArrayList<Account> accounts;

    // ! Constructor
    public MainGameModel(String pathConfigurationFile) throws FileNotFoundException, IOException {

        this.setConfigurationFileController(new ConfigurationFileController(pathConfigurationFile));
        this.setNotificationsManager(new NotificationsManager(this));

        this.setGameState(GameState.UNDEFINED);
        this.setAccounts(new ArrayList<Account>());

    }

    // ! Methods
    public void startGame(GameRules gameRules, ArrayList<Account> accounts) {
        this.setAccounts(accounts);
        // TODO
    }

    public ArrayList<Action> processEvent(EventCollision eventCollision) {
        return this.getMainGameController().processEvent(eventCollision);
    }

    public ArrayList<VisualObject> getVisualObjects() {
        return this.getMainGameController().getVisualObjects();
    }

    public void updateVisualObjectPosition(VisualObject visualObject) {
        this.getMainGameController().updateVisualObjectPosition(visualObject);
    }

    public void decreaseLifeVisualObject(VisualObject visualObject, float lifeDowngrade) {
        this.getMainGameController().decreaseLifeVisualObject(visualObject, lifeDowngrade);
    }

    public void processActionExplosion(ExplosionAction explosionAction) {
        this.getMainGameController().processActionExplosion(explosionAction);
    }

    // ! Getters and Setters
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

    /**
     * @return the notificationsManager
     */
    public NotificationsManager getNotificationsManager() {
        return notificationsManager;
    }

    /**
     * @param notificationsManager the notificationsManager to set
     */
    public void setNotificationsManager(NotificationsManager notificationsManager) {
        this.notificationsManager = notificationsManager;
    }

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
     * @return the mainGameController
     */
    public MainGameController getMainGameController() {
        return mainGameController;
    }

    /**
     * @param mainGameController the mainGameController to set
     */
    public void setMainGameController(MainGameController mainGameController) {
        this.mainGameController = mainGameController;
    }

}
