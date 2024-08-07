package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serializable;

public class UnblockResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    public UnblockResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public UnblockResponse setMessage(String message) {
        this.message = message;
        return this;
    }
    public String toString() {
        return "UnblockResponse [message=" + message + "]";
    }
}
