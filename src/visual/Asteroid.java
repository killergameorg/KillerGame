package visual;
import java.awt.image.BufferedImage;

public class Asteroid extends DynamicVisualObject {
    private double rotationAngle;
    public Asteroid(int id, BufferedImage skin, Position position, float life, int accountId, VisualGameModel vgm,int playerNumber,Animation deadAnim, Animation spawnAnim, double width, double height, double velocity, double angle, PhysicsEngine pEngine) {
        super(id, skin, position, life, accountId, vgm, playerNumber, deadAnim, spawnAnim, width, height, velocity, angle, pEngine);
        rotationAngle = 0;
    }
    @Override
    public void update(){
        position = pEngine.calculatePosition(position,angle,velocity);
        rotationAngle += VisualConstants.asteroidRotationSpeed;
    }
     @Override 
    public void run(){}
}
