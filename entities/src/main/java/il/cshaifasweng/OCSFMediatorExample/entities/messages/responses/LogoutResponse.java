package il.cshaifasweng.OCSFMediatorExample.entities.messages.responses;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Response;

import java.io.Serial;

public class LogoutResponse implements Response {

    @Serial
    private static final long serialVersionUID = -5386248350340650194L;

    private boolean success;
    private String message;

    public LogoutResponse() {
    }

    public LogoutResponse(LogoutResponse other) {
        this.success = other.success;
        this.message = other.message;
    }


    public LogoutResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public LogoutResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public LogoutResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String toString() {
        return "LogoutResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
