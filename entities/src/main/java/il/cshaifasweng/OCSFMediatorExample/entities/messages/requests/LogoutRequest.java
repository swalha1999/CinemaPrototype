package il.cshaifasweng.OCSFMediatorExample.entities.messages.requests;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Request;

import java.io.Serial;

public class LogoutRequest implements Request {
    @Serial
    private static final long serialVersionUID = -5386248350340650194L;

    private String sessionKey;
    private String username;
    private int userId;

    public LogoutRequest() {
    }

    public LogoutRequest(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public LogoutRequest(String sessionKey, String username) {
        this.sessionKey = sessionKey;
        this.username = username;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public LogoutRequest setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public LogoutRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public LogoutRequest setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String toString() {
        return "LogoutRequest{" +
                "sessionKey='" + sessionKey + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public boolean isValid() {
        return sessionKey != null && !sessionKey.isEmpty();
    }

    public boolean equals(LogoutRequest logoutRequest) {
        return this.sessionKey.equals(logoutRequest.getSessionKey());
    }

}
