package il.cshaifasweng.OCSFMediatorExample.entities.dataTypes;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "seats")
public class Seat implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screening_id")
    private Screening screening;

    private int seatLocationX;
    private int seatLocationY;
    private boolean isAvailable;

    @OneToOne(mappedBy = "seat", fetch = FetchType.LAZY)
    private MovieTicket movieTicket;

    public Seat() {
        this.seatLocationX = 0;
        this.seatLocationY = 0;
        this.isAvailable = true;
    }

    public Seat(int seatLocationX, int seatLocationY, int seatNumber, boolean isAvailable) {
        this.seatLocationX = seatLocationX;
        this.seatLocationY = seatLocationY;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public int getSeatLocationX() {
        return seatLocationX;
    }

    public int getSeatLocationY() {
        return seatLocationY;
    }

    public void setSeatLocationX(int seatLocationX) {
        this.seatLocationX = seatLocationX;
    }

    public void setSeatLocationY(int seatLocationY) {
        this.seatLocationY = seatLocationY;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public MovieTicket getMovieTicket() {
        return movieTicket;
    }

    public void setMovieTicket(MovieTicket movieTicket) {
        this.movieTicket = movieTicket;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", seatLocationX=" + seatLocationX +
                ", seatLocationY=" + seatLocationY +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
