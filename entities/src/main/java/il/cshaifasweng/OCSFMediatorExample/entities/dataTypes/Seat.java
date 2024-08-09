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
    private Hall hall;

    // the SeatLocation class is used to represent the location of the seat in the seat map
    private int seatLocationX;
    private int seatLocationY;
    private int SeatAngle;
    private int SeatNumber;
    private boolean isAvailable = true;

    public Seat() {
        this.seatLocationX = 0;
        this.seatLocationY = 0;
        this.SeatAngle = 0;
        this.SeatNumber = 0;
    }

    public Seat(int seatLocationX, int seatLocationY, int SeatAngle, int SeatNumber) {
        this.seatLocationX = seatLocationX;
        this.seatLocationY = seatLocationY;
        this.SeatAngle = SeatAngle;
        this.SeatNumber = SeatNumber;
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

    public int getSeatAngle() {
        return SeatAngle;
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

    public void setSeatAngle(int SeatAngle) {
        this.SeatAngle = SeatAngle;
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

}
