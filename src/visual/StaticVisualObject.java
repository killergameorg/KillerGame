package visual;

import java.awt.image.BufferedImage;

public class StaticVisualObject extends VisualObject{
    // * Constructor

    public StaticVisualObject(int id, BufferedImage skin, Position position, float life, int accountId,
    VisualGameModel visualGameModel, int playerNumber, Animation deadAnim, Animation spawnAnim, double angle) {
        super(id, skin, position, life, accountId, visualGameModel, playerNumber, deadAnim, spawnAnim, angle);
    }

    // * Methods

    @Override
    public void run(){}

    @Override
    public void updatePosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePosition'");
    }

    @Override
    public void updateRotation(Direction direction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRotation'");
    }

    @Override
    public void calculateNewPosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateNewPosition'");
    }
    
}