package il.cshaifasweng.OCSFMediatorExample.entities.messages.requests;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Request;

import java.io.Serial;

public class RemoveUserRequest implements Request {
    @Serial
    private static final long serialVersionUID = -5386248350340650197L;

    private String sessionKey;
    private String username;
    private int userId;

    private String usernameToRemove = "admin"; // we cant remove the admin so we set it as default
    private int userIdToRemove = 0 ;

    public RemoveUserRequest() {
    }

    public RemoveUserRequest(String sessionKey, String username, String usernameToRemove, int userIdToRemove) {
        this.sessionKey = sessionKey;
        this.username = username;
        this.usernameToRemove = usernameToRemove;
        this.userIdToRemove = userIdToRemove;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public String getUsername() {
        return username;
    }

    public String getUsernameToRemove() {
        return usernameToRemove;
    }

    public int getUserIdToRemove() {
        return userIdToRemove;
    }

    public int getUserId() {
        return userId;
    }

    public String toString() {
        return "RemoveUserRequest{" +
                "sessionKey='" + sessionKey + '\'' +
                ", username='" + username + '\'' +
                ", usernameToRemove='" + usernameToRemove + '\'' +
                ", userIdToRemove=" + userIdToRemove +
                '}';
    }

    public RemoveUserRequest setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    public RemoveUserRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public RemoveUserRequest setUsernameToRemove(String usernameToRemove) {
        this.usernameToRemove = usernameToRemove;
        return this;
    }

    public RemoveUserRequest setUserIdToRemove(int userIdToRemove) {
        this.userIdToRemove = userIdToRemove;
        return this;
    }

    public RemoveUserRequest setUserId(int userId) {
        this.userId = userId;
        return this;
    }

}
