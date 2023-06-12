package visual;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import lobby.Maps;

public class BackgroundManager {

    // ! Attributes
    private VisualGameView visualGameView;
    private String backgroundTheme;
    private Maps mapName;
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
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/visual/img/backgrounds/" + backgroundTheme + "/background" + screenID + ".png")));
        } catch (IOException e) {
            System.err.println("Error en la obtención del fondo");
        }

    }
    private void obtainBackgroundTheme(){
        //backgroundTheme = "defaultBackground";
        //backgroundTheme = "purpleSpace";
        mapName = visualGameView.getVisualGameController().getMainGameController().getBackgroundTheme();
        switch (mapName){
            case MAP_1:
                backgroundTheme = "theme1";
                break;

            case MAP_2:
                backgroundTheme = "theme2";
                break;
            
            case MAP_3:
                backgroundTheme = "theme3";
                break;

            case MAP_4:
                backgroundTheme = "theme4";
                break;
        }

    }
    private void obtainScreenID() {
        screenID = String.valueOf(visualGameView.getVisualGameController().getMainGameController().getConfigurationFileId());
        //screenID = 1;
    }
    // ! Getters and Setters

    public BufferedImage getBackground() {
        return background;
    }
}
