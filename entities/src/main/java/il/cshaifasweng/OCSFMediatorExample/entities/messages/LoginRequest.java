package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serializable;

public class LoginRequest implements Serializable{

    private String username;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
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
        return "Username: " + username + ", Password: " + password;
    }

}
