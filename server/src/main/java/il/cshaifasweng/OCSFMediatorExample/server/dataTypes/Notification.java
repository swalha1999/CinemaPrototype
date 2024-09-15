package il.cshaifasweng.OCSFMediatorExample.server.dataTypes;

import java.time.LocalDateTime;

public class Notification {
    private final String message;
    private final LocalDateTime notificationTime;
    private final LoggedInUser userConnection;
    private final String email;


    public Notification(String message, LocalDateTime notificationTime, LoggedInUser user) {
        this.message = message;
        this.notificationTime = notificationTime;
        this.userConnection = user;
        this.email = null;
    }

    public Notification(String message, LocalDateTime notificationTime, String email) {
        this.message = message;
        this.notificationTime = notificationTime;
        this.email = email;
        this.userConnection = null;
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
