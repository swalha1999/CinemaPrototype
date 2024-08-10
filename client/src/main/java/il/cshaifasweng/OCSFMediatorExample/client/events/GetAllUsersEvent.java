package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

import java.util.ArrayList;
import java.util.List;

public class GetAllUsersEvent{

    private final boolean isSucceed;
    private final String message;
    private final List<User> users;

    public GetAllUsersEvent(Message message) {
        this.isSucceed = message.isSuccess();
        this.message = message.getMessage();

        //TODO: check if this is the correct casting
        this.users = (List<User>) message.getDataObject();
    }

    public boolean isSuccess() {
        return isSucceed;
    }

    public String getMessage() {
        return message;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "GetAllUsersEvent{" +
                "isSucceed=" + isSucceed +
                ", message='" + message + '\'' +
                ", users=" + users +
                '}';
    }
}