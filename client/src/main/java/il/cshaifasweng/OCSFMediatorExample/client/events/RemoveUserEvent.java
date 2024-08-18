package il.cshaifasweng.OCSFMediatorExample.client.events;


import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class RemoveUserEvent  {

    private final String message;
    private final String username;
    private final int userId;
    private final Boolean success;

    public RemoveUserEvent(Message message) {
        this.message = message.getMessage();
        this.success = message.isSuccess();
        this.username = ((User) message.getDataObject()).getUsername();
        this.userId = ((User) message.getDataObject()).getId();
    }

    public String getMessage() {
        return this.message;
    }

    public String getUsername() {
        return this.username;
    }

    public int getUserId() {
        return this.userId;
    }

    public Boolean getSuccess() {
        return this.success;
    }


}
