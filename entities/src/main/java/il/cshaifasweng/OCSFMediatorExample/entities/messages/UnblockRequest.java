package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serializable;

public class UnblockRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    public UnblockRequest(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String toString() {
        return message;
    }
}
