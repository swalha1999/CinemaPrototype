package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import il.cshaifasweng.OCSFMediatorExample.entities.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class GetAllUsersResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -5386248350340650199L;

    private boolean isSucceed = false;
    private String message;
    private List<User> users = new ArrayList<>();

    public GetAllUsersResponse() {
    }

    public GetAllUsersResponse(List<User> users) {
        this.users = users;
    }

    public List<User> setUsers() {
        return users;
    }

    public GetAllUsersResponse setUsers(List<User> users) {
        this.users = users;
        return this;
    }

    public boolean isSucceed() {
        return isSucceed;
    }

    public GetAllUsersResponse setSucceed(boolean isSucceed) {
        this.isSucceed = isSucceed;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public GetAllUsersResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public GetAllUsersResponse addUser(User user) {
        this.users.add(user);
        return this;
    }

    public String toString() {
        return "GetAllUsersResponse{" +
                "users='" + users + '\'' +
                '}';
    }


}
