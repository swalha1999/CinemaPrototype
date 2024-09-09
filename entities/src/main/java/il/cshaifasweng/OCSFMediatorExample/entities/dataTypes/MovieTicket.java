package il.cshaifasweng.OCSFMediatorExample.entities.dataTypes;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "movie_tickets")
public class MovieTicket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screening_id")
    private Screening screening;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    private boolean isUsed; // Prevent double use of ticket
    private boolean isRefunded; // Marks the ticket as refunded
    private boolean isBundleTicket; // For future use

    public MovieTicket() {
        this.user = null;
        this.seat = null;
        this.isUsed = false;
        this.isRefunded = false;
        this.isBundleTicket = false;
    }

    public MovieTicket(User user, Screening screening, Seat seat) {
        this.user = user;
        this.screening = screening;
        this.seat = seat;
        this.isUsed = false;
        this.isRefunded = false;
        this.isBundleTicket = false;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Screening getScreening() {
        return screening;
    }

    public Seat getSeat() {
        return seat;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public void setRefunded(boolean isRefunded) {
        this.isRefunded = isRefunded;
    }

    public void setBundleTicket(boolean isBundleTicket) {
        this.isBundleTicket = isBundleTicket;
    }

    public boolean getIsRefunded() {
        return isRefunded;
    }

    public boolean getIsBundleTicket() {
        return isBundleTicket;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MovieTicket{" +
                "id=" + id +
                ", user=" + user.getFirstName() + " " + user.getLastName() +
                ", screening=" + screening.getMovie().getTitle() +
                ", seat=" + seat.getSeatNumber() + // Assuming Seat class has a seatNumber or similar
                ", isUsed=" + isUsed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieTicket ticket = (MovieTicket) o;
        return id == ticket.id && user.equals(ticket.user) && screening.equals(ticket.screening) && seat.equals(ticket.seat) && isUsed == ticket.isUsed;
    }

    public boolean isRefunded() {
        return this.isRefunded;
    }

    public boolean isBundleTicket() {
        return this.isBundleTicket;
    }
}
