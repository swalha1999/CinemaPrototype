package il.cshaifasweng.OCSFMediatorExample.entities;

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

    private int seatNumber;
    private boolean isUsed;


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


}
