package il.cshaifasweng.OCSFMediatorExample.client.data;

import il.cshaifasweng.OCSFMediatorExample.entities.UserRole;

// This class is used to store the session keys of the user that is currently logged in.
// This is a singleton class, meaning that there can only be one instance of this class.
public class SessionKeysStorage {
    private static SessionKeysStorage instance = null;

    private String sessionKey;
    private String username;
    private UserRole role;

    private SessionKeysStorage() {
    }

    public static SessionKeysStorage getInstance() {
        if (instance == null) {
            instance = new SessionKeysStorage();
        }
        return instance;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public SessionKeysStorage setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SessionKeysStorage setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserRole getRole() {
        return role;
    }

    public SessionKeysStorage setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public SessionKeysStorage clearSession() {
        sessionKey = null;
        username = null;
        role = null;
        return this;
    }

    public String toString() {
        return "SessionKeysStorage{" +
                "sessionKey='" + sessionKey + '\'' +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}