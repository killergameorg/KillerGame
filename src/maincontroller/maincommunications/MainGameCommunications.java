package maincontroller.maincommunications;

import java.util.ArrayList;

import communications.ConnectionController;
import communications.P2PCommListener;
import lobby.lobbyModel.GameRules;
import maincontroller.MainGameModel;
import maincontroller.gameinfo.Account;
import maincontroller.gameinfo.GameState;
import maincontroller.maincommunications.clustercomputers.ClusterCommunicationsController;
import maincontroller.maincommunications.clustercomputers.proccessapplyingtomaster.PackageApplyingToMaster;
import maincontroller.maincommunications.mobiles.MobileCommunicationsController;
import maincontroller.maincommunications.mobiles.packages.PackageJoystick;
import maincontroller.maincommunications.mobiles.packages.PackageShipMobile;
import maincontroller.maincommunications.packages.PackageStartGame;
import maincontroller.maincommunications.proccessknownewconnection.KnowNewConnectionController;
import maincontroller.maincommunications.proccessknownewconnection.packages.PackageKnowNewConnection;
import maincontroller.maincommunications.soundserver.SoundServerConnectionController;
import maincontroller.maincommunications.soundserver.packages.MusicType;
import maincontroller.maincommunications.soundserver.packages.SoundType;
import visual.Ship;

public class MainGameCommunications implements P2PCommListener {

    // ! Attributes

    private MainGameModel mainGameModel;

    private KnowNewConnectionController knowNewConnectionController;

    private SoundServerConnectionController soundServerConnectionController;
    private ClusterCommunicationsController clusterCommunicationsController;
    private MobileCommunicationsController mobileCommunicationsController;

    private ConnectionController connectionController;
    private ArrayList<ClusterComputer> clusterComputers;
    private ArrayList<Mobile> mobiles;
    private ServerSound serverSound;

    private int sleepWhileKnowConnections;

    // ! Constructor

    public MainGameCommunications(
            MainGameModel mainGameModel,
            int sleepWhileKnowConnections,
            int timeToWaitForVotesFromConfig

    ) {

        this.setMainGameModel(mainGameModel);

        this.setKnowNewConnectionController(new KnowNewConnectionController(this));

        this.setSoundServerConnectionController(new SoundServerConnectionController(this));
        this.setClusterCommunicationsController(
                new ClusterCommunicationsController(
                        this,
                        timeToWaitForVotesFromConfig

                )

        );
        this.setMobileCommunicationsController(new MobileCommunicationsController(this));

        this.setClusterComputers(new ArrayList<ClusterComputer>());
        this.setMobiles(new ArrayList<Mobile>());

        this.setSleepWhileKnowConnections(sleepWhileKnowConnections);
    }

    // ! Methods

    public void initializeConnectionController() {
        this.setConnectionController(new ConnectionController());
        this.getConnectionController().setCommListener(this);
        this.getConnectionController().initialize();
    }

    public void sendBroadcastClusterComputers(Object object) {
        for (int i = 0; i < this.getClusterComputers().size(); i++) {
            this.getConnectionController().sendPrivate(
                    this.getClusterComputers().get(i).getIp(),
                    object

            );
        }
    }

    public void sendBroadcastMobiles(Object object) {
        for (int i = 0; i < this.getMobiles().size(); i++) {
            this.getConnectionController().sendPrivate(
                    this.getMobiles().get(i).getIp(),
                    object

            );
        }
    }

    public int getMyId() {
        return this.getMainGameModel().getMyId();
    }

    public void addClusterComputer(ClusterComputer clusterComputer) {
        this.getClusterComputers().add(clusterComputer);
    }

    public void addMobile(Mobile mobile) {

        if (this.getMobiles().size() == 0) {
            mobile.getAccount().setMaster(true);
        }
        this.getMobiles().add(mobile);

        this.notifyNumberOfMobiles(this.getMobiles().size());

    }

    private void removeConnection(String ip) {

        ArrayList<String> knownConnections = this.getKnownConnections();
        if (knownConnections.contains(ip)) {
            knownConnections.remove(ip);

        } else if (this.getServerSound().getIp().equals(ip)) {
            this.setServerSound(null);

        } else {
            boolean found = false;

            int index = 0;
            while (!found && index < this.getMobiles().size()) {
                Mobile mobile = this.getMobiles().get(index);
                if (mobile.getIp().equals(ip)) {

                    this.getMobiles().remove(index);
                    found = true;

                    if (mobile.getAccount().isMaster()) {
                        this.getMobileCommunicationsController().searchNewMaster();
                    }
                }
                index++;
            }

            index = 0;
            while (!found && index < this.getClusterComputers().size()) {
                if (this.getClusterComputers().get(index).getIp().equals(ip)) {
                    this.getClusterComputers().remove(index);
                    found = true;
                }
                index++;
            }

            if (found) {
                this.notifyNumberOfMobiles(this.getMobiles().size());
            }
        }
    }

