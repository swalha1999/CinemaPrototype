package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Seat;

import java.time.LocalDateTime;
import java.util.Set;

public class PurchaseScreeningEvent {
    private String movieTitle;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String userEmail;
    private Set<Seat> seats;

    public PurchaseScreeningEvent(String movieTitle, LocalDateTime startTime, LocalDateTime endTime, String userEmail, Set<Seat> seats) {
        this.movieTitle = movieTitle;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userEmail = userEmail;
        this.seats = seats;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Set<Seat> getSeats() {
        return seats;
    }
}
