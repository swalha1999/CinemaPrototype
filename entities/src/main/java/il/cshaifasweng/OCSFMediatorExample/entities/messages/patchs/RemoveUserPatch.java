package il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs;

public class RemoveUserPatch {
    private String username;

    public RemoveUserPatch() {
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

    public String toString() {
        return "RemoveUserPatch{" +
                "username='" + username + '\'' +
                '}';
    }
}
