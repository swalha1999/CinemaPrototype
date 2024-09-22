package il.cshaifasweng.OCSFMediatorExample.server.dataTypes;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {
    private final String id;
    private final String message;
    private final LocalDateTime notificationTime;
    private final LoggedInUser userConnection;
    private final String email;


    public Notification(String message, LocalDateTime notificationTime, LoggedInUser user) {
        this.id = UUID.randomUUID().toString();
        this.message = message;
        this.notificationTime = notificationTime;
        this.userConnection = user;
        this.email = null;
    }

    public Notification(String message, LocalDateTime notificationTime, String email) {
        this.id = UUID.randomUUID().toString();
        this.message = message;
        this.notificationTime = notificationTime;
        this.email = email;
        this.userConnection = null;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public LoggedInUser getUserConnection() {
        return userConnection;
    }

}
