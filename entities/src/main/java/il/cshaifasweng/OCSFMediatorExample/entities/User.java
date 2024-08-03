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

    private boolean isLogged;
    private boolean isBlocked;
    private boolean isDeleted;
    private boolean isAgeRestricted; //TODO: For future use

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

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String password) {
        this.hashedPassword = password;
    }

    public String getUsername() {
        return username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String FirstName) {
        this.firstName = FirstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String LastName) {
        this.lastName = LastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Set<MovieTicket> getTickets() {
        return tickets;
    }

    public void addTicket(MovieTicket ticket) {
        this.tickets.add(ticket);
        ticket.setUser(this);
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

}
