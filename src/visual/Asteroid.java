package visual;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;


public class Asteroid extends DynamicVisualObject {
    private double rotationAngle;

    // * Constructor
    public Asteroid(int id, BufferedImage skin, Position position, float life, int accountId, VisualGameModel visualGameModel,int playerNumber,Animation deadAnim, Animation spawnAnim, double width, double height, double velocity, double angle) {
        super(id, skin, position, life, accountId, visualGameModel, playerNumber, deadAnim, spawnAnim, width, height, velocity, angle);
        rotationAngle = 0;
    }

    // * Methods
    @Override
    public void update(){
        rotationAngle += VisualConstants.asteroidRotationSpeed;
    }

    @Override
    public void drawObject(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = g2d.getTransform();
        g2d.translate(position.getxPos(), position.getyPos());
        g2d.rotate(rotationAngle);
        g2d.drawImage(this.skin, at, null);
    }

    @Override
    public void run(){
          while(getIsAlive()){
            update();
            if(getFuturePosition() != null){
                getVisualGameModel().notifyToVGC(new NotificationMsg(NotificationType.positionUpdate, this));
            }
        }
    }
}
