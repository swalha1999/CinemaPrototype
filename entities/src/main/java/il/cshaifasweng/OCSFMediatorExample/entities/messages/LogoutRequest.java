package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serial;
import java.io.Serializable;

public class LogoutRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -5386248350340650194L;

    private String sessionKey;
    private String username;

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

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {
        return "Session Key: " + sessionKey;
    }

    public boolean isValid() {
        return sessionKey != null && !sessionKey.isEmpty();
    }

    public boolean equals(LogoutRequest logoutRequest) {
        return this.sessionKey.equals(logoutRequest.getSessionKey());
    }

}
