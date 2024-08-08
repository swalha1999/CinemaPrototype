package il.cshaifasweng.OCSFMediatorExample.entities.messages.responses;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Response;

import java.io.Serial;

public class UnblockUserResponse implements Response {
    @Serial
    private static final long serialVersionUID = 1L;

    private String message;
    private Boolean success;

    public UnblockUserResponse() {
        this.message = "";
        this.success = false;
    }


    public String toString() {
        return "UnblockResponse{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }

    public String getMessage() {
        return this.message;
    }

    public UnblockUserResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public UnblockUserResponse setSuccess(Boolean success) {
        this.success = success;
        return this;
    }
}
