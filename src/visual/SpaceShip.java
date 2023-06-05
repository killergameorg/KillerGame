package visual;
import java.awt.image.BufferedImage;
import maincontroller.gameinfo.Team;

import java.awt.image.BufferedImage;

public class SpaceShip extends DynamicVisualObject {
    // * Constructor
    
    public SpaceShip(int id, BufferedImage skin, Position position, float life, int accountId, VisualGameModel visualGameModel,
            int playerNumber, Animation deadAnim, Animation spawnAnim, Team team, double velocity,
            double angle) {
        super(id, skin, position, life, accountId, visualGameModel, playerNumber, deadAnim, spawnAnim, team, velocity, angle);
    }
    
    // * Methods

    @Override
    public void run() {
    }
}
