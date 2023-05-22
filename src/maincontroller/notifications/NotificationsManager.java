package maincontroller.notifications;

import java.util.ArrayList;
import java.util.HashMap;

import events.Action;
import events.ExplosionAction;
import maincontroller.MainGameModel;
import visual.NotificationMsg;
import visual.NotificationType;
import visual.VisualObject;

public class NotificationsManager {

    // ! Attributes
    private MainGameModel mainGameModel;
    private HashMap<NotificationType, NotificationProcessor> notificationHashMap;
    private NotificationsActionsProcessor notificationsActionsProcessor;

    // ! Constructor
    public NotificationsManager(MainGameModel mainGameModel) {
        this.setMainGameModel(mainGameModel);
        this.setNotificationHashMap(this.createNotificationHashMap());
        this.setNotificationsActionsProcessor(new NotificationsActionsProcessor());
    }

    // ! Constructor methods
    private HashMap<NotificationType, NotificationProcessor> createNotificationHashMap() {
        HashMap<NotificationType, NotificationProcessor> notificationHashMap = new HashMap<NotificationType, NotificationProcessor>();

        notificationHashMap.put(
                NotificationType.POSITION_UPDATE,
                new NotificationProcessorPositionUpdate(this.getMainGameModel()));

        // Can add more notifications here if needed in the future

        return notificationHashMap;
    }

    // ! Methods

    /**
     * Process a notification
     * 
     * @param notificationMsg The notification to process
     */
    public void processNotification(NotificationMsg notificationMsg) {

        VisualObject visualObject = notificationMsg.getVisualObject();
        NotificationType notificationType = notificationMsg.getNotificationType();

        ArrayList<Action> actions = this.getNotificationHashMap()
                .get(notificationType)
                .processNotification(visualObject);

        if (actions.size() == 0) {
            this.updateVisualObjectPosition(visualObject);
        } else {
            this.getNotificationsActionsProcessor().processActions(actions);
        }
    }

    public void updateVisualObjectPosition(VisualObject visualObject) {
        this.getMainGameModel().updateVisualObjectPosition(visualObject);
    }

    public void decreaseLifeVisualObject(VisualObject visualObject, float lifeDowngrade) {
        this.getMainGameModel().decreaseLifeVisualObject(visualObject, lifeDowngrade);
    }

    public void processActionExplosion(ExplosionAction explosionAction) {
        this.getMainGameModel().processActionExplosion(explosionAction);
    }

    // ! Getters and Setters

    /**
     * @return the notificationHashMap
     */
    public HashMap<NotificationType, NotificationProcessor> getNotificationHashMap() {
        return notificationHashMap;
    }

    /**
     * @param notificationHashMap the notificationHashMap to set
     */
    public void setNotificationHashMap(HashMap<NotificationType, NotificationProcessor> notificationHashMap) {
        this.notificationHashMap = notificationHashMap;
    }

    /**
     * @return the notificationsActionsProcessor
     */
    public NotificationsActionsProcessor getNotificationsActionsProcessor() {
        return notificationsActionsProcessor;
    }

    /**
     * @param notificationsActionsProcessor the notificationsActionsProcessor to set
     */
    public void setNotificationsActionsProcessor(NotificationsActionsProcessor notificationsActionsProcessor) {
        this.notificationsActionsProcessor = notificationsActionsProcessor;
    }

    /**
     * @return the mainGameModel
     */
    public MainGameModel getMainGameModel() {
        return mainGameModel;
    }

    /**
     * @param mainGameModel the mainGameModel to set
     */
    public void setMainGameModel(MainGameModel mainGameModel) {
        this.mainGameModel = mainGameModel;
    }

}