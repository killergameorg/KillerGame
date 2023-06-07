package maincontroller.maincommunications;

import java.util.ArrayList;

import communications.ConnectionController;
import communications.P2PCommListener;
import events.Action;
import events.MoveWindowVisualObjectAction;
import events.PointWinAction;
import lobby.lobbyModel.GameRules;
import maincontroller.MainGameModel;
import maincontroller.gameinfo.GameState;
import maincontroller.gameinfo.Team;
import maincontroller.maincommunications.clustercomputers.ClusterCommunicationsController;
import maincontroller.maincommunications.clustercomputers.packages.PackageClusterCommunications;
import maincontroller.maincommunications.mobiles.MobileCommunicationsController;
import maincontroller.maincommunications.mobiles.packages.PackageJoystick;
import maincontroller.maincommunications.mobiles.packages.PackageMobileCommunications;
import maincontroller.maincommunications.packages.PackageMainCommunications;
import maincontroller.maincommunications.packages.PackageRemoveConnection;
import maincontroller.maincommunications.packages.PackageGameState;
import maincontroller.maincommunications.proccessknownewconnection.KnowNewConnectionController;
import maincontroller.maincommunications.proccessknownewconnection.packages.PackageProccessKnowNewConnection;
import maincontroller.maincommunications.soundserver.SoundServerConnectionController;
import maincontroller.maincommunications.soundserver.packages.MusicType;
import maincontroller.maincommunications.soundserver.packages.SoundType;
import visual.Position;
import visual.VisualObject;

public class MainGameCommunications implements P2PCommListener {

    // ! Attributes
    private MainGameModel mainGameModel;

    private ConnectionController connectionController;

    private KnowNewConnectionController knowNewConnectionController;

    private SoundServerConnectionController soundServerConnectionController;
    private ClusterCommunicationsController clusterCommunicationsController;
    private MobileCommunicationsController mobileCommunicationsController;

    // ! Constructor
    public MainGameCommunications(
            MainGameModel mainGameModel

    ) {

        this.setMainGameModel(mainGameModel);

        this.setKnowNewConnectionController(new KnowNewConnectionController(
                this,
                this.getSleepWhileKnowConnections()

        ));

        this.setSoundServerConnectionController(new SoundServerConnectionController(this));
        this.setClusterCommunicationsController(new ClusterCommunicationsController(
                this,
                this.getTimeToWaitForVotesFromConfig()

        ));
        this.setMobileCommunicationsController(new MobileCommunicationsController(this));
    }

    // ! Methods

    public void initializeConnectionController() {
        this.setConnectionController(new ConnectionController());
        this.getConnectionController().setCommListener(this);
        this.getConnectionController().initialize();
    }

