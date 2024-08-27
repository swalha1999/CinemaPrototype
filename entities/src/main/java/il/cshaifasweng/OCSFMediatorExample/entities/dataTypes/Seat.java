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
    @JoinColumn(name = "hall_id")
    private Screening screening;

    // the SeatLocation class is used to represent the location of the seat in the seat map
    private int seatLocationX;
    private int seatLocationY;
    private int SeatNumber;
    private boolean isAvailable;

    public Seat() {
        this.seatLocationX = 0;
        this.seatLocationY = 0;
        this.SeatNumber = 0;
        this.isAvailable = true;
    }

    public Seat(int seatLocationX, int seatLocationY, int SeatNumber, boolean isAvailable) {
        this.seatLocationX = seatLocationX;
        this.seatLocationY = seatLocationY;
        this.SeatNumber = SeatNumber;
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

    public int getSeatNumber() {
        return SeatNumber;
    }

    public void setSeatLocationX(int seatLocationX) {
        this.seatLocationX = seatLocationX;
    }

    public void setSeatLocationY(int seatLocationY) {
        this.seatLocationY = seatLocationY;
    }

    public void setSeatNumber(int SeatNumber) {
        this.SeatNumber = SeatNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
        this.screening.addSeat(this);
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", seatLocationX=" + seatLocationX +
                ", seatLocationY=" + seatLocationY +
                ", SeatNumber=" + SeatNumber +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
