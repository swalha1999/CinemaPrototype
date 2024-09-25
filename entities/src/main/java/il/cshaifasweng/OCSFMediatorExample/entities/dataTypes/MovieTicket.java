package il.cshaifasweng.OCSFMediatorExample.entities.dataTypes;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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
    private Screening screening;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    private boolean isUsed;
    private boolean isRefunded;
    private boolean isBundleTicket;
    private LocalDateTime ticketPurchaseDay;
    private String notificationId;

    public MovieTicket() {
        this.user = null;
        this.screening = null;
        this.seat = null;
        this.isUsed = false;
        this.isRefunded = false;
        this.isBundleTicket = false;
        this.ticketPurchaseDay = LocalDateTime.now();
    }

    public MovieTicket(User user, Screening screening, Seat seat) {
        this.user = user;
        this.screening = screening;
        this.seat = seat;
        this.isUsed = false;
        this.isRefunded = false;
        this.isBundleTicket = false;
        this.ticketPurchaseDay = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public boolean getRefunded() {
        return isRefunded;
    }

    public void setRefunded(boolean isRefunded) {
        this.isRefunded = isRefunded;
    }

    public boolean getBundleTicket() {
        return isBundleTicket;
    }

    public void setBundleTicket(boolean isBundleTicket) {
        this.isBundleTicket = isBundleTicket;
    }

    public LocalDateTime getTicketPurchaseDay() {
        return ticketPurchaseDay;
    }

    public void setTicketPurchaseDay(LocalDateTime ticketPurchaseDay) {
        this.ticketPurchaseDay = ticketPurchaseDay;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    @Override
    public String toString() {
        return "MovieTicket{" +
                "id=" + id +
                ", user=" + user.getFirstName() + " " + user.getLastName() +
                ", screening=" + screening.getMovie().getTitle() +
                ", seat=" + seat.toString() +
                ", isUsed=" + isUsed +
                ", isRefunded=" + isRefunded +
                ", ticketPurchaseDay=" + ticketPurchaseDay +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieTicket ticket = (MovieTicket) o;
        return id == ticket.id &&
                isUsed == ticket.isUsed &&
                isRefunded == ticket.isRefunded &&
                isBundleTicket == ticket.isBundleTicket &&
                user.equals(ticket.user) &&
                screening.equals(ticket.screening) &&
                seat.equals(ticket.seat) &&
                ticketPurchaseDay.equals(ticket.ticketPurchaseDay);
    }

}
