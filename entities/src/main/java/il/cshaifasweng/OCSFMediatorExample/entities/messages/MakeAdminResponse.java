package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serial;
import java.io.Serializable;

public class MakeAdminResponse implements Response {

    @Serial
    private static final long serialVersionUID = 1L;
    private String message;
    private Boolean success;

    public MakeAdminResponse() {
    }

    public MakeAdminResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public MakeAdminResponse(MakeAdminResponse other) {
        this.message = other.message;
        this.success = other.success;
    }

    public String getMessage() {
        return message;
    }

    public MakeAdminResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public MakeAdminResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String toString() {
        return "MakeAdminResponse{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }




}
