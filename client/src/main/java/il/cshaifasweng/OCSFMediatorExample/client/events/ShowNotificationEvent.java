package il.cshaifasweng.OCSFMediatorExample.client.events;

public class ShowNotificationEvent {
    private String message;
    private boolean isSuccessful;

    public ShowNotificationEvent(String message, boolean Success) {
        this.message = message;
        this.isSuccessful = Success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}
