package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import il.cshaifasweng.OCSFMediatorExample.entities.UserRole;

public class AddUserRequest implements Request{
    private String username;
    private UserRole role;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private boolean isLogged;
    private boolean isBlocked;
    private boolean isDeleted;
    public AddUserRequest(String username, UserRole role, String email, String phone, String firstName, String lastName) {
        role = role;
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isLogged = false;
        this.isBlocked = false;
        this.isDeleted = false;
        this.username = username;
    }

    @Override
    public String getSessionKey() {
        return "";
    }

    @Override
    public Request setSessionKey(String sessionKey) {
        return null;
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
        return 0;
    }

    @Override
    public Request setUserId(int userId) {
        return null;
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
    public boolean isLogged() {
        return isLogged;
    }
    public void setLogged(boolean logged) {
        isLogged = logged;
    }
    public boolean isBlocked() {
        return isBlocked;
    }

}
