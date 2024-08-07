package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serializable;

public class MakeAdminResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    public MakeAdminResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public MakeAdminResponse setMessage(String message) {
        this.message = message;
        return this;
    }
    public String toString() {
        return "MakeAdminResponse [message=" + message + "]";
    }
}
