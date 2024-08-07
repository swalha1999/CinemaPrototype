package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serial;

public class BlockRequest implements Request {
    @Serial
    private static final long serialVersionUID = 1L;
    private String block;
    private String username ;
    private int userid;
    private String SessionKey;
    public BlockRequest(String block) {
        this.block = block;
    }
    public String getBlock() {
        return block;
    }
    public void setBlock(String block) {
        this.block = block;
    }
    public String toString() {
        return block;
    }

    public String getSessionKey() {
        return SessionKey;
    }

    public BlockRequest setSessionKey(String sessionKey) {
        this.SessionKey = sessionKey;
        return this;
    }

    public  String getUsername() {
        return this.username;
    }

    public BlockRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getUserId() {
        return userid;
    }
    public BlockRequest setUserId(int userId) {
        this.userid = userId;
        return this;
    }
}
