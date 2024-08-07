package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String username;
    private String hashedPassword;
    private String salt;
    private UserRole role;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String creditCard;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieTicket> tickets;

    private int remainingTicketsPurchasedByBundle;

    private Boolean isLogged;
    private Boolean isBlocked;
    private Boolean isDeleted;
    private Boolean isAgeRestricted; //TODO: For future use

    private int NumberOfTicketsPurchased; //TODO: For future use in statistics
    private int NumberOfBundlePurchased; //TODO: For future use in statistics
    private int NumberOfOnlineScreeningsPurchased; //TODO: For future use in statistics

    public User(String username, String password, UserRole role, String email, String phone, String creditCard) {
        this.username = username;
        this.hashedPassword = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.creditCard = creditCard;
        this.isLogged = false;
    }

    public User() {
        this.username = "";
        this.hashedPassword = "";
        this.role = UserRole.USER;
        this.email = "";
        this.phone = "";
        this.creditCard = "";
        this.isLogged = false;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public User setLogged(boolean logged) {
        isLogged = logged;
        return this;
    }

    public User setLoggedIn() {
        return setLogged(true);
    }

    public User setLoggedOut() {
        return setLogged(false);
    }

    public String getCreditCard() {
        return creditCard;
    }

    public User setCreditCard(String creditCard) {
        this.creditCard = creditCard;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserRole getRole() {
        return role;
    }

    public User setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public User setHashedPassword(String password) {
        this.hashedPassword = password;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public User setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public User setBlocked(boolean blocked) {
        isBlocked = blocked;
        return this;
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public User setDeleted(boolean deleted) {
        isDeleted = deleted;
        return this;
    }

    public Set<MovieTicket> getTickets() {
        return tickets;
    }

    public User addTicket(MovieTicket ticket) {
        this.tickets.add(ticket);
        ticket.setUser(this);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public String toString(){
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", salt='" + salt + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", creditCard='" + creditCard + '\'' +
                ", isLogged=" + isLogged +
                ", isBlocked=" + isBlocked +
                ", isDeleted=" + isDeleted +
                ", isAgeRestricted=" + isAgeRestricted +
                ", NumberOfTicketsPurchased=" + NumberOfTicketsPurchased +
                ", NumberOfBundlePurchased=" + NumberOfBundlePurchased +
                ", NumberOfOnlineScreeningsPurchased=" + NumberOfOnlineScreeningsPurchased +
                '}';
    }
}