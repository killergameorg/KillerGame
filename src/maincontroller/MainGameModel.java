package maincontroller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import events.Action;
import events.Colision;
import events.ExplosionAction;
import events.PointWinAction;
import lobby.MasterOrder;
import lobby.lobbyModel.GameRules;
import maincontroller.gameinfo.Account;
import maincontroller.gameinfo.GameState;
import maincontroller.gameinfo.Team;
import maincontroller.maincommunications.MainGameCommunications;
import maincontroller.maincommunications.mobiles.packages.PackageJoystick;
import maincontroller.maincommunications.mobiles.packages.PackageShipInfo;
import maincontroller.maincommunications.mobiles.packages.PackageShipMobile;
import maincontroller.maincommunications.packages.PackageMainCommunications;
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
    private ArrayList<Team> teams;
    private ArrayList<Account> accounts;

    // ! Constructor
    public MainGameModel(
            MainGameController mainGameController,
            String pathConfigurationFile

    ) throws FileNotFoundException, IOException {

        this.setMainGameController(mainGameController);

        this.setConfigurationFileController(
                new ConfigurationFileController(pathConfigurationFile)

        );
        this.setMainGameCommunications(new MainGameCommunications(this));

        this.setNotificationsManager(new NotificationsManager(this));

        this.setGameState(GameState.UNDEFINED);
        this.setAccounts(new ArrayList<Account>());

    }

    // ! Methods
    public void startGame(GameRules gameRules) {

        this.setGameRules(gameRules);

        this.setGameState(GameState.GAME);
        this.notifyAllStartGame(gameRules);

        // TODO: El equipo visual me tiene que indicar como quieren que les avise para
        // TODO: que se muestre su Frame
    }

    public void decreaseLifeVisualObject(VisualObject visualObject, int lifeDowngrade) throws Exception {

        if (visualObject instanceof Ship) {
            Ship ship = (Ship) visualObject;

            this.sendFlood(
                    new PackageShipMobile(
                            ship.getAccountId(),
                            this.isMobileMaster(ship.getAccountId()),
                            new PackageShipInfo(
                                    ship.getLife() - lifeDowngrade,
                                    ship.getTeam()

                            )

                    )

            );

        }

        this.getMainGameController().decreaseLifeVisualObject(visualObject, lifeDowngrade);
    }

    public void notifyJoystick(int idAccount, PackageJoystick packageJoystick) {

        if (this.getGameState() == GameState.LOBBY) {

            if (packageJoystick.isRotateLeft()) {
                this.sendMessageToLobby(MasterOrder.LEFT);
            }
            if (packageJoystick.isRotateRight()) {
                this.sendMessageToLobby(MasterOrder.RIGHT);
            }
            if (packageJoystick.isAccelerate()) {
                this.sendMessageToLobby(MasterOrder.OK);
            }
            if (packageJoystick.isShoot()) {
                this.sendMessageToLobby(MasterOrder.BACK);
            }

        } else if (this.getGameState() == GameState.GAME) {

            if (packageJoystick.isAccelerate()) {
                this.getMainGameController().moveForwardVisualObject(
                        idAccount

                );

            } else if (packageJoystick.isShoot()) {

                // TODO: Preguntar a ver que les parece usar los sonidos así
                this.playSound(SoundType.LASER);
                this.getMainGameController().createVisualObjectBullet(
                        idAccount

                );

            } else if (packageJoystick.isRotateRight()) {
                this.getMainGameController().rotateVisualObject(
                        idAccount,
                        Direction.RIGHT

                );

            } else if (packageJoystick.isRotateLeft()) {
                this.getMainGameController().rotateVisualObject(
                        idAccount,
                        Direction.LEFT

                );

            }

        }

    }

    public void processActionPointWin(PointWinAction pointWinAction) {
        pointWinAction.getTeam().setScore(
                pointWinAction.getTeam().getScore() + 1

        );

        this.loadPointWinAction(pointWinAction);

        this.sendFlood(pointWinAction);
    }

    public synchronized void loadPointWinAction(PointWinAction pointWinAction) {

        boolean load = false;
        int i = 0;
        while (!load && i < this.getTeams().size()) {
            if (this.getTeams().get(i).getTeamName() == pointWinAction.getTeam().getTeamName()) {
                this.getTeams().get(i).setScore(pointWinAction.getTeam().getScore());
                load = true;
            }
            i++;
        }
    }

    // ! Linking Methods

    public void processActionExplosion(ExplosionAction explosionAction) {
        this.playSound(SoundType.EXPLOSION);
        this.getMainGameController().processActionExplosion(explosionAction);
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

    private void playSound(SoundType soundType) {
        this.getMainGameCommunications().playSound(soundType);
    }

    public void notifyNumberOfMobiles(int numberOfMobiles) {
        this.getMainGameController().notifyNumberOfMobiles(numberOfMobiles);
    }

    private void notifyAllStartGame(GameRules gameRules) {
        this.getMainGameCommunications().notifyAllStartGame(gameRules);
    }

    public boolean iAmMaster() {
        return this.getMainGameController().iAmMaster();
    }

    public void sendMessageToLobby(MasterOrder masterOrder) {
        this.getMainGameController().sendMessageToLobby(masterOrder);
    }

    private boolean isMobileMaster(int idAccount) {
        return this.getMainGameCommunications().isMobileMaster(idAccount);
    }

    private void sendFlood(PackageMainCommunications packageMainCommunications) {
        this.getMainGameCommunications().sendFlood(packageMainCommunications);
    }

    public int getSleepWhileKnowConnections() {
        return this.getConfigurationFileController().getSleepWhileKnowConnections();
    }

    public int getTimeToWaitForVotesFromConfig() {
        return this.getConfigurationFileController().getTimeToWaitForVotesFromConfig();
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

    /**
     * @return the teams
     */
    public ArrayList<Team> getTeams() {
        return teams;
    }

    /**
     * @param teams the teams to set
     */
    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

}
