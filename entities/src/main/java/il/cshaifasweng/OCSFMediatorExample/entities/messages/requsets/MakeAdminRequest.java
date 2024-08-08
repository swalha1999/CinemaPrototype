package il.cshaifasweng.OCSFMediatorExample.entities.messages.requsets;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Request;

import java.io.Serial;

public class MakeAdminRequest implements Request {
    @Serial
    private static final long serialVersionUID = 1L;

    // the data of the user that makes the request (the admin)
    private String SessionKey;
    private String Username;
    private int UserId;

    private String username_to_make_admin;

    public MakeAdminRequest() {
    }

    public MakeAdminRequest(MakeAdminRequest other) {
        this.SessionKey = other.SessionKey;
        this.Username = other.Username;
        this.UserId = other.UserId;
        this.username_to_make_admin = other.username_to_make_admin;
    }

    public MakeAdminRequest(String SessionKey, String UserName, int UserId, String username_to_make_admin) {
        this.SessionKey = SessionKey;
        this.Username = UserName;
        this.UserId = UserId;
        this.username_to_make_admin = username_to_make_admin;
    }

    public String getSessionKey() {
        return SessionKey;
    }

    public MakeAdminRequest setSessionKey(String sessionKey) {
        this.SessionKey = sessionKey;
        return this;
    }

    public String getUsername() {
        return Username;
    }

    public MakeAdminRequest setUsername(String username) {
        Username = username;
        return this;
    }

    public int getUserId() {
        return UserId;
    }

    public MakeAdminRequest setUserId(int userId) {
        UserId = userId;
        return this;
    }

    public String getUsername_to_make_admin() {
        return username_to_make_admin;
    }

    public MakeAdminRequest setUsername_to_make_admin(String username_to_make_admin) {
        this.username_to_make_admin = username_to_make_admin;
        return this;
    }

    @Override
    public String toString() {
        return "MakeAdminRequest{" +
                "SessionKey='" + SessionKey + '\'' +
                ", Username='" + Username + '\'' +
                ", UserId=" + UserId +
                ", username_to_make_admin='" + username_to_make_admin + '\'' +
                '}';
    }






}
