package maincontroller.maincommunications.packages;

import lobby.lobbyModel.GameRules;
import maincontroller.gameinfo.GameState;

public class PackageGameState implements PackageMainCommunications {

    // ! Attributes
    private GameState gameState;
    private GameRules gameRules;

    // ! Constructor
    public PackageGameState(GameState gameState, GameRules gameRules) {
        this.setGameState(gameState);
        this.setGameRules(gameRules);
    }

    // ! Getters and Setters

    /**
     * @return the gameRules
     */
    public GameRules getGameRules() {
        return gameRules;
    }

    /**
     * @param gameRules the gameRules to set
     */
    public void setGameRules(GameRules gameRules) {
        this.gameRules = gameRules;
    }

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

}
