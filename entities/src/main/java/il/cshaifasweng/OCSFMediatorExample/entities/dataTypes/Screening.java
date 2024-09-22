package il.cshaifasweng.OCSFMediatorExample.entities.dataTypes;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

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

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieTicket> tickets = new HashSet<>();

    // Changed to List instead of Set
    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Seat> seats = new HashSet<>();

    // SupportTickets field
    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SupportTicket> supportTickets = new HashSet<>();

    private LocalDateTime startingAt;
    private int timeInMinute;
    private int price;
    private boolean isOnlineScreening = false;

    public Screening(Movie movie, Hall hall, LocalDateTime date, int time, int price, int availableSeats, boolean isOnlineScreening) {
        this.movie = movie;
        this.hall = hall;
        this.startingAt = date;
        this.timeInMinute = time;
        this.price = price;
        this.isOnlineScreening = isOnlineScreening;
        if (!isOnlineScreening) {
            hall.addScreening(this);
        }
    }

    public Screening() {
        this.startingAt = LocalDateTime.now();
        this.timeInMinute = 120;
        this.price = 0;
        }

    public int getId() {
        return id;
    }

    public LocalDateTime getStartingAt() {
        return startingAt;
    }

    public void setStartingAt(LocalDateTime date) {
        this.startingAt = date;
    }

    public int getTimeInMinute() {
        return timeInMinute;
    }

    public void setTimeInMinute(int time) {
        this.timeInMinute = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public Cinema getCinema() {
        return cinema;
    }

    public Screening setCinema(Cinema cinema) {
        this.cinema = cinema;
        return this;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {  // Updated setter to handle a List of seats
        this.seats = seats;
    }

    public void addSeat(Seat seat) {
        this.seats.add(seat);
        if (seat.getScreening() != this) {
            seat.setScreening(this);
        }
    }

    public void removeSeat(Seat seat) {
        this.seats.remove(seat);
    }

    // SupportTickets methods
    public Set<SupportTicket> getSupportTickets() {
        return supportTickets;
    }

    public void addSupportTicket(SupportTicket ticket) {
        this.supportTickets.add(ticket);
        ticket.setScreening(this);
    }

    public void removeSupportTicket(SupportTicket ticket) {
        this.supportTickets.remove(ticket);
        ticket.setScreening(null);
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

    public void setSeats(List<Seat> seats) {
        this.seats = new HashSet<>(seats);

    }
}
