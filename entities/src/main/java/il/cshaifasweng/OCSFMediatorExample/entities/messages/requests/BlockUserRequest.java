package il.cshaifasweng.OCSFMediatorExample.entities.messages.requests;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Request;

import java.io.Serial;

public class BlockUserRequest implements Request {
    @Serial
    private static final long serialVersionUID = 1L;

    private String sessionKey;
    private String username;
    private int userId;

    private String usernameToBlock = "admin"; // we cant block the admin so we set it as default
    private int userIdToBlock = 0 ;

    public BlockUserRequest() {
    }

    public BlockUserRequest(String sessionKey, String username, int userId, String usernameToBlock, int userIdToBlock) {
        this.sessionKey = sessionKey;
        this.username = username;
        this.userId = userId;
        this.usernameToBlock = usernameToBlock;
        this.userIdToBlock = userIdToBlock;
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

    public String getUsernameToBlock() {
        return usernameToBlock;
    }

    public int getUserIdToBlock() {
        return userIdToBlock;
    }

    public String toString() {
        return "BlockUserRequest{" +
                "sessionKey='" + sessionKey + '\'' +
                ", username='" + username + '\'' +
                ", userId=" + userId +
                ", usernameToBlock='" + usernameToBlock + '\'' +
                ", userIdToBlock=" + userIdToBlock +
                '}';
    }

    public BlockUserRequest setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    public BlockUserRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public BlockUserRequest setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public BlockUserRequest setUsernameToBlock(String usernameToBlock) {
        this.usernameToBlock = usernameToBlock;
        return this;
    }

    public BlockUserRequest setUserIdToBlock(int userIdToBlock) {
        this.userIdToBlock = userIdToBlock;
        return this;
    }

}
