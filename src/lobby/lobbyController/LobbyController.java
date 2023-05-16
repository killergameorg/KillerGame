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

    // Atributos
    private MainGameController mainGameController;
    private MasterStatus status;
    private LobbyView lobbyView;
    private LobbyModel lobbyModel;

    // Constructor
    public LobbyController(MasterStatus status) {
        this.status = status;
    }

    // Metodos
    /*
     * En caso de ser el lobbyMaster se ejecuta el siguiente funcion,
     * Se crea el objeto de view y modelo de lobby
     */
    public void startLobby() {
        LobbyView lobbyView = new LobbyView(this);
        this.setLobbyView(lobbyView);
        LobbyModel lobbyModel = new LobbyModel(this);
        this.setLobbyModel(lobbyModel);
    }

    /**
     * El room master manda un enum Left o Right para cambiar la posicion de rule
     * seleccionado
     * ademas enum Ok o Back sirve para aumentar o disminuir el valor de rule.
     * 
     * @param order enum Left, Right, Ok, Back
     */
    public void reciveMasterMsg(MasterOrder order) {
        lobbyModel.reciveMasterMsg(order);
    }

    /**
     * Cuando el room master decide comenzar el juego se pasa al maingamecontroller
     * gameRule.
     * @param gameRules
     */
    public void startGame(GameRules gameRules) {
        this.mainGameController.startGame(gameRules);
    }

    /**
     * Este metodo sire para actualizar el view al aumentar o disminuir 
     * cantidad de jugador
     * @param numPlayer un numero que indica cantidad de jugador.
     */
    public void setPlayerCount(int numPlayer) {

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
