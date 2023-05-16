package lobby.lobbyModel;

import lobby.LOBBYSECTION;
import lobby.Maps;
import lobby.MasterOrder;
import lobby.lobbyController.LobbyController;

public class LobbyModel {

    // Atributos
    private int selectedRule;
    private LOBBYSECTION lobbysection;
    private GameRules gameRules;
    private int numChangeableRule;
    private LobbyController lobbyController;

    // Constructor
    public LobbyModel(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
        this.selectedRule = 0;
        this.lobbysection = LOBBYSECTION.values()[selectedRule];
        this.gameRules = new GameRules(100, 10, Maps.map1);
        this.numChangeableRule = LOBBYSECTION.values().length;

        // TODO Aqui al crear el modelo debe pasar enum de que esta seleccionado Life
        // Tu metodo(lobbysection),
        lobbyController.getLobbyView().updatePos(this.lobbysection);
        lobbyController.getLobbyView().refreshMasterValues(this.gameRules);
    }


    public void reciveMasterMsg(MasterOrder order) {

        switch (order) {
            case LEFT:
                beforeGameRulePosition();
                break;
            case RIGHT:
                nextGameRulePosition();
                break;
            case OK:
                plusGameRuleValue();
                break;
            case BACK:
                minusGameRuleValue();
                break;
        }

    }

    public void nextGameRulePosition() {
        this.selectedRule = Math.floorMod(selectedRule + 1, numChangeableRule);
        this.lobbysection = LOBBYSECTION.values()[selectedRule];

        // TODO Aqui debe pasar el enum
        // Tu metodo(lobbysection)
        lobbyController.getLobbyView().updatePos(this.lobbysection);
    }

    public void beforeGameRulePosition() {
        this.selectedRule = Math.floorMod(selectedRule - 1, numChangeableRule);
        this.lobbysection = LOBBYSECTION.values()[selectedRule];

        // TODO Aqui debe pasar el enum
        // Tu metodo(lobbysection)
        lobbyController.getLobbyView().updatePos(this.lobbysection);
    }

    public void minusGameRuleValue() {
        // Actualizar valores refresh()
        switch (this.lobbysection) {
            case LIFE:
                if (this.gameRules.getLife() > 0) {
                    this.gameRules.setLife(this.gameRules.getLife() - 5);
                }
                break;
            case BULLETDAMAGE:
                if (this.gameRules.getBulletDamage() > 0) {
                    this.gameRules.setBulletDamage(this.gameRules.getBulletDamage() - 5);
                }
                break;
            case MAP:
                int enumPosition = Maps.valueOf(this.gameRules.getMap().toString()).ordinal();
                this.gameRules.setMap(Maps.values()[Math.floorMod(enumPosition - 1, Maps.values().length)]);
                break;
            case GAMEREADY:
                this.lobbyController.startGame(gameRules);
                break;
        }
        // TODO Aqui te paso el game rule al actualizar
        // Tu metodo(gameRule)
        lobbyController.getLobbyView().refreshMasterValues(this.gameRules);
    }

    public void plusGameRuleValue() {
        // Actualizar valores refresh()
        switch (this.lobbysection) {
            case LIFE:
                this.gameRules.setLife(this.gameRules.getLife() + 5);
                break;
            case BULLETDAMAGE:
                this.gameRules.setBulletDamage(this.gameRules.getBulletDamage() + 5);
                break;
            case MAP:
                int enumPosition = Maps.valueOf(this.gameRules.getMap().toString()).ordinal();
                this.gameRules.setMap(Maps.values()[Math.floorMod(enumPosition + 1, Maps.values().length)]);
                break;
            case GAMEREADY:
                this.lobbyController.startGame(gameRules);
                break;
        }
        // TODO Aqui te paso el game rule al actualizar
        // Tu metodo(gameRule)
        lobbyController.getLobbyView().refreshMasterValues(this.gameRules);
    }

    // Getter y Setters
    public int getSelectedRule() {
        return selectedRule;
    }

    public void setSelectedRule(int selectedRule) {
        this.selectedRule = selectedRule;
    }

    public GameRules getGameRules() {
        return gameRules;
    }

    public void setGameRules(GameRules gameRules) {
        this.gameRules = gameRules;
    }

    public LOBBYSECTION getDinamicRules() {
        return lobbysection;
    }

    public void setDinamicRules(LOBBYSECTION lobbysection) {
        this.lobbysection = lobbysection;
    }

    public LOBBYSECTION getLobbysection() {
        return lobbysection;
    }

    public void setLobbysection(LOBBYSECTION lobbysection) {
        this.lobbysection = lobbysection;
    }

    public int getNumChangeableRule() {
        return numChangeableRule;
    }

    public void setNumChangeableRule(int numChangeableRule) {
        this.numChangeableRule = numChangeableRule;
    }

    public LobbyController getLobbyController() {
        return lobbyController;
    }

    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

}
