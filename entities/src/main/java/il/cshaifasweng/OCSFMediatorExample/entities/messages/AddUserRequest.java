package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import il.cshaifasweng.OCSFMediatorExample.entities.UserRole;

public class AddUserRequest implements Request{

    // for the user how made the request (the admin)
    private String sessionKey;
    private int userId;

    // the data to add the new user
    private String username;
    private UserRole role;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;


    public AddUserRequest(String username, UserRole role, String email, String phone, String firstName, String lastName) {
        role = role;
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    @Override
    public String getSessionKey() {
        return "";
    }

    @Override
    public AddUserRequest setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    public String getUsername() {
        return username;
    }
    public AddUserRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public int getUserId() {
        return this.userId;
    }

    @Override
    public AddUserRequest setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
