package visual;

import java.awt.image.BufferedImage;

public class Bullet extends DynamicVisualObject {
    // * Constructor

    public Bullet(int id, BufferedImage skin, Position position, float life, int accountId, VisualGameModel vgm,int playerNumber,Animation deadAnim, Animation spawnAnim, double velocity, double angle) {
        super(id, skin, position, life, accountId, vgm, playerNumber, deadAnim, spawnAnim, velocity, angle);
    }
    
    // * Methods

    @Override
    public void run(){
          while(getIsAlive()){
            if(getFuturePosition() == null){
                calculateNewPosition()
                getVisualGameModel().notifyToVGC(new NotificationMsg(NotificationType.positionUpdate, this));
            }
        }
    }
}