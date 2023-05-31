package visual;

import java.awt.image.BufferedImage;

public class StaticVisualObject extends VisualObject{
    // * Constructor

    public StaticVisualObject(int id, BufferedImage skin, Position position, float life, int accountId,
    VisualGameModel visualGameModel, int playerNumber, Animation deadAnim, Animation spawnAnim, double width,
    double height) {
        super(id, skin, position, life, accountId, visualGameModel, playerNumber, deadAnim, spawnAnim, width, height);
    }

    // * Methods

    @Override
    public void run(){}
    
}