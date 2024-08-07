package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serializable;

public class RemoveUserResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    public RemoveUserResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public RemoveUserResponse setMessage(String message) {
        this.message = message;
        return this;
    }
    public String toString() {
        return "RemoveUserResponse [message=" + message + "]";
    }
}
