package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class UserInfoReceivedEvent {
    boolean isSuccess;
    String message;
    User user;

    public UserInfoReceivedEvent(Message message) {
        this.isSuccess = message.isSuccess();
        this.message = message.getMessage();
        this.user = (User) message.getDataObject();
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

}