    private ArrayList<String> getKnownConnections() {
        return this.getKnowNewConnectionController().getKnownConnections();
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

    public void setMaster() {
        this.getMainGameModel().setMaster();
    }

    public void setSlave() {
        this.getMainGameModel().setSlave();
    }

    public void sendPrivateMobile(String ip, PackageShipMobile packageShipMobile) {
        this.getConnectionController().sendPrivate(ip, packageShipMobile);
    }

    public GameState getGameState() {
        return this.getMainGameModel().getGameState();
    }

    public void notifyJoystick(Account account, PackageJoystick packageJoystick) {
        this.getMainGameModel().notifyJoystick(account, packageJoystick);
    }

    public void decreaseLifeShip(Ship ship, int lifeDowngrade) throws Exception {
        this.getMobileCommunicationsController().decreaseLifeShip(ship, lifeDowngrade);
    }

    public void playSound(SoundType soundType) {
        this.getSoundServerConnectionController().playSound(soundType);
    }

    private void notifyNumberOfMobiles(int numberOfMobiles) {
        this.getMainGameModel().notifyNumberOfMobiles(numberOfMobiles);
    }

    public void notifyAllStartGame(GameRules gameRules) {
        this.getSoundServerConnectionController().notifyAllStartGame();
        this.getClusterCommunicationsController().notifyAllStartGame(gameRules);
        // TODO: Me ha faltado hacer esto justo
        this.getMobileCommunicationsController().notifyAllStartGame();
    }

    private void startGame(PackageStartGame packageStartGame) {
        // TODO: Por ahora solo se utiliza para los GameRules, en el futuro podría
        // TODO: usarse para más cosas
        this.getMainGameModel().setGameRules(packageStartGame.getGameRules());
    }

    // ! P2PCommListener methods

    @Override
    public void onNewConnection(String ip) {
        this.getKnowNewConnectionController().knowNewConnection(ip);
    }

    @Override
    public void onIncomingMessage(String ip, Object message) {

        if (message instanceof PackageKnowNewConnection) {
            this.getKnowNewConnectionController().onIncomingMessage(
                    ip,
                    (PackageKnowNewConnection) message

            );

        } else if (message instanceof PackageApplyingToMaster) {
            this.getClusterCommunicationsController().onIncomingMessage(
                    ip,
                    (PackageApplyingToMaster) message

            );

        } else if (message instanceof PackageShipMobile) {
            try {
                this.getMobileCommunicationsController().onIncomingMessage(
                        ip,
                        (PackageShipMobile) message

                );
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (message instanceof PackageStartGame) {
            this.startGame((PackageStartGame) message);
        }

        else { // TODO: Esto deberíamos cambiarlo teniendo una Clase como paquete
            this.getSoundServerConnectionController().onIncomingMessage(ip, message);
        }

    }

    @Override
    public void onConnectionClosed(String ip) {
        this.removeConnection(ip);
    }

    @Override
    public void onConnectionLost(String ip) {
        this.removeConnection(ip);
        // TODO Preguntar a los demas sobre en que se podría aplicar este método
    }

    // ! Getters and setters

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
     * @return the clusterComputer
     */
    public ArrayList<ClusterComputer> getClusterComputers() {
        return clusterComputers;
    }

    /**
     * @param clusterComputers the clusterComputers to set
     */
    public void setClusterComputers(ArrayList<ClusterComputer> clusterComputers) {
        this.clusterComputers = clusterComputers;
    }

    /**
     * @return the mobiles
     */
    public ArrayList<Mobile> getMobiles() {
        return mobiles;
    }

    /**
     * @param mobiles the mobiles to set
     */
    public void setMobiles(ArrayList<Mobile> mobiles) {
        this.mobiles = mobiles;
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
     * @return the serverSound
     */
    public ServerSound getServerSound() {
        return serverSound;
    }

    /**
     * @param serverSound the serverSound to set
     */
    public void setServerSound(ServerSound serverSound) {
        this.serverSound = serverSound;
    }

    /**
     * @return the sleepWhileKnowConnections
     */
    public int getSleepWhileKnowConnections() {
        return sleepWhileKnowConnections;
    }

    /**
     * @param sleepWhileKnowConnections the sleepWhileKnowConnections to set
     */
    public void setSleepWhileKnowConnections(int sleepWhileKnowConnections) {
        this.sleepWhileKnowConnections = sleepWhileKnowConnections;
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

}
