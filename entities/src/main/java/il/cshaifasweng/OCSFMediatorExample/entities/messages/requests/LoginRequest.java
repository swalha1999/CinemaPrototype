package il.cshaifasweng.OCSFMediatorExample.entities.messages.requests;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Request;

import java.io.Serial;

public class LoginRequest implements Request {

    @Serial
    private static final long serialVersionUID = -5386248350340650197L;

    private String username;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getSessionKey() {
        return "";
    }

    @Override
    public Request setSessionKey(String sessionKey) {
        return this;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LoginRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public int getUserId() {
        return 0;
    }

    @Override
    public Request setUserId(int userId) {
        return null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(LoginRequest loginRequest) {
        return this.username.equals(loginRequest.getUsername()) && this.password.equals(loginRequest.getPassword());
    }

    public boolean isValid() {
        return username != null && password != null && !username.isEmpty() && !password.isEmpty();
    }

    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
