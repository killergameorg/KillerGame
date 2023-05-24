package visual_package;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class BackgroundManager {

    // ! Attributes
    private VisualGameView visualGameView;
    private String backgroundTheme;
    private String screenID;
    private BufferedImage background;

    // ! Constructor
    public BackgroundManager(VisualGameView visualGameView){
        this.visualGameView = visualGameView;
        obtainBackgroundTheme();
        obtainScreenID();
        loadBackground();
    }



    // ! Methods

    private void loadBackground() {
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/visual_package/img/backgrounds/" + backgroundTheme + "/background" + screenID + ".png")));
        } catch (IOException e) {
            System.err.println("Error en la obtención del fondo");
        }

    }
    private void obtainBackgroundTheme(){
        //backgroundTheme = "defaultBackground";
        //backgroundTheme = "purpleSpace";
        backgroundTheme = visualGameView.getVisualController().getMainGameController().getBackgroundTheme();
    }
    private void obtainScreenID() {
        screenID = visualGameView.getVisualController().getMainGameController().getScreenID();
        //screenID = 1;
    }
    // ! Getters and Setters

    public BufferedImage getBackground() {
        return background;
    }
}
