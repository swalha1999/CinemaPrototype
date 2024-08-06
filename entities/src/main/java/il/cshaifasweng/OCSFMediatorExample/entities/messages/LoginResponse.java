package il.cshaifasweng.OCSFMediatorExample.entities.messages;

public class LoginResponse {
    private boolean success;
    private String message;
    private String sessionID;
    private String role;


    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
