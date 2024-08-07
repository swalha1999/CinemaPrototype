package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serializable;

public class RemoveUserRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private int userId;
    public RemoveUserRequest() {
    }
    public RemoveUserRequest(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String toString() {
        return "RemoveUserRequest [userId=" + userId + "]";
    }
}
