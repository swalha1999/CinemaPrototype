package il.cshaifasweng.OCSFMediatorExample.entities.messages.requests;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Request;

import java.io.Serial;

public class RegisterRequest implements Request {

    @Serial
    private static final long serialVersionUID = -5386248350340650194L;

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    // TODO: Add more fields as needed

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public String getSessionKey() {
        return "";
    }

    @Override
    public RegisterRequest setSessionKey(String sessionKey) {
        return null;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public RegisterRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public int getUserId() {
        return 0;
    }

    @Override
    public RegisterRequest setUserId(int userId) {
        return null;
    }

    public RegisterRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public RegisterRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public RegisterRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public RegisterRequest setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

}
