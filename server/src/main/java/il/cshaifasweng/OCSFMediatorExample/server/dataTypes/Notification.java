package il.cshaifasweng.OCSFMediatorExample.server.dataTypes;

import java.time.LocalDateTime;

public class Notification {
    private final String message;
    private final LocalDateTime notificationTime;

    public Notification(String message, LocalDateTime notificationTime) {
        this.message = message;
        this.notificationTime = notificationTime;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }
}
