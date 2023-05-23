package visual_package;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class AssetsManager {
	
	public static BufferedImage spaceShipA;
	public static BufferedImage spaceShipB;
	public static BufferedImage thrust;
	public static BufferedImage bullet;
	public static BufferedImage meteor;
	
	// Animations
	public static BufferedImage[] bulletExplosion = new BufferedImage[10];
	public static BufferedImage[] spaceShipExplosion = new BufferedImage[10];

	//Tags
	public static BufferedImage[] playerTag = new BufferedImage[10];

	public AssetsManager() {
		loadAssets();
	}

	public void loadAssets() {

		spaceShipA = spriteLoader("/visual_package/img/assets/spaceShipA.png");
		spaceShipB = spriteLoader("/visual_package/img/assets/spaceShipB.png");
		thrust = spriteLoader("/visual_package/img/assets/thrust.png");
		bullet = spriteLoader("/visual_package/img/assets/laser.png");
		meteor = spriteLoader("/visual_package/img/assets/meteor.png");

		for(int i = 0; i < bulletExplosion.length; i++)
			bulletExplosion[i] = spriteLoader("/visual_package/img/animations/bulletExplosion/"+i+".png");

		for(int i = 0; i < spaceShipExplosion.length; i++)
			spaceShipExplosion[i] = spriteLoader("/visual_package/img/animations/spaceShipExplosion/"+i+".png");

		for(int i = 0; i < playerTag.length; i++)
			playerTag[i] = spriteLoader("/visual_package/img/assets/playerTags/"+i+".png");

	}

	public BufferedImage spriteLoader(String path) {
		try {
			return ImageIO.read(Objects.requireNonNull(AssetsManager.class.getResource(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
