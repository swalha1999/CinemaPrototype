package il.cshaifasweng.OCSFMediatorExample.client.data;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.UserRole;

// This class is used to store the session keys of the user that is currently logged in.
// This is a singleton class, meaning that there can only be one instance of this class.
public class SessionKeysStorage {
    private static SessionKeysStorage instance = null;

    private String sessionKey;
    private String username;
    private UserRole role;
    private String email;  // Fixed the typo from "Email" to "email" to follow Java naming conventions.

    // Private constructor to enforce singleton pattern
    private SessionKeysStorage() {
    }

    // Static method to get the singleton instance
    public static SessionKeysStorage getInstance() {
        if (instance == null) {
            instance = new SessionKeysStorage();
        }
        return instance;
    }

    // Getter and setter for sessionKey
    public String getSessionKey() {
        return sessionKey;
    }

    public SessionKeysStorage setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    // Getter and setter for username
    public String getUsername() {
        return username;
    }

    public SessionKeysStorage setUsername(String username) {
        this.username = username;
        return this;
    }

    // Getter and setter for role
    public UserRole getRole() {
        return role;
    }

    public SessionKeysStorage setRole(UserRole role) {
        this.role = role;
        return this;
    }

    // Getter and setter for email
    public String getEmail() {
        return email;
    }

    public SessionKeysStorage setEmail(String email) {
        this.email = email;
        return this;
    }

    // Clear all session data
    public SessionKeysStorage clearSession() {
        sessionKey = null;
        username = null;
        role = null;
        email = null;  // Clear the email as well
        return this;
    }

    @Override
    public String toString() {
        return "SessionKeysStorage{" +
                "sessionKey='" + sessionKey + '\'' +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +  // Added email to the toString() method
                '}';
    }

    // Method to retrieve user email (fixed to return the actual email)
    public String getUserEmail() {
        return email;
    }
}
