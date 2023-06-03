package maincontroller.maincommunications.packages;

import java.io.Serializable;

import lobby.lobbyModel.GameRules;

public class PackageStartGame implements Serializable {

    // ! Atributes
    private GameRules gameRules;

    // ! Constructor
    public PackageStartGame(GameRules gameRules) {
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

}
