import java.io.FileNotFoundException;
import java.io.IOException;

import maincontroller.MainGameController;

public class Main {
    public static void main(String[] args) {
        try {
            MainGameController mainGameController = new MainGameController(
                    "configuration.properties"

            );

            mainGameController.initializeConnectionController();

            mainGameController.applyingToMaster();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
