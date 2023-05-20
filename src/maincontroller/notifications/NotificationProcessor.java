package maincontroller.notifications;

import java.util.ArrayList;

import events.Action;
import visual.VisualObject;

public interface NotificationProcessor {

    /**
     * Method to process a notification
     * @param visualObject The VisualObject that is being notified (This parameter can be change to Object if needed in the future)
     * @return An ArrayList of Action objects that will be executed by the MainGameController
     */
    public ArrayList<Action> processNotification(VisualObject visualObject);

}
