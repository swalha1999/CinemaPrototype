package il.cshaifasweng.OCSFMediatorExample.client.data;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieDetails;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ScreeningView {
    private final SimpleIntegerProperty movieId;
    private final SimpleStringProperty cinema;
    private final SimpleStringProperty screeningDate;
    private final SimpleStringProperty hall;
    private final SimpleStringProperty startTime;
    private final SimpleIntegerProperty availableSeats;
    private final SimpleIntegerProperty bookedSeats;
    private final SimpleStringProperty endTime;
    ScreeningView(SimpleIntegerProperty movieId, SimpleStringProperty cinema, SimpleStringProperty screeningDate, SimpleStringProperty hall, SimpleStringProperty startTime, SimpleIntegerProperty availableSeats, SimpleIntegerProperty bookedSeats, SimpleStringProperty endTime){
        this.movieId = movieId;
        this.cinema = cinema;
        this.screeningDate = screeningDate;
        this.hall = hall;
        this.startTime = startTime;
        this.availableSeats = availableSeats;
        this.bookedSeats = bookedSeats;
        this.endTime = endTime;
    }
    public ScreeningView(MovieDetails movie){
        this.movieId = new SimpleIntegerProperty(movie.getMovieId());
        this.cinema = new SimpleStringProperty(movie.getCinema());
        this.screeningDate = new SimpleStringProperty(movie.getScreeningDate());
        this.hall = new SimpleStringProperty(movie.getHall());
        this.startTime = new SimpleStringProperty(movie.getStartTime());
        this.availableSeats = new SimpleIntegerProperty(movie.getAvailableSeats());
        this.bookedSeats = new SimpleIntegerProperty(movie.getBookedSeats());
        this.endTime = new SimpleStringProperty(movie.getEndTime());
    }
    ScreeningView(ScreeningView screeningView){
        this.movieId = screeningView.movieId;
        this.cinema = screeningView.cinema;
        this.screeningDate = screeningView.screeningDate;
        this.hall = screeningView.hall;
        this.startTime = screeningView.startTime;
        this.availableSeats = screeningView.availableSeats;
        this.bookedSeats = screeningView.bookedSeats;
        this.endTime = screeningView.endTime;
    }
    public SimpleIntegerProperty movieIdProperty(){
        return movieId;
    }
    public SimpleStringProperty cinemaProperty(){
        return cinema;
    }
    public SimpleStringProperty screeningDateProperty(){
        return screeningDate;
    }
    public SimpleStringProperty hallProperty(){
        return hall;
    }
    public SimpleStringProperty startTimeProperty(){
        return startTime;
    }
    public SimpleIntegerProperty availableSeatsProperty(){
        return availableSeats;
    }
    public SimpleIntegerProperty bookedSeatsProperty(){
        return bookedSeats;
    }
    public SimpleStringProperty endTimeProperty(){
        return endTime;
    }
    public int getMovieId(){
        return movieId.get();
    }
    public String getCinema(){
        return cinema.get();
    }
    public String getScreeningDate(){
        return screeningDate.get();
    }
    public String getHall(){
        return hall.get();
    }
    public String getStartTime(){
        return startTime.get();
    }
    public int getAvailableSeats(){
        return availableSeats.get();
    }
    public int getBookedSeats(){
        return bookedSeats.get();
    }
    public String getEndTime(){
        return endTime.get();
    }
}
