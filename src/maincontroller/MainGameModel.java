package maincontroller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import events.Action;
import events.Colision;
import events.ExplosionAction;
import events.MoveWindowVisualObjectAction;
import events.PointWinAction;
import lobby.MasterOrder;
import lobby.lobbyModel.GameRules;
import maincontroller.gameinfo.Account;
import maincontroller.gameinfo.GameState;
import maincontroller.gameinfo.Team;
import maincontroller.gameinfo.TeamName;
import maincontroller.maincommunications.MainGameCommunications;
import maincontroller.maincommunications.mobiles.packages.PackageJoystick;
import maincontroller.maincommunications.mobiles.packages.PackageShipInfo;
import maincontroller.maincommunications.mobiles.packages.PackageShipMobile;
import maincontroller.maincommunications.packages.PackageMainCommunications;
import maincontroller.maincommunications.soundserver.packages.SoundType;
import maincontroller.maincommunications.typesofconnections.Mobile;
import maincontroller.notifications.NotificationsManager;
import visual.Direction;
import visual.Position;
import visual.SpaceShip;
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

        // TODO: Refactorizar todo esto
        ArrayList<Mobile> mobiles = this.getMainGameCommunications().getMobileCommunicationsController().getMobiles();

        int winScoreBlueTeam = 0;
        for (int i = 0; i < mobiles.size(); i++) {
            if (mobiles.get(i).getAccount().getTeam().getTeamName() == TeamName.RED) {
                winScoreBlueTeam++;
            }
        }

        int winScoreRedTeam = 0;
        for (int i = 0; i < mobiles.size(); i++) {
            if (mobiles.get(i).getAccount().getTeam().getTeamName() == TeamName.BLUE) {
                winScoreRedTeam++;
            }
        }

        gameRules.getStaticGameRule().getWinScore().put(TeamName.RED, winScoreRedTeam);
        gameRules.getStaticGameRule().getWinScore().put(TeamName.BLUE, winScoreBlueTeam);

        // TODO: Refactorizar hasta aqui

        this.setGameRules(gameRules);

        this.setGameState(GameState.GAME);
        this.notifyAllStartGame(gameRules);

        // * Notify to VisualGameController
        this.setVisualGameController();
        for (int i = 0; i < this.getAccounts().size(); i++) {
            Account account = this.getAccounts().get(i);

            account.setVisualObject(
                    this.createVisualObjectControllableSpaceShip(
                            account.getIdAccount(),
                            account.getTeam()

                    )

            );
        }

    }

    public void decreaseLifeVisualObject(VisualObject visualObject, float lifeDowngrade) throws Exception {

        if (visualObject instanceof SpaceShip) {
            SpaceShip ship = (SpaceShip) visualObject;

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

    public VisualObject getVisualObjectById(int id) {
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

    public Team contraryTeam(TeamName teamName) {
        this.getTeams();

        TeamName contraryTeamName = null;

        if (teamName == TeamName.BLUE) {
            contraryTeamName = TeamName.RED;
        } else if (teamName == TeamName.RED) {
            contraryTeamName = TeamName.BLUE;
        }

        Team team = null;

        boolean found = false;
        int i = 0;
        while (!found && i < this.getTeams().size()) {
            if (this.getTeams().get(i).getTeamName() == contraryTeamName) {
                team = this.getTeams().get(i);
            }
            i++;
        }

        return team;
    }

    // ! Linking Methods

    public void processActionExplosion(ExplosionAction explosionAction) {
        this.playSound(SoundType.EXPLOSION);
        this.getMainGameController().processActionExplosion(explosionAction);
    }

    public void processActionMoveWindowVisualObject(MoveWindowVisualObjectAction moveWindowVisualObjectAction) {
        this.getMainGameCommunications().processActionMoveWindowVisualObject(moveWindowVisualObjectAction);

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

    public void playSound(SoundType soundType) {
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

    public void killVisualObject(VisualObject visualObject) {
        this.getMainGameController().killVisualObject(visualObject);
    }

    public void removeVisualObject(VisualObject visualObject) {
        this.getMainGameController().removeVisualObject(visualObject);
    }

    public void addVisualObject(VisualObject visualObject, Position newPositionVisualObject) {
        this.getMainGameController().addVisualObject(visualObject, newPositionVisualObject);
    }

    private void setVisualGameController() {
        this.getMainGameController().setVisualGameController();
    }

    private SpaceShip createVisualObjectControllableSpaceShip(int accountId, Team team) {
        return this.getMainGameController().createVisualObjectControllableSpaceShip(accountId, team);
    }

    public void startEndGame() {

        Team team1 = null;
        Team team2 = null;
        for (int i = 0; i < this.getTeams().size(); i++) {
            Team team = this.getTeams().get(i);
            if (team.getTeamName() != TeamName.MACHINE) {
                if (team1 == null) {
                    team1 = team;
                } else if (team2 == null) {
                    team2 = team;
                }
            }
        }


        this.getMainGameController().startEndGame(team1, team2);
    }

    public int getScreenHeight() {
        return this.getMainGameController().getScreenHeight();
    }

    public int getScreenWidth() {
        return this.getMainGameController().getScreenWidth();
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
