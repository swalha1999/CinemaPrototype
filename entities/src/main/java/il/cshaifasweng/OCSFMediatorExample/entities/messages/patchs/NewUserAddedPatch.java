package il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs;

import il.cshaifasweng.OCSFMediatorExample.entities.User;

public class NewUserAddedPatch implements Patch {

    private String message;
    private Boolean success;
    private User user;


    public NewUserAddedPatch() {
        this.message = "";
        this.success = false;
    }

    public String getMessage() {
        return this.message;
    }

    public NewUserAddedPatch setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public NewUserAddedPatch setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public NewUserAddedPatch setUser(User user) {
        this.user = user;
        return this;
    }
}
