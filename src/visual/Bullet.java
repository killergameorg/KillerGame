package visual;

import java.awt.image.BufferedImage;

public class Bullet extends DynamicVisualObject {
    public Bullet(int id, BufferedImage skin, Position position, float life, int accountId, VisualGameModel vgm,int playerNumber,Animation deadAnim, Animation spawnAnim, double width, double height, double velocity, double angle, PhysicsEngine pEngine) {
        super(id, skin, position, life, accountId, vgm, playerNumber, deadAnim, spawnAnim, width, height, velocity, angle, pEngine);
    }
    @Override
    public void update(){
        position = pEngine.calculatePosition(position,angle,velocity);
    }
     @Override 
    public void run(){}
}