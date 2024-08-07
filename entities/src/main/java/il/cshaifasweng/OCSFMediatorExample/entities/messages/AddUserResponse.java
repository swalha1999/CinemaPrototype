package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serial;
import java.io.Serializable;

public class AddUserResponse implements Response {
    @Serial
    private static final long serialVersionUID = 1L;

    private String message;
    private boolean success;
    private String newUsername;

    public AddUserResponse() {
    }

    public AddUserResponse(AddUserResponse other) {
        this.message = other.message;
        this.success = other.success;
        this.newUsername = other.newUsername;
    }

    public AddUserResponse(String message, boolean success, String newUsername) {
        this.message = message;
        this.success = success;
        this.newUsername = newUsername;
    }

    public String getMessage() {
        return message;
    }

    public AddUserResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public AddUserResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public AddUserResponse setNewUsername(String newUsername) {
        this.newUsername = newUsername;
        return this;
    }

    public String toString() {
        return message;
    }

}
