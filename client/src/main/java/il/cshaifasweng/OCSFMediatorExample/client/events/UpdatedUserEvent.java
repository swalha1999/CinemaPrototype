package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class UpdatedUserEvent {
    private final boolean success;
    private final String message;
    private final User updatedUser;

    public UpdatedUserEvent(Message userUpdatedMessage) {
        this.success = userUpdatedMessage.isSuccess();
        this.message = userUpdatedMessage.getMessage();
        this.updatedUser = (User) userUpdatedMessage.getDataObject();
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public User getUpdatedUser() {
        return updatedUser;
    }
}
