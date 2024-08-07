package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serial;
import java.io.Serializable;

public class MakeAdminRequest implements Request{
    @Serial
    private static final long serialVersionUID = 1L;
    private String request;
    private String SessionKey;
    private String UserName;
    private int UserId;
    public MakeAdminRequest(String request) {
        this.request = request;
    }
    public String getRequest() {
        return request;
    }
    public void setRequest(String request) {
        this.request = request;
    }
    public String toString() {
        return request;
    }
    public String getSessionKey() {
        return SessionKey;
    }

    public MakeAdminRequest setSessionKey(String sessionKey) {
       this.SessionKey = sessionKey;
       return this;
    }

    public String getUsername() {
       return UserName;
    }

    @Override
    public MakeAdminRequest setUsername(String username) {
        this.UserName = username;
        return this;
    }

    @Override
    public int getUserId() {
        return this.UserId;
    }

    @Override
    public MakeAdminRequest setUserId(int userId) {
        this.UserId = userId;
        return this;
    }
}
