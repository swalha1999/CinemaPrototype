package il.cshaifasweng.OCSFMediatorExample.entities.messages.requsets;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Request;

import java.io.Serial;

public class GetAllUsersRequest implements Request {
    @Serial
    private static final long serialVersionUID = -5386248350340650199L;

    private String SessionKey;
    private String username;
    private int userId;

    public GetAllUsersRequest() {
    }

    public GetAllUsersRequest(String sessionKey) {
        SessionKey = sessionKey;
    }

    public String getSessionKey() {
        return SessionKey;
    }

    public GetAllUsersRequest setSessionKey(String sessionKey) {
        SessionKey = sessionKey;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public GetAllUsersRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public GetAllUsersRequest setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String toString() {
        return "GetAllUsersRequest{" +
                "SessionKey='" + SessionKey + '\'' +
                ", username='" + username + '\'' +
                ", userId=" + userId +
                '}';
    }
}
