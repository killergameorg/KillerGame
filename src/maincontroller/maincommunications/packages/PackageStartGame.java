package maincontroller.maincommunications.packages;

import lobby.lobbyModel.GameRules;

public class PackageStartGame implements PackageMainCommunications {

    // ! Attributes
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
