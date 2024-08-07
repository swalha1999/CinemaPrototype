package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serializable;

public class UnblockResponse implements Response {
    private static final long serialVersionUID = 1L;
    private String message;
    private Boolean IsSuccess;
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

    @Override
    public boolean isSuccess() {
        return false;
    }

    public String toString() {
        return "UnblockResponse [message=" + message + "]";
    }
}
