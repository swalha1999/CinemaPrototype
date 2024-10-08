package il.cshaifasweng.OCSFMediatorExample.entities.messages.responses;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Response;

import java.io.Serial;

public class RegisterResponse implements Response {
    @Serial
    private static final long serialVersionUID = -5386248350340650194L;

    private boolean success;
    private String message;
    private int userId;

    public RegisterResponse() {
    }

    public RegisterResponse(RegisterResponse other) {
        this.success = other.success;
        this.message = other.message;
    }

    public RegisterResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public RegisterResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public RegisterResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public RegisterResponse setUserId(int userId) {
        this.userId = userId;
        return this;
    }
}
