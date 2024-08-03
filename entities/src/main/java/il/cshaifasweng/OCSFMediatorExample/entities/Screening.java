package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "screenings")
public class Screening implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;

    @OneToMany(mappedBy = "screening")
    private Set<MovieTicket> tickets = new HashSet<>();

    private String date;
    private String time;
    private int price;
    private int availableSeats;

    private boolean isOnlineScreening = false; // true if the screening is online, false if it's in a physical hall (default)

    public Screening(Movie movie, Hall hall, String date, String time, int price, int availableSeats) {
        this.movie = movie;
        this.hall = hall;
        this.date = date;
        this.time = time;
        this.price = price;
        this.availableSeats = availableSeats;
        hall.addScreening(this);
    }

    public Screening() {
        this.date = "";
        this.time = "";
        this.price = 0;
        this.availableSeats = 0;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }
}