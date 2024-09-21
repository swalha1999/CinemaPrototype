package il.cshaifasweng.OCSFMediatorExample.entities.dataTypes;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "support_tickets")
public class SupportTicket implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String subject;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime purchasDayTime;  // Purchase date-time field
    private SupportTicketStatus status;

    // Many-to-one relationship with User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Many-to-one relationship with Screening
    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = true)
    private Screening screening;

    // Many-to-one relationship with Cinema
    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = true)
    private Cinema cinema;

    // Default constructor
    public SupportTicket() {
        this.createdDate = LocalDateTime.now();
        this.status = SupportTicketStatus.OPEN; // Default status
    }

    // Parameterized constructor
    public SupportTicket(String name, String email, String subject, String description, User user, Screening screening, Cinema cinema) {
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.description = description;
        this.createdDate = LocalDateTime.now();
        this.status = SupportTicketStatus.OPEN; // Default status
        this.user = user;
        this.screening = screening;
        this.cinema = cinema;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getPurchasDayTime() {
        return purchasDayTime;
    }

    public void setPurchasDayTime(LocalDateTime purchasDayTime) {
        this.purchasDayTime = purchasDayTime;
    }

    public SupportTicketStatus getStatus() {
        return status;
    }

    public void setStatus(SupportTicketStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    @Override
    public String toString() {
        return "SupportTicket{" +
                "id=" + id +
                ", name='" + (name != null ? name : "N/A") + '\'' +
                ", email='" + (email != null ? email : "N/A") + '\'' +
                ", subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", purchasDayTime=" + purchasDayTime +  // Include purchase date-time in toString()
                ", status=" + status +
                (user != null ? ", user=" + user.getUsername() : ", user=N/A") +  // Handle null user
                (screening != null ? ", screening=" + screening.getId() : ", screening=N/A") +  // Handle null screening
                (cinema != null ? ", cinema=" + cinema.getName() : ", cinema=N/A") +  // Handle null cinema
                '}';
    }
}
