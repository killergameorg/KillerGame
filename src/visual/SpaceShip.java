package visual;

public class SpaceShip extends DynamicVisualObject {
    // * Constructor
    
    public SpaceShip(int id, BufferedImage skin, Position position, float life, int accountId, VisualGameModel visualGameModel,
            int playerNumber, Animation deadAnim, Animation spawnAnim, double width, double height, double velocity,
            double angle) {
        super(id, skin, position, life, accountId, visualGameModel, playerNumber, deadAnim, spawnAnim, width, height, velocity,
                angle);
    }
    
    // * Methods

    @Override
    public void run() {
    }
}