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

    private boolean isOnlineScreening = false; //TODO: for future use if the screening is online from home and should send a link for the user

    public Screening(Movie movie, Hall hall, String date, String time, int price, int availableSeats, boolean isOnlineScreening) {
        this.movie = movie;
        this.hall = hall;
        this.date = date;
        this.time = time;
        this.price = price;
        this.availableSeats = availableSeats;
        this.isOnlineScreening = isOnlineScreening;
        if (!isOnlineScreening) {
            hall.addScreening(this);
        }
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

    public Set<MovieTicket> getTickets() {
        return tickets;
    }

    public void addTicket(MovieTicket ticket) {
        this.tickets.add(ticket);
    }

    public boolean getIsOnlineScreening() {
        return isOnlineScreening;
    }

    public void setIsOnlineScreening(boolean isOnlineScreening) {
        this.isOnlineScreening = isOnlineScreening;
    }

    public void setTickets(Set<MovieTicket> tickets) {
        this.tickets = tickets;
    }

    public void addHall(Hall hall) {
        this.hall = hall;
    }

    public void removeHall(Hall hall) {
        this.hall = null;
    }

    public void removeMovie(Movie movie) {
        this.movie = null;
    }

    public void removeTicket(MovieTicket ticket) {
        this.tickets.remove(ticket);
    }




}