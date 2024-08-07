package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serial;
import java.io.Serializable;

public class UnblockRequest implements Request {
    @Serial
    private static final long serialVersionUID = 1L;
    private String message;
    private String SessionKey;
    private String UserName;
    private int UserId;
    public UnblockRequest(String message) {
        this.message = message;
    }
   public UnblockRequest(String message, String SessionKey, String UserName) {
        this.message = message;
        this.SessionKey = SessionKey;
        this.UserName = UserName;
   }
   public String getMessage() {
        return message;
   }
   public void setMessage(String message) {
        this.message = message;
   }
   public String getSessionKey() {
        return SessionKey;
   }

    @Override
    public UnblockRequest setSessionKey(String sessionKey) {
        SessionKey = sessionKey;
        return this;
    }

    @Override
    public String getUsername() {
        return this.UserName;
    }

    @Override
    public UnblockRequest setUsername(String username) {
        this.UserName = username;
        return this;
    }

    @Override
    public int getUserId() {
        return this.UserId;
    }

    @Override
    public UnblockRequest setUserId(int userId) {
        this.UserId = userId;
        return this;
    }
}
