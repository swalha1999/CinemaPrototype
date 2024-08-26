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

    private int seatNumber; //TODO: replace with seat object for future use
    private boolean isUsed; //TODO: for prvent double use of ticket
    private boolean isRefunded; //TODO: this marks the ticket as refunded and should be used in the future
    private boolean isBundleTicket; //TODO: for future use


    public MovieTicket() {
        this.user = null;
        this.seatNumber = 0;
        this.isUsed = false;
    }

    public MovieTicket(User user, Screening screening, int seatNumber) {
        this.user = user;
        this.screening = screening;
        this.seatNumber = seatNumber;
        this.isUsed = false;
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

    public int getSeatNumber() {
        return seatNumber;
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

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
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

    public void setIsRefunded(boolean isRefunded) {
        this.isRefunded = isRefunded;
    }

    public void setIsBundleTicket(boolean isBundleTicket) {
        this.isBundleTicket = isBundleTicket;
    }



    @Override
    public String toString() {
        return "MovieTicket{" +
                "id=" + id +
                ", user=" + user.getFirstName() + " " + user.getLastName() +
                ", screening=" + screening.getMovie().getTitle() +
                ", seatNumber=" + seatNumber +
                ", isUsed=" + isUsed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieTicket ticket = (MovieTicket) o;
        return id == ticket.id && user.equals(ticket.user) && screening.equals(ticket.screening) && seatNumber == ticket.seatNumber && isUsed == ticket.isUsed;
    }


    public boolean isRefunded() {   return this.isRefunded;
    }

    public boolean isBundleTicket() { return this.isBundleTicket;
    }
}
