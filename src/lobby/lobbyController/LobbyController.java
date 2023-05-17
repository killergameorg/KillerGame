package lobby.lobbyController;

import java.util.ArrayList;

import lobby.MasterOrder;
import lobby.MasterStatus;
import lobby.lobbyModel.GameRules;
import lobby.lobbyModel.LobbyModel;
import lobby.lobbyView.LobbyView;
import maincontroller.Account;
import maincontroller.MainGameController;

public class LobbyController {

    // Atributes
    private MainGameController mainGameController;
    private MasterStatus status;
    private LobbyView lobbyView;
    private LobbyModel lobbyModel;

    // Constructor
    public LobbyController(MasterStatus status) {
        this.status = status;
    }
    // Methods
    public void addAccount(Account account) {
        lobbyModel.addAccount(account);
    }

    public void removeAccount(Account account) {
        lobbyModel.removeAccount(account);
    }

    public void reciveMasterMsg(Account account, MasterOrder order) {
        lobbyModel.reciveMasterMsg(account, order);
    }

    public void startGame(GameRules gameRules, ArrayList<Account> players) {
        this.mainGameController.startGame(gameRules, players);
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
