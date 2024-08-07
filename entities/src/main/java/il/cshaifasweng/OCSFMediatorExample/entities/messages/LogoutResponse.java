package il.cshaifasweng.OCSFMediatorExample.entities.messages;

public class LogoutResponse {
    private boolean success;
    private String message;

    public LogoutResponse() {
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
}
