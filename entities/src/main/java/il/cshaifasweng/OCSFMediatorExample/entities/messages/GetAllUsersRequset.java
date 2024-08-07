package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serial;

public class GetAllUsersRequset implements Request {
    @Serial
    private static final long serialVersionUID = -5386248350340650199L;

    private String SessionKey;
    private String username;
    private int userId;

    public GetAllUsersRequset() {
    }

    public GetAllUsersRequset(String sessionKey) {
        SessionKey = sessionKey;
    }

    public String getSessionKey() {
        return SessionKey;
    }

    public GetAllUsersRequset setSessionKey(String sessionKey) {
        SessionKey = sessionKey;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public GetAllUsersRequset setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public GetAllUsersRequset setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String toString() {
        return "GetAllUsersRequset{" +
                "SessionKey='" + SessionKey + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
