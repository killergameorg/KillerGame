package lobby.lobbyModel;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import lobby.LOBBYSECTION;
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
        // Initialize attributes
        this.lobbyController = lobbyController;
        this.selectedRule = 0;
        this.lobbysection = LOBBYSECTION.values()[selectedRule];
        this.gameRules = new GameRules();
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
        this.startGameOrChangeValueSelectedRule(false);
        // Update the view with the values
        lobbyController.getLobbyView().refreshMasterValues(this.gameRules);
    }

    /**
     * When activated, the gamer rule selected, in case of int will plus 5, and
     * enum will select next one
     */
    public void plusGameRuleValue() {
        this.startGameOrChangeValueSelectedRule(true);
        // Update the view with the values
        lobbyController.getLobbyView().refreshMasterValues(this.gameRules);
    }

    /**
     * If the selected rule is the last changeable rule, it starts the game.
     * As easter egg if the damage is more or eaqual to life invoque demon
     * If not change the value
     * 
     * @param plus
     */
    public void startGameOrChangeValueSelectedRule(boolean plus) {
        if (this.selectedRule == this.numChangeableRule - 1) {
            if (this.getGameRules().getDinamicGameRule().getLife() <= this.getGameRules().getDinamicGameRule()
                    .getBulletDamage()) {
                TimerTask animationTask = new TimerTask() {
                    @Override
                    public void run() {
                        lobbyController.startGame(gameRules);
                    }
                };
                this.lobbyController.getLobbyView().invoqueDemon();
                Timer timer = new Timer();
                timer.schedule(animationTask, 1500);
            }else{
                this.lobbyController.startGame(gameRules);
            }
        } else {
            this.changeValueSelectedRule(plus);
        }
    }

    /**
     * Changes the value of the selected rule depending of the boolean.
     * 
     * In case you add more attribute of int or enum in class DinamicGameRule
     * this method dont need to change, but you need to add a enum to LOBBYSECTION
     * with exact same order as attribute of DinamicGameRule.
     * 
     * @param plus incicate to increment the value (true)
     *             or decrement the value (false)
     */
    public void changeValueSelectedRule(boolean plus) {
        DinamicGameRule dinamicGameRule = this.getGameRules().getDinamicGameRule();
        Field[] campos = dinamicGameRule.getClass().getDeclaredFields();
        Field atributo = campos[this.selectedRule];
        atributo.setAccessible(true);
        try {
            // See what type of attribute is and plus of minus the value
            if (atributo.getType() == int.class) {
                int value = atributo.getInt(dinamicGameRule);
                if (plus) {
                    atributo.setInt(dinamicGameRule, value + 5);
                } else {
                    if (value - 5 > 0) {
                        atributo.setInt(dinamicGameRule, value - 5);
                    }
                }
            } else if (atributo.getType().isEnum()) {
                Object[] enumValues = atributo.getType().getEnumConstants();
                Object valorEnum = atributo.get(dinamicGameRule);
                int indexEnum = indexEnum(enumValues, valorEnum);
                int nextEnumIndex;
                if (plus) {
                    nextEnumIndex = (indexEnum + 1) % enumValues.length;
                } else {
                    nextEnumIndex = Math.floorMod((indexEnum - 1), enumValues.length);
                }
                atributo.set(dinamicGameRule, enumValues[nextEnumIndex]);
            } // If adding a different type of attribute need to add other else if to check
              // and do what you need
        } catch (Exception e) {
            System.out.println("error");
        }

    }

    private int indexEnum(Object[] array, Object enumObject) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(enumObject)) {
                return i;
            }
        }
        return -1;
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
