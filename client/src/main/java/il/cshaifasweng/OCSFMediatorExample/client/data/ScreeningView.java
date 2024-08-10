package il.cshaifasweng.OCSFMediatorExample.client.data;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDateTime;
import java.util.Date;

public class ScreeningView {
    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty availableSeats;
    private final SimpleIntegerProperty bookedSeats;
    private final SimpleIntegerProperty cinema;
    private final SimpleIntegerProperty hall;
    private final SimpleIntegerProperty movieId;
    private final SimpleObjectProperty<LocalDateTime> screeningDate;
    private final SimpleIntegerProperty durationInMinutes;
    private final SimpleIntegerProperty endTime;

    public ScreeningView(int id, int availableSeats, int bookedSeats, int cinema, int hall, int movieId, int screeningDate, int startTime, int endTime) {
        this.id = new SimpleIntegerProperty(id);
        this.availableSeats = new SimpleIntegerProperty(availableSeats);
        this.bookedSeats = new SimpleIntegerProperty(bookedSeats);
        this.cinema = new SimpleIntegerProperty(cinema);
        this.hall = new SimpleIntegerProperty(hall);
        this.movieId = new SimpleIntegerProperty(movieId);
        this.screeningDate = new SimpleObjectProperty<>(LocalDateTime.now());
        this.durationInMinutes = new SimpleIntegerProperty(startTime);
        this.endTime = new SimpleIntegerProperty(endTime);
    }

    public ScreeningView(Screening obj) {
        this.id = new SimpleIntegerProperty(obj.getId());
        this.availableSeats = new SimpleIntegerProperty(obj.getAvailableSeats());
        this.bookedSeats = new SimpleIntegerProperty(obj.getTotalSeats() - obj.getAvailableSeats());
        this.hall = new SimpleIntegerProperty(obj.getHall().getId());
        this.cinema = new SimpleIntegerProperty(obj.getCinema().getId());
        this.movieId = new SimpleIntegerProperty(obj.getMovie().getId());
        this.screeningDate = new SimpleObjectProperty<>(obj.getStartingAt());
        this.durationInMinutes = new SimpleIntegerProperty(obj.getTimeInMinute());
        this.endTime = new SimpleIntegerProperty(obj.getTimeInMinute() + obj.getMovie().getDurationInMinutes());
    }

    // Getter methods for properties
    public int getId() { return id.get(); }
    public int getAvailableSeats() { return availableSeats.get(); }
    public int getBookedSeats() { return bookedSeats.get(); }
    public int getCinema() { return cinema.get(); }
    public int getHall() { return hall.get(); }
    public int getMovieId() { return movieId.get(); }
    public LocalDateTime getScreeningDate() { return screeningDate.get(); }
    public int getDurationInMinutes() { return durationInMinutes.get(); }
    public int getEndTime() { return endTime.get(); }

    // Property methods for JavaFX bindings
    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleIntegerProperty availableSeatsProperty() { return availableSeats; }
    public SimpleIntegerProperty bookedSeatsProperty() { return bookedSeats; }
    public SimpleIntegerProperty cinemaProperty() { return cinema; }
    public SimpleIntegerProperty hallProperty() { return hall; }
    public SimpleIntegerProperty movieIdProperty() { return movieId; }
    public SimpleObjectProperty<LocalDateTime> screeningDateProperty() { return screeningDate; }
    public SimpleIntegerProperty durationInMinutesProperty() { return durationInMinutes; }
    public SimpleIntegerProperty endTimeProperty() { return endTime; }



    @Override
    public String toString() {
        return "ScreeningView{" +
                "id=" + id +
                ", availableSeats=" + availableSeats +
                ", bookedSeats=" + bookedSeats +
                ", cinema=" + cinema +
                ", hall=" + hall +
                ", movieId=" + movieId +
                ", screeningDate=" + screeningDate +
                ", startTime=" + durationInMinutes +
                ", endTime=" + endTime +
                '}';
    }
}
