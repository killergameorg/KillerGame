package maincontroller;

import java.util.ArrayList;

import lobby.lobbyModel.GameRules;

public class MainGameController {

    // ! Attributes
    private GameState gameState;
    private ArrayList<Account> accounts;

    // ! Constructor
    public MainGameController() {
        this.setGameState(GameState.UNDEFINED);
        this.setAccounts(new ArrayList<Account>());
    }

    // ! Methods
    public void applyingToMaster() {
        // TODO
    }

    public void startGame(GameRules gameRules, ArrayList<Account> accounts) {
        this.setAccounts(accounts);
        // TODO
    }

    // ! Getters and Setters
    /**
     * @return the gameState
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * @param gameState the gameState to set
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * @return the accounts
     */
    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    /**
     * @param accounts the accounts to set
     */
    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

}
