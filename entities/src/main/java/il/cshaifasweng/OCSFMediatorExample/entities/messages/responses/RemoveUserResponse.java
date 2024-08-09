package il.cshaifasweng.OCSFMediatorExample.entities.messages.responses;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Response;

import java.io.Serial;

public class RemoveUserResponse implements Response {

    @Serial
    private static final long serialVersionUID = 1L;

    private String message;
    private Boolean success;
    private int userId;

    public RemoveUserResponse() {
        this.message = "";
        this.success = false;
    }

    public String getMessage() {
        return this.message;
    }

    public RemoveUserResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public RemoveUserResponse setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public RemoveUserResponse setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String toString() {
        return "RemoveUserResponse{" +
                "message='" + message + '\'' +
                ", success=" + success +
                ", userId=" + userId +
                '}';
    }

}
