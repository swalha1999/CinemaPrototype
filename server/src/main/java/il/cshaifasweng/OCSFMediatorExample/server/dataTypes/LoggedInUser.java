package il.cshaifasweng.OCSFMediatorExample.server.dataTypes;

import il.cshaifasweng.OCSFMediatorExample.entities.UserRole;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

import java.time.LocalDateTime;

public class LoggedInUser {
    private final String sessionKey;
    private final String username;
    private final ConnectionToClient client;
    private UserRole role;
    private LocalDateTime expirationTime; // TODO: for future use in session expiration

    public LoggedInUser(String sessionKey, String username, UserRole role, ConnectionToClient client) {
        this.sessionKey = sessionKey;
        this.username = username;
        this.role = role;
        this.client = client;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }

    public ConnectionToClient getClient() {
        return client;
    }

    public LoggedInUser setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public LoggedInUser setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
        return this;
    }

    public boolean isExpired() {
        return expirationTime.isBefore(LocalDateTime.now());
    }


}
