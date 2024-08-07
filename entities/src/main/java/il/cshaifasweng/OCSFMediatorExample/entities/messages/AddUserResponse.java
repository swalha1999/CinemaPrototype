package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serial;
import java.io.Serializable;

public class AddUserResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String message;
    public AddUserResponse(String message) {
        this.message = message;

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
}
