package il.cshaifasweng.OCSFMediatorExample.client.data;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserView {
    private final SimpleStringProperty userName;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty email;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty role;
    private final SimpleBooleanProperty isLogged;
    private final SimpleBooleanProperty isBlocked;
    private final SimpleBooleanProperty isDeleted;

    public UserView(String userName, String firstName, String lastName, String email, String phone, String role, boolean isLogged, boolean isBlocked, boolean isDeleted) {
        this.userName = new SimpleStringProperty(userName);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.role = new SimpleStringProperty(role);
        this.isLogged = new SimpleBooleanProperty(isLogged);
        this.isBlocked = new SimpleBooleanProperty(isBlocked);
        this.isDeleted = new SimpleBooleanProperty(isDeleted);
    }

    public UserView(User user) {
        this.userName = new SimpleStringProperty(user.getUsername());
        this.firstName = new SimpleStringProperty(user.getFirstName());
        this.lastName = new SimpleStringProperty(user.getLastName());
        this.email = new SimpleStringProperty(user.getEmail());
        this.phone = new SimpleStringProperty(user.getPhone());
        this.role = new SimpleStringProperty(user.getRole().toString());
        this.isLogged = new SimpleBooleanProperty(user.isLogged());
        this.isBlocked = new SimpleBooleanProperty(user.isBlocked());
        this.isDeleted = new SimpleBooleanProperty(user.isDeleted());
    }

    // Getter methods for properties
    public String getUsername() { return userName.get(); }
    public String getFirstName() { return firstName.get(); }
    public String getLastName() { return lastName.get(); }
    public String getEmail() { return email.get(); }
    public String getPhone() { return phone.get(); }
    public String getRole() { return role.get(); }
    public boolean getIsLogged() { return isLogged.get(); }
    public boolean getIsLocked() { return isBlocked.get(); }
    public boolean getIsDeleted() { return isDeleted.get(); }

    // Property methods for JavaFX bindings
    public SimpleStringProperty userNameProperty() { return userName; }
    public SimpleStringProperty firstNameProperty() { return firstName; }
    public SimpleStringProperty lastNameProperty() { return lastName; }
    public SimpleStringProperty emailProperty() { return email; }
    public SimpleStringProperty phoneProperty() { return phone; }
    public SimpleStringProperty roleProperty() { return role; }
    public SimpleBooleanProperty isLoggedProperty() { return isLogged; }
    public SimpleBooleanProperty isLockedProperty() { return isBlocked; }
    public SimpleBooleanProperty isDeletedProperty() { return isDeleted; }

}