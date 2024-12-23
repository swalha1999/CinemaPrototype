package il.cshaifasweng.OCSFMediatorExample.client.data;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.util.Date;

public class ScreeningView {
    private Screening screening;
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty cinema;
    private SimpleIntegerProperty hall;
    private SimpleIntegerProperty movieId;
    private SimpleObjectProperty<LocalDateTime> screeningDate;
    private SimpleIntegerProperty durationInMinutes;
    private SimpleIntegerProperty endTime;
    private SimpleIntegerProperty price;
    private SimpleStringProperty movieTitle;

    public ScreeningView(Screening obj) {
        this.screening = obj;
        this.id = new SimpleIntegerProperty(obj.getId());
        this.hall = new SimpleIntegerProperty(obj.getHall().getId());
        this.cinema = new SimpleIntegerProperty(obj.getCinema().getId());
        this.movieId = new SimpleIntegerProperty(obj.getMovie().getId());
        this.screeningDate = new SimpleObjectProperty<>(obj.getStartingAt());
        this.durationInMinutes = new SimpleIntegerProperty(obj.getTimeInMinute());
        this.endTime = new SimpleIntegerProperty(obj.getTimeInMinute() + obj.getMovie().getDurationInMinutes());
        this.movieTitle = new SimpleStringProperty(obj.getMovie().getEnglishTitle());
        this.price = new SimpleIntegerProperty(obj.getPrice());
    }

    // Getter methods for properties
    public int getId() { return id.get(); }
    public int getPrice() {return price.get(); }
    public int getCinema() { return cinema.get(); }
    public int getHall() { return hall.get(); }
    public int getMovieId() { return movieId.get(); }
    public LocalDateTime getScreeningDate() { return screeningDate.get(); }
    public int getDurationInMinutes() { return durationInMinutes.get(); }
    public int getEndTime() { return endTime.get(); }
    public Screening getScreening(){ return screening;}

    // Property methods for JavaFX bindings
    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleIntegerProperty cinemaProperty() { return cinema; }
    public SimpleIntegerProperty hallProperty() { return hall; }
    public SimpleIntegerProperty movieIdProperty() { return movieId; }
    public SimpleObjectProperty<LocalDateTime> screeningDateProperty() { return screeningDate; }
    public SimpleIntegerProperty durationInMinutesProperty() { return durationInMinutes; }
    public SimpleIntegerProperty endTimeProperty() { return endTime; }
    public SimpleStringProperty movieTitleProperty() { return movieTitle; }
    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public ScreeningView copy(Screening screening) {
        this.id = new SimpleIntegerProperty(screening.getId()) ;
        this.cinema = new SimpleIntegerProperty(screening.getCinema().getId());
        this.hall = new SimpleIntegerProperty(screening.getHall().getId());
        this.movieId = new SimpleIntegerProperty(screening.getMovie().getId());
        this.screeningDate = new SimpleObjectProperty<>(screening.getStartingAt());
        this.durationInMinutes = new SimpleIntegerProperty(screening.getTimeInMinute());
        this.endTime = new SimpleIntegerProperty(screening.getTimeInMinute() + screening.getMovie().getDurationInMinutes());
        this.movieTitle = new SimpleStringProperty(screening.getMovie().getEnglishTitle());
        this.price = new SimpleIntegerProperty(screening.getPrice());
        return this;
    }


    @Override
    public String toString() {
        return "ScreeningView{" +
                "id=" + id +
                ", cinema=" + cinema +
                ", hall=" + hall +
                ", movieId=" + movieId +
                ", screeningDate=" + screeningDate +
                ", startTime=" + durationInMinutes +
                ", endTime=" + endTime +
                ", price=" + price +
                '}';
    }
}
