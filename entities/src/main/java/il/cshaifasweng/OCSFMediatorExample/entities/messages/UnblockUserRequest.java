package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serial;

public class UnblockUserRequest implements Request {
    @Serial
    private static final long serialVersionUID = 1L;

    private String sessionKey;
    private String username;
    private int userId;

    private String usernameToUnblock = "admin"; // we cant block the admin so we set it as default
    private int userIdToUnblock = 0 ;

    public UnblockUserRequest() {
    }

    public UnblockUserRequest(String sessionKey, String username, int userId, String usernameToBlock, int userIdToBlock) {
        this.sessionKey = sessionKey;
        this.username = username;
        this.userId = userId;
        this.usernameToUnblock = usernameToBlock;
        this.userIdToUnblock = userIdToBlock;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsernameToUnblock() {
        return usernameToUnblock;
    }

    public int getUserIdToUnblock() {
        return userIdToUnblock;
    }

    public String toString() {
        return "UnblockRequest{" +
                "sessionKey='" + sessionKey + '\'' +
                ", username='" + username + '\'' +
                ", userId=" + userId +
                ", usernameToUnblock='" + usernameToUnblock + '\'' +
                ", userIdToUnblock=" + userIdToUnblock +
                '}';
    }

    public UnblockUserRequest setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    public UnblockUserRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public UnblockUserRequest setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public UnblockUserRequest setUsernameToUnblock(String usernameToUnblock) {
        this.usernameToUnblock = usernameToUnblock;
        return this;
    }

    public UnblockUserRequest setUserIdToUnblock(int userIdToUnblock) {
        this.userIdToUnblock = userIdToUnblock;
        return this;
    }
}
