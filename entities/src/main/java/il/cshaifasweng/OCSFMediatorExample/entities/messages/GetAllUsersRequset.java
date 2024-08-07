package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serial;
import java.io.Serializable;

public class GetAllUsersRequset implements Serializable {
    @Serial
    private static final long serialVersionUID = -5386248350340650199L;

    private String SessionKey;
    private String username;

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

    public String toString() {
        return "GetAllUsersRequset{" +
                "SessionKey='" + SessionKey + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