    public void tryApplyingToMaster() {

        do {

            try {
                Thread.sleep(this.getSleepWhileKnowConnections());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (this.getKnownConnections().size() > 0);

        this.getClusterCommunicationsController().tryApplyingToMaster();
    }

    public void startLobby() {
        this.getSoundServerConnectionController().playMusic(MusicType.CALMA);
        this.getMainGameModel().startLobby();
    }

    public void notifyAllStartGame(GameRules gameRules) {
        this.sendFlood(new PackageGameState(GameState.GAME, gameRules));
    }

    private void removeConnection(String ip) {

        boolean found = false;

        found = this.getKnowNewConnectionController().removeConnection(ip);

        if (!found) {
            found = this.getSoundServerConnectionController().removeConnection(ip);
        }
        if (!found) {
            found = this.getClusterCommunicationsController().removeConnection(ip);
        }
        if (!found) {
            found = this.getMobileCommunicationsController().removeConnection(ip);
        }

        if (found) {
            this.notifyNumberOfMobiles();
        }

    }

    // ! P2PCommListener methods

    @Override
    public void onNewConnection(String ip) {
        this.getKnowNewConnectionController().knowNewConnection(ip);
    }

    @Override
    public void onIncomingMessage(String ip, Object message) {
        if (message instanceof PackageMainCommunications) {
            PackageMainCommunications packageMainCommunications = (PackageMainCommunications) message;

            if (packageMainCommunications instanceof PackageProccessKnowNewConnection) {
                this.getKnowNewConnectionController().onIncomingMessage(
                        ip,
                        (PackageProccessKnowNewConnection) packageMainCommunications

                );

            } else if (packageMainCommunications instanceof PackageClusterCommunications) {
                this.getClusterCommunicationsController().onIncomingMessage(
                        ip,
                        (PackageClusterCommunications) packageMainCommunications

                );

            } else if (packageMainCommunications instanceof PackageMobileCommunications) {
                this.getMobileCommunicationsController().onIncomingMessage(
                        ip,
                        (PackageMobileCommunications) packageMainCommunications

                );

            } else if (packageMainCommunications instanceof PackageGameState) {
                PackageGameState packageGameState = (PackageGameState) packageMainCommunications;

                if (packageGameState.getGameState() == GameState.UNDEFINED) {
                    this.setGameState(GameState.UNDEFINED);

                } else if (packageGameState.getGameState() == GameState.LOBBY) {
                    this.setGameState(GameState.LOBBY);

                } else if (packageGameState.getGameState() == GameState.GAME) {
                    this.setGameRules(packageGameState.getGameRules());
                    this.setGameState(GameState.GAME);

                } else if (packageGameState.getGameState() == GameState.GAME_END) {
                    this.setGameState(GameState.GAME_END);

                }

            } else if (packageMainCommunications instanceof PackageRemoveConnection) {
                this.removeConnection(((PackageRemoveConnection) packageMainCommunications).getIp());

            } else if (packageMainCommunications instanceof Action) {
                Action action = (Action) packageMainCommunications;
                // TODO: Hacer una controladora para estas cosas
                if (action instanceof PointWinAction) {
                    PointWinAction pointWinAction = (PointWinAction) action;
                    this.loadPointWinAction(pointWinAction);

                }

            }

        }
    }

    @Override
    public void onConnectionClosed(String ip) {
        this.removeConnection(ip);
        this.sendFloodRemoveConnection(ip);
    }

    @Override
    public void onConnectionLost(String ip) {
        this.removeConnection(ip);
        this.sendFloodRemoveConnection(ip);
    }

    // ! Linking methods

    public int getMyId() {
        return this.getMainGameModel().getMyId();
    }

    public void setSoundServer(String ip) {
        this.getSoundServerConnectionController().setSoundServer(ip);
    }

    private int getSleepWhileKnowConnections() {
        return this.getMainGameModel().getSleepWhileKnowConnections();
    }

    private int getTimeToWaitForVotesFromConfig() {
        return this.getMainGameModel().getTimeToWaitForVotesFromConfig();
    }

    private ArrayList<String> getKnownConnections() {
        return this.getKnowNewConnectionController().getKnownConnections();
    }

    public void sendPrivate(String ip, PackageMainCommunications packageMainCommunications) {
        this.getConnectionController().sendPrivate(ip, packageMainCommunications);
    }

    public void sendFlood(PackageMainCommunications packageMainCommunications) {
        this.getConnectionController().sendFlood(packageMainCommunications);
    }

    public void setMaster() {
        this.getMainGameModel().setMaster();
    }

    public void setSlave() {
        this.getMainGameModel().setSlave();
    }

    public void playSound(SoundType soundType) {
        this.getSoundServerConnectionController().playSound(soundType);
    }

    public void notifyNumberOfMobiles(int numberOfMobiles) {
        this.getMainGameModel().notifyNumberOfMobiles(numberOfMobiles);
    }

    private void notifyNumberOfMobiles() {
        this.getMobileCommunicationsController().notifyNumberOfMobiles();
    }

    public void addMobile(String ip) {
        this.getMobileCommunicationsController().addMobile(ip);
    }

    public void addClusterComputer(int id, String ip) {
        this.getClusterCommunicationsController().addClusterComputer(id, ip);
    }

    public void addMobileInLobbyMaster(String ip) {
        this.getMobileCommunicationsController().addMobileInLobbyMaster(ip);
    }

    public boolean iAmMaster() {
        return this.getMainGameModel().iAmMaster();
    }

    public void setGameState(GameState gameState) {
        this.getMainGameModel().setGameState(gameState);
    }

    public GameState getGameState() {
        return this.getMainGameModel().getGameState();
    }

    public boolean isMobileMaster(int idAccount) {
        return this.getMobileCommunicationsController().isMobileMaster(idAccount);
    }

    public void notifyJoystick(int idAccount, PackageJoystick joystick) {
        this.getMainGameModel().notifyJoystick(idAccount, joystick);
    }

    private void sendFloodRemoveConnection(String ip) {
        this.sendFlood(new PackageRemoveConnection(ip));
    }

    private void loadPointWinAction(PointWinAction pointWinAction) {
        this.getMainGameModel().loadPointWinAction(pointWinAction);
    }

    public void processActionMoveWindowVisualObject(MoveWindowVisualObjectAction moveWindowVisualObjectAction) {
        this.getClusterCommunicationsController().processActionMoveWindowVisualObject(moveWindowVisualObjectAction);
    }

    public void killVisualObject(VisualObject visualObject) {
        this.getMainGameModel().killVisualObject(visualObject);
    }

    public void removeVisualObject(VisualObject visualObject) {
        this.getMainGameModel().removeVisualObject(visualObject);
    }

    public void addVisualObject(VisualObject visualObject, Position newPositionVisualObject) {
        this.getMainGameModel().addVisualObject(visualObject, newPositionVisualObject);
    }

    public void loadIdClusterDirection(int idNewCluster) {
        this.getClusterCommunicationsController().loadIdClusterDirection(idNewCluster);
    }

    public void setGameRules(GameRules gameRules) {
        this.getMainGameModel().setGameRules(gameRules);
    }

    public ArrayList<Team> getTeams() {
        return this.getMainGameModel().getTeams();
    }

    // ! Getters and Setters

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
     * @return the knowNewConnectionController
     */
    public KnowNewConnectionController getKnowNewConnectionController() {
        return knowNewConnectionController;
    }

    /**
     * @param knowNewConnectionController the knowNewConnectionController to set
     */
    public void setKnowNewConnectionController(KnowNewConnectionController knowNewConnectionController) {
        this.knowNewConnectionController = knowNewConnectionController;
    }

    /**
     * @return the connectionController
     */
    public ConnectionController getConnectionController() {
        return connectionController;
    }

    /**
     * @param connectionController the connectionController to set
     */
    public void setConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    /**
     * @return the soundServerConnectionController
     */
    public SoundServerConnectionController getSoundServerConnectionController() {
        return soundServerConnectionController;
    }

    /**
     * @param soundServerConnectionController the soundServerConnectionController to
     *                                        set
     */
    public void setSoundServerConnectionController(SoundServerConnectionController soundServerConnectionController) {
        this.soundServerConnectionController = soundServerConnectionController;
    }

    /**
     * @return the clusterCommunicationsController
     */
    public ClusterCommunicationsController getClusterCommunicationsController() {
        return clusterCommunicationsController;
    }

    /**
     * @param clusterCommunicationsController the clusterCommunicationsController to
     *                                        set
     */
    public void setClusterCommunicationsController(ClusterCommunicationsController clusterCommunicationsController) {
        this.clusterCommunicationsController = clusterCommunicationsController;
    }

    /**
     * @return the mobileCommunicationsController
     */
    public MobileCommunicationsController getMobileCommunicationsController() {
        return mobileCommunicationsController;
    }

    /**
     * @param mobileCommunicationsController the mobileCommunicationsController to
     *                                       set
     */
    public void setMobileCommunicationsController(MobileCommunicationsController mobileCommunicationsController) {
        this.mobileCommunicationsController = mobileCommunicationsController;
    }

}
