package visual;

public class NotificationMsg {
    
    private NotificationType notificationType;
    private VisualObject visualObject;

    // * Constructor

    public NotificationMsg(NotificationType notificationType, VisualObject visualObject) {
        this.notificationType = notificationType;
        this.visualObject = visualObject;
    }

    // * Getters

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public VisualObject getVisualObject() {
        return visualObject;
    }
}
