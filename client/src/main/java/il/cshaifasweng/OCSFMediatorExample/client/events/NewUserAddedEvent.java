package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class NewUserAddedEvent{

    private final String message;
    private final Boolean success;
    private final User user;

    public NewUserAddedEvent(Message message) {
        this.message = message.getMessage();
        this.success = message.isSuccess();
        this.user = (User) message.getDataObject();
    }

    public String getMessage() {
        return this.message;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public User getUser() {
        return this.user;
    }
}
