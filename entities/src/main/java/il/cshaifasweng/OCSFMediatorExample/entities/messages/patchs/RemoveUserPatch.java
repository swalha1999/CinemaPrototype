package il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs;

import java.io.Serial;

public class RemoveUserPatch implements Patch {
    @Serial
    private static final long serialVersionUID = -5386248350340650197L;

    private String message;
    private String username;
    private int userId;
    private Boolean success;

    public RemoveUserPatch() {
    }

    public RemoveUserPatch(RemoveUserPatch removeUserPatch) {
        this.message = removeUserPatch.getMessage();
        this.username = removeUserPatch.getUsername();
        this.userId = removeUserPatch.getUserId();
        this.success = removeUserPatch.isSuccess();
    }

    public RemoveUserPatch(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public RemoveUserPatch setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public RemoveUserPatch setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public RemoveUserPatch setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public RemoveUserPatch setUserId(int userId) {
        this.userId = userId;
        return this;
    }


    public String toString() {
        return "RemoveUserPatch{" +
                "message='" + message + '\'' +
                ", success=" + success +
                ", username='" + username + '\'' +
                ", userId=" + userId +
                '}';
    }


}
