package il.cshaifasweng.OCSFMediatorExample.entities.dataTypes;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
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
    private float balance;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieTicket> tickets = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PriceChangeRequest> priceChangeRequests = new HashSet<>();

    @OneToOne
    private Cinema cinema; // This is used for the manager of the cinema

    private int remainingTicketsPurchasedByBundle;

    private boolean isLogged;
    private boolean isBlocked;
    private boolean isDeleted;
    private boolean isAgeRestricted;

    private int NumberOfTicketsPurchased;
    private int NumberOfBundlePurchased;
    private int NumberOfOnlineScreeningsPurchased;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SupportTicket> supportTickets;

    public User(String username, String password, UserRole role, String email, String phone, String creditCard, Set<SupportTicket> supportTickets) {
        this.username = username;
        this.hashedPassword = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.creditCard = creditCard;
        this.supportTickets = supportTickets;
        this.isLogged = false;
        this.balance = 0.0f;
    }

    public User() {
        this.supportTickets = null;
        this.username = "";
        this.hashedPassword = "";
        this.role = UserRole.USER;
        this.email = "";
        this.phone = "";
        this.creditCard = "";
        this.isLogged = false;
        this.balance = 0.0f;
    }

    // Getters and setters for supportTickets
    public Set<SupportTicket> getSupportTickets() {
        return supportTickets;
    }

    public User setSupportTickets(Set<SupportTicket> supportTickets) {
        this.supportTickets = supportTickets;
        return this;
    }

    // Remaining getters and setters

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

    public User removeTicket(MovieTicket ticket) {
        this.tickets.remove(ticket);
        ticket.setUser(null);
        return this;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public User setCinema(Cinema cinema) {
        this.cinema = cinema;
        return this;
    }

    public int getRemainingTicketsPurchasedByBundle() {
        return remainingTicketsPurchasedByBundle;
    }

    public User setRemainingTicketsPurchasedByBundle(int remainingTicketsPurchasedByBundle) {
        this.remainingTicketsPurchasedByBundle = remainingTicketsPurchasedByBundle;
        return this;
    }

    public boolean isAgeRestricted() {
        return isAgeRestricted;
    }

    public User setAgeRestricted(boolean ageRestricted) {
        isAgeRestricted = ageRestricted;
        return this;
    }

    public int getNumberOfTicketsPurchased() {
        return NumberOfTicketsPurchased;
    }

    public User setNumberOfTicketsPurchased(int numberOfTicketsPurchased) {
        NumberOfTicketsPurchased = numberOfTicketsPurchased;
        return this;
    }

    public int getNumberOfBundlePurchased() {
        return NumberOfBundlePurchased;
    }

    public User setNumberOfBundlePurchased(int numberOfBundlePurchased) {
        NumberOfBundlePurchased = numberOfBundlePurchased;
        return this;
    }

    public int getNumberOfOnlineScreeningsPurchased() {
        return NumberOfOnlineScreeningsPurchased;
    }

    public User setNumberOfOnlineScreeningsPurchased(int numberOfOnlineScreeningsPurchased) {
        NumberOfOnlineScreeningsPurchased = numberOfOnlineScreeningsPurchased;
        return this;
    }

    public float getBalance() {
        return balance;
    }

    public User setBalance(float balance) {
        this.balance = balance;
        return this;
    }

    public Set<PriceChangeRequest> getPriceChangeRequests() {
        return priceChangeRequests;
    }

    public User addPriceChangeRequest(PriceChangeRequest priceChangeRequest) {
        this.priceChangeRequests.add(priceChangeRequest);
        priceChangeRequest.setContentManager(this);
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", balance=" + balance +
                '}';
    }

    public void sendToClient(Message notification) {
        // Add method logic here
    }
}
