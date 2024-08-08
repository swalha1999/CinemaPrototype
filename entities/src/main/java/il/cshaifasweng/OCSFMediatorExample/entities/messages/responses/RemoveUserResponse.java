package il.cshaifasweng.OCSFMediatorExample.entities.messages.responses;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Response;

public class RemoveUserResponse implements Response {
    private String message;
    private Boolean success;

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

}
