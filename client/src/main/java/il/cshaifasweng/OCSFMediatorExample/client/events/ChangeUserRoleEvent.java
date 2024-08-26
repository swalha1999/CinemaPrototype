package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class ChangeUserRoleEvent {

    private final boolean isSuccess;
    private final String message;
    public ChangeUserRoleEvent(Message message) {
        this.isSuccess = message.isSuccess();
        this.message = message.getMessage();
    }
    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

}