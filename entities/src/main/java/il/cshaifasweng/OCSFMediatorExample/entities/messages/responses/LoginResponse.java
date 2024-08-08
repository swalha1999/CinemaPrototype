package il.cshaifasweng.OCSFMediatorExample.entities.messages.responses;

import il.cshaifasweng.OCSFMediatorExample.entities.UserRole;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Response;

import java.io.Serial;
import java.util.Objects;

public class LoginResponse implements Response {
    @Serial
    private static final long serialVersionUID = -5386248350340650194L;

    private boolean success;
    private String message;
    private String username;
    private String sessionKey;
    private UserRole role;
    private int userId;

    public LoginResponse() {
    }


    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // if the login was successful, the sessionID and role are set
    public LoginResponse(boolean success, String message, String sessionID, UserRole role) {
        this.success = success;
        this.message = message;
        this.sessionKey = sessionID;
        this.role = role;
    }

    public LoginResponse(LoginResponse other) {
        this.success = other.success;
        this.message = other.message;
        this.username = other.username;
        this.sessionKey = other.sessionKey;
        this.userId = other.userId;
        this.role = other.role;
    }

    public UserRole getRole() {
        return role;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public LoginResponse setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    public LoginResponse setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public LoginResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public LoginResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public LoginResponse setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public LoginResponse setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", username='" + username + '\'' +
                ", sessionKey='" + sessionKey + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LoginResponse that = (LoginResponse) obj;
        return success == that.success && message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message);
    }

    public boolean isValid() {
        return message != null && !message.isEmpty();
    }

    public boolean isSuccessful() {
        return success;
    }

    public boolean isFailure() {
        return !success;
    }
}