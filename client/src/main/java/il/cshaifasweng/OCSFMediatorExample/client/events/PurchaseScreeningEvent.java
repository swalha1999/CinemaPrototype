package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Seat;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

import java.time.LocalDateTime;
import java.util.Set;

public class PurchaseScreeningEvent {

    private final String movieTitle;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String userEmail;
    private final Set<Seat> seats;
    private final Boolean success;
    private final String message;

    public PurchaseScreeningEvent(Message message) {
        this.movieTitle = (String) message.getDataObject();
        this.startTime = (LocalDateTime) message.getDataObject();
        this.endTime = (LocalDateTime) message.getDataObject();
        this.userEmail = (String) message.getDataObject();
        this.seats = (Set<Seat>) message.getDataObject();
        this.success = message.isSuccess();
        this.message = message.getMessage();
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

    public Boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
