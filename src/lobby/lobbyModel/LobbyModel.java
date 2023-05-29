package lobby.lobbyModel;

import lobby.LOBBYSECTION;
import lobby.MasterOrder;
import lobby.lobbyController.LobbyController;
import lobby.Maps;

public class LobbyModel {

    // Atributos
    private int selectedRule;
    private LOBBYSECTION lobbysection;
    private GameRules gameRules;
    private int numChangeableRule;
    private LobbyController lobbyController;

    // Constructor
    public LobbyModel(LobbyController lobbyController) {
        // Initialize attributes
        this.lobbyController = lobbyController;
        this.selectedRule = 0;
        this.lobbysection = LOBBYSECTION.values()[selectedRule];
        this.gameRules = new GameRules(100, 10, Maps.MAP_1);
        this.numChangeableRule = LOBBYSECTION.values().length;

        // Update the viewer the selected rule and the values of all game rules
        lobbyController.getLobbyView().updatePos(this.lobbysection);
        lobbyController.getLobbyView().refreshMasterValues(this.gameRules);
    }

    /**
     * The room master sends an enum Left or Right to change the position of rule
     * selected ,also enum Ok or Back is used to increase or decrease the value of
     * the rule.
     * 
     * @param order enum Left, Right, Ok, Back
     */
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

    /** When activated, the following gamer rule will be selected */
    public void nextGameRulePosition() {
        // Set the selectedRule to next
        this.selectedRule = Math.floorMod(selectedRule + 1, numChangeableRule);
        // Select the enum of gameRule
        this.lobbysection = LOBBYSECTION.values()[selectedRule];

        // Pass the enum to update the view.
        lobbyController.getLobbyView().updatePos(this.lobbysection);
    }

    /** When activated, the previous gamer rule will be selected */
    public void beforeGameRulePosition() {
        // Set the selectedRule to previous
        this.selectedRule = Math.floorMod(selectedRule - 1, numChangeableRule);
        // Select the enum of gameRule
        this.lobbysection = LOBBYSECTION.values()[selectedRule];

        // Pass the enum to update the view.
        lobbyController.getLobbyView().updatePos(this.lobbysection);
    }

    /**
     * When activated, the gamer rule selected, in case of int will minus 5, and
     * enum will select previous
     */
    public void minusGameRuleValue() {
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
        // Update the view with the values
        lobbyController.getLobbyView().refreshMasterValues(this.gameRules);
    }

    /**
     * When activated, the gamer rule selected, in case of int will plus 5, and
     * enum will select next one
     */
    public void plusGameRuleValue() {
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
        // Update the view with the values
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
