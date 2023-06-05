package lobby.lobbyController;

import lobby.MasterOrder;
import lobby.MasterStatus;
import lobby.endscreen.EndView;
import lobby.lobbyModel.GameRules;
import lobby.lobbyModel.LobbyModel;
import lobby.lobbyView.LobbyView;
import maincontroller.MainGameController;
import maincontroller.gameinfo.Team;
import maincontroller.gameinfo.TeamName;

public class LobbyController {

    // Atributos
    private MainGameController mainGameController;
    private MasterStatus status;
    private LobbyView lobbyView;
    private LobbyModel lobbyModel;
    private EndView endView;

    // Constructor
    // When created, it asks the maincontroller to apply the master
    public LobbyController() {
        this.mainGameController.applyingToMaster();
        System.out.println("Pedir ser master");
        this.status = MasterStatus.ApplyingToMaster;
    }

    // Metodos
    /*
     * In case of being the lobbyMaster the following function is executed,
     * The object view and lobbymodel are created.
     */
    public void startLobby() {
        if (endView != null) {
            this.endView.dispose();
            this.endView=null;
        }
        LobbyView lobbyView = new LobbyView(this);
        this.setLobbyView(lobbyView);
        LobbyModel lobbyModel = new LobbyModel(this);
        this.setLobbyModel(lobbyModel);
        System.out.println("Start lobby");
    }

    /**
     * The room master sends an enum Left or Right to change the position of rule
     * selected ,also enum Ok or Back is used to increase or decrease the value of
     * the rule.
     * 
     * IF the endview is launched and recive another msg from master
     * start the game again
     * 
     * @param order enum Left, Right, Ok, Back
     */
    public void reciveMasterMsg(MasterOrder order) {
        if (endView == null) {
            lobbyModel.reciveMasterMsg(order);
        } else {
            this.startLobby();
        }
        System.out.println("Recived master msg");
    }

    /**
     * When the room master decides to start the game, it is passed to the
     * maingamecontroller.
     * gameRule.
     * 
     * @param gameRules
     */
    public void startGame(GameRules gameRules) {
        this.lobbyView.dispose();
        this.mainGameController.startGame(gameRules);
        System.out.println("Start Game");
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


    /*
     * Afther finish the game maingamecontroller pass the team and their score
     */
    public void startEndView(Team team1, Team team2) {
        EndView endView = new EndView();
        endView.getViewer().getTeam1Label().setText(team1.getTeamName().toString());
        endView.getViewer().getTeam2Label().setText(team2.getTeamName().toString());
        endView.getViewer().getTeam1Score().setText(String.valueOf(team1.getScore()));
        endView.getViewer().getTeam2Score().setText(String.valueOf(team2.getScore()));
        this.setEndView(endView);
        System.out.println("Start End");
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

    public EndView getEndView() {
        return endView;
    }

    public void setEndView(EndView endView) {
        this.endView = endView;
    }

}
