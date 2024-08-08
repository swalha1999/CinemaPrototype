package il.cshaifasweng.OCSFMediatorExample.entities.messages.responses;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Response;

import java.io.Serial;

public class BlockUserResponse implements Response {

    @Serial
    private static final long serialVersionUID = 1L;

    private String message;
    private Boolean success;

    public BlockUserResponse() {
        this.message = "";
        this.success = false;
    }


    public String toString() {
        return "BlockUserResponse{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }

    public String getMessage() {
        return this.message;
    }

    public BlockUserResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public BlockUserResponse setSuccess(Boolean success) {
        this.success = success;
        return this;
    }
}
