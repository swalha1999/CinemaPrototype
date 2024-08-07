package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serial;
import java.io.Serializable;

public class GetMyTicketsRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -5386248350340650198L;

    private String sessionKey;
    private String username;

    public GetMyTicketsRequest() {
    }

    public GetMyTicketsRequest(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public GetMyTicketsRequest setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public GetMyTicketsRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public String toString() {
        return "GetMyTicketsRequest{" +
                "sessionKey='" + sessionKey + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

}
