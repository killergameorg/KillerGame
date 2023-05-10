package lobby.lobbyController;

import maincontroller.Account;
import lobby.MasterOrder;
import lobby.MasterStatus;
import lobby.lobbyModel.LobbyModel;
import lobby.lobbyView.LobbyView;

public class LobbyController {

    // Atributos
    private MasterStatus status;
    private LobbyView lobbyView;
    private LobbyModel lobbyModel;

    // Constructor
    public LobbyController(MasterStatus status, LobbyView lobbyView, LobbyModel lobbyModel) {
        this.status = status;
        this.lobbyView = lobbyView;
        this.lobbyModel = lobbyModel;
    }

    // Metodos
    public void addAccount(Account account) {
        lobbyModel.addAccount(account);
    }

    public void removeAccount(Account account) {
        lobbyModel.removeAccount(account);
    }

    public void reciveMasterMsg(Account account, MasterOrder order) {
        lobbyModel.reciveMasterMsg(account, order);
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

}
