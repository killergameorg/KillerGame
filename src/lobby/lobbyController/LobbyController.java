package lobby.lobbyController;

import lobby.MasterOrder;
import lobby.MasterStatus;
import lobby.lobbyModel.GameRules;
import lobby.lobbyModel.LobbyModel;
import lobby.lobbyView.LobbyView;
import maincontroller.MainGameController;

public class LobbyController {

    // Atributos
    private MainGameController mainGameController;
    private MasterStatus status;
    private LobbyView lobbyView;
    private LobbyModel lobbyModel;

    // Constructor
    // When created, it asks the maincontroller to apply the master
    public LobbyController() {
        this.mainGameController.applyingToMaster();
        this.status = MasterStatus.ApplyingToMaster;
    }

    // Metodos
    /*
     * In case of being the lobbyMaster the following function is executed,
     * The object view and lobbymodel are created.
     */
    public void startLobby() {
        LobbyView lobbyView = new LobbyView(this);
        this.setLobbyView(lobbyView);
        LobbyModel lobbyModel = new LobbyModel(this);
        this.setLobbyModel(lobbyModel);
    }

    /**
     * The room master sends an enum Left or Right to change the position of rule
     * selected ,also enum Ok or Back is used to increase or decrease the value of
     * the rule.
     * 
     * @param order enum Left, Right, Ok, Back
     */
    public void reciveMasterMsg(MasterOrder order) {
        lobbyModel.reciveMasterMsg(order);
    }

    /**
     * When the room master decides to start the game, it is passed to the
     * maingamecontroller.
     * gameRule.
     * 
     * @param gameRules
     */
    public void startGame(GameRules gameRules) {
        this.mainGameController.startGame(gameRules);
        this.lobbyView.dispose();
    }

    /**
     * This method is used to update the view by increasing or decreasing the
     * amount of player.
     * 
     * @param numPlayer a number indicating the number of players.
     */
    public void setPlayerCount(int numPlayer) {
        this.lobbyView.refreshPlayerNum(numPlayer);
    }

    /*
     * Afther using method mainGameController.applyingToMaster()
     * maincontroller will decide, this object is master or slave
     * by using one of these thwo method.
     */
    public void setSlave() {
        this.status = MasterStatus.LobbySlave;
    }

    public void setMaster() {
        this.status = MasterStatus.LobbyMaster;
    }


    public boolean iAmMaster() {
        return this.status == MasterStatus.LobbyMaster;
    }

    // Getter y Setters
    public MasterStatus getStatus() {
        return status;
    }

    public void setStatus(MasterStatus status) {
        this.status = status;
    }

    public LobbyView getLobbyView() {
        return lobbyView;
    }

    public void setLobbyView(LobbyView lobbyView) {
        this.lobbyView = lobbyView;
    }

    public LobbyModel getLobbyModel() {
        return lobbyModel;
    }

    public void setLobbyModel(LobbyModel lobbyModel) {
        this.lobbyModel = lobbyModel;
    }

    public MainGameController getMainGameController() {
        return mainGameController;
    }

    public void setMainGameController(MainGameController mainGameController) {
        this.mainGameController = mainGameController;
    }

}
