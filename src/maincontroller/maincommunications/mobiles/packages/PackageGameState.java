package maincontroller.maincommunications.mobiles.packages;

import java.io.Serializable;

public class PackageGameState implements Serializable {
    private static final long serialVersionUID = 91345667L;

    private GameStateTypes gameState;

    public PackageGameState(GameStateTypes gameState) {
        this.gameState = gameState;
    }

    public GameStateTypes getGameState() {
        return gameState;
    }

}
