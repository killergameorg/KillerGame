package visual_package;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class AssetsManager {
	
	public BufferedImage spaceShipA;
	public BufferedImage spaceShipB;
	public BufferedImage thrust;
	public BufferedImage bullet;
	public BufferedImage meteor;
	
	// Animations
	public BufferedImage[] bulletExplosionAnimation = new BufferedImage[10];
	public BufferedImage[] spaceShipExplosionAnimation = new BufferedImage[10];
	public BufferedImage[] laserSparkleAnimation = new BufferedImage[10];

	//Tags
	public BufferedImage[] playerTag = new BufferedImage[10];

	public AssetsManager() {
		loadAssets();
	}

	public void loadAssets() {

		spaceShipA = spriteLoader("/visual_package/img/assets/spaceShipA.png");
		spaceShipB = spriteLoader("/visual_package/img/assets/spaceShipB.png");
		thrust = spriteLoader("/visual_package/img/assets/thrust.png");
		bullet = spriteLoader("/visual_package/img/assets/laser.png");
		meteor = spriteLoader("/visual_package/img/assets/meteor.png");

		for(int i = 0; i < bulletExplosionAnimation.length; i++)
			bulletExplosionAnimation[i] = spriteLoader("/visual_package/img/animations/bulletExplosion/"+i+".png");

		for(int i = 0; i < spaceShipExplosionAnimation.length; i++)
			spaceShipExplosionAnimation[i] = spriteLoader("/visual_package/img/animations/spaceShipExplosion/"+i+".png");

		for(int i = 0; i < playerTag.length; i++)
			playerTag[i] = spriteLoader("/visual_package/img/assets/playerTag/"+i+".png");

		for(int i = 0; i < laserSparkleAnimation.length; i++)
			laserSparkleAnimation[i] = spriteLoader("/visual_package/img/animations/laserSparkle/"+i+".png");

	}

	public BufferedImage spriteLoader(String path) {
		try {
			return ImageIO.read(Objects.requireNonNull(AssetsManager.class.getResource(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public BufferedImage getSpaceShipA() {
		return spaceShipA;
	}

	public BufferedImage getSpaceShipB() {
		return spaceShipB;
	}

	public BufferedImage getThrust() {
		return thrust;
	}

	public BufferedImage getBullet() {
		return bullet;
	}

	public BufferedImage getMeteor() {
		return meteor;
	}

	public BufferedImage[] getBulletExplosionAnimation() {
		return bulletExplosionAnimation;
	}

	public BufferedImage[] getSpaceShipExplosionAnimation() {
		return spaceShipExplosionAnimation;
	}

	public BufferedImage[] getLaserSparkleAnimation() {
		return laserSparkleAnimation;
	}

	public BufferedImage[] getPlayerTag() {
		return playerTag;
	}
}
