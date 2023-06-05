package visual;

<<<<<<< Updated upstream
=======
import java.awt.image.BufferedImage;
import maincontroller.gameinfo.Team;

>>>>>>> Stashed changes
public class SpaceShip extends DynamicVisualObject {
    // * Constructor
    
    public SpaceShip(int id, BufferedImage skin, Position position, float life, int accountId, VisualGameModel visualGameModel,
            int playerNumber, Animation deadAnim, Animation spawnAnim, Team team, double velocity,
            double angle) {
<<<<<<< Updated upstream
        super(id, skin, position, life, accountId, visualGameModel, playerNumber, deadAnim, spawnAnim, width, height, velocity,
=======
        super(id, skin, position, life, accountId, visualGameModel, playerNumber, deadAnim, spawnAnim, team, velocity,
>>>>>>> Stashed changes
                angle);
    }
    
    // * Methods

    @Override
    public void run() {
    }
}