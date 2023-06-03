package maincontroller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import events.Action;
import events.Colision;
import events.ExplosionAction;
import lobby.MasterOrder;
import lobby.lobbyModel.GameRules;
import maincontroller.gameinfo.Account;
import maincontroller.gameinfo.GameState;
import maincontroller.maincommunications.MainGameCommunications;
import maincontroller.maincommunications.mobiles.packages.PackageJoystick;
import maincontroller.maincommunications.soundserver.packages.SoundType;
import maincontroller.notifications.NotificationsManager;
import visual.Direction;
import visual.Ship;
import visual.VisualObject;

public class MainGameModel {

    // ! Attributes
    private MainGameController mainGameController;

    private ConfigurationFileController configurationFileController;
    private MainGameCommunications mainGameCommunications;

    private NotificationsManager notificationsManager;

    private GameState gameState;
    private ArrayList<Account> accounts;

    // ! Constructor
    public MainGameModel(
            MainGameController mainGameController,
            String pathConfigurationFile,
            int sleepWhileKnowConnections,
            int timeToWaitForVotesFromConfig

    )
            throws FileNotFoundException, IOException {

        this.setMainGameController(mainGameController);

        this.setConfigurationFileController(
                new ConfigurationFileController(pathConfigurationFile)

        );
        this.setMainGameCommunications(
                new MainGameCommunications(
                        this,
                        sleepWhileKnowConnections,
                        timeToWaitForVotesFromConfig

                )

        );

        this.setNotificationsManager(new NotificationsManager(this));

        this.setGameState(GameState.UNDEFINED);
        this.setAccounts(new ArrayList<Account>());

    }

    // ! Methods
    public void startGame(GameRules gameRules) {

        this.setGameRules(gameRules);

        this.notifyAllStartGame(gameRules);

        // TODO: El equipo visual me tiene que indicar como quieren que les avise para
        // TODO: que se muestre su Frame
    }

    public void setGameRules(GameRules gameRules) {
        this.getMainGameController().setGameRules(gameRules);
    }

    public ArrayList<Action> processEvent(Colision colision) {
        return this.getMainGameController().processEvent(colision);
    }

    public ArrayList<VisualObject> getVisualObjects() {
        return this.getMainGameController().getVisualObjects();
    }

    public void updateVisualObjectPosition(VisualObject visualObject) {
        this.getMainGameController().updateVisualObjectPosition(visualObject);
    }

    public void decreaseLifeVisualObject(VisualObject visualObject, int lifeDowngrade) throws Exception {

        if (visualObject instanceof Ship) {
            Ship ship = (Ship) visualObject;

            this.getMainGameCommunications().decreaseLifeShip(
                    ship,
                    lifeDowngrade

            );

        }

        this.getMainGameController().decreaseLifeVisualObject(visualObject, lifeDowngrade);
    }

    public void processActionExplosion(ExplosionAction explosionAction) {
        this.playSound(SoundType.EXPLOSION);
        this.getMainGameController().processActionExplosion(explosionAction);
    }

    public int getMyId() {
        return this.getConfigurationFileController().getId();
    }

    public void initializeConnectionController() {
        this.getMainGameCommunications().initializeConnectionController();
    }

    public void tryApplyingToMaster() {
        this.getMainGameCommunications().tryApplyingToMaster();
    }

    public void startLobby() {
        this.getMainGameController().startLobby();
    }

    public void setMaster() {
        this.getMainGameController().setMaster();
    }

    public void setSlave() {
        this.getMainGameController().setSlave();
    }

    public void notifyJoystick(Account account, PackageJoystick packageJoystick) {

        if (this.getGameState() == GameState.LOBBY) {

            MasterOrder masterOrder = null;
            if (packageJoystick.isRotateLeft()) {
                masterOrder = MasterOrder.LEFT;
            } else if (packageJoystick.isRotateRight()) {
                masterOrder = MasterOrder.RIGHT;
            } else if (packageJoystick.isAccelerate()) {
                masterOrder = MasterOrder.OK;
            } else if (packageJoystick.isShoot()) {
                masterOrder = MasterOrder.BACK;
            }

            if (masterOrder != null) {
                this.getMainGameController().sendMessageToLobby(masterOrder);
            }

        } else if (this.getGameState() == GameState.GAME) {

            if (packageJoystick.isAccelerate()) {
                this.getMainGameController().moveForwardVisualObject(
                        account.getVisualObject()

                );

            } else if (packageJoystick.isShoot()) {

                // TODO: Preguntar a ver que les parece usar los sonidos así
                this.playSound(SoundType.LASER);
                this.getMainGameController().createVisualObjectBullet(
                        account.getIdAccount()

                );

            } else if (packageJoystick.isRotateRight()) {
                this.getMainGameController().rotateVisualObject(
                        account.getVisualObject(),
                        Direction.RIGHT

                );

            } else if (packageJoystick.isRotateLeft()) {
                this.getMainGameController().rotateVisualObject(
                        account.getVisualObject(),
                        Direction.LEFT

                );

            }

        }

    }

    private void playSound(SoundType soundType) {
        this.getMainGameCommunications().playSound(soundType);
    }

    public void notifyNumberOfMobiles(int numberOfMobiles) {
        this.getMainGameController().notifyNumberOfMobiles(numberOfMobiles);
    }

    private void notifyAllStartGame(GameRules gameRules) {
        this.getMainGameCommunications().notifyAllStartGame(gameRules);
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

    /**
     * @return the mainGameCommunications
     */
    public MainGameCommunications getMainGameCommunications() {
        return mainGameCommunications;
    }

    /**
     * @param mainGameCommunications the mainGameCommunications to set
     */
    public void setMainGameCommunications(MainGameCommunications mainGameCommunications) {
        this.mainGameCommunications = mainGameCommunications;
    }

}
