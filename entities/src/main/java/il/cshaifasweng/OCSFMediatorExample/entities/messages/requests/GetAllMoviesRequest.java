package il.cshaifasweng.OCSFMediatorExample.entities.messages.requests;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Request;

import java.io.Serial;

public class GetAllMoviesRequest implements Request {
    @Serial
    private static final long serialVersionUID = -5386248350340650300L;

    private String SessionKey;
    private String username;
    private int userId;

    public GetAllMoviesRequest() {
    }

    public GetAllMoviesRequest(String sessionKey) {
        SessionKey = sessionKey;
    }

    public String getSessionKey() {
        return SessionKey;
    }

    public GetAllMoviesRequest setSessionKey(String sessionKey) {
        SessionKey = sessionKey;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public GetAllMoviesRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public GetAllMoviesRequest setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String toString() {
        return "GetAllMoviesRequest{" +
                "SessionKey='" + SessionKey + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
