package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serial;
import java.io.Serializable;

public class AddUserResponse implements Request {
    @Serial
    private static final long serialVersionUID = 1L;
    private String message;
    public AddUserResponse(String message) {
        this.message = message;

    }
    public AddUserResponse(AddUserResponse response) {
        this.message = response.message;
    }
    public String getMessage() {
        return message;
    }
    public AddUserResponse setMessage(String message) {
        this.message = message;
        return this;
    }
    public String toString() {
        return "AddUserResponse [message=" + message + "]";
    }

    public String getSessionKey() {
        return "";
    }

    public Request setSessionKey(String sessionKey) {
       sessionKey = getSessionKey();
        return null;
    }

    public String getUsername() {
       return  this.getUsername();
    }

    public Request setUsername(String username) {
        this.setUsername(username);
        return this;
    }

    public int getUserId() {
        return  this.getUserId();
    }

    @Override
    public Request setUserId(int userId) {
        this.setUserId(userId);
        return this;
    }
}
