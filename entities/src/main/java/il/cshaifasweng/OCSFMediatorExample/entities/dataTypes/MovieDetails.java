package il.cshaifasweng.OCSFMediatorExample.entities.dataTypes;

public class MovieDetails {
    private int movieId;
    private String cinema;
    private String screeningDate;
    private String hall;
    private String startTime;
    private int availableSeats;
    private int bookedSeats;
    private String endTime;

    // Default constructor
    public MovieDetails() {
    }

    // Parameterized constructor
    public MovieDetails(int movieId, String cinema, String screeningDate, String hall,
                        String startTime, int availableSeats,int bookedSeats, String endTime) {
        this.movieId = movieId;
        this.cinema = cinema;
        this.screeningDate = screeningDate;
        this.hall = hall;
        this.startTime = startTime;
        this.availableSeats = availableSeats;
        this.bookedSeats = bookedSeats;
        this.endTime = endTime;
    }

    // Copy constructor
    public MovieDetails(MovieDetails other) {
        this.movieId = other.movieId;
        this.cinema = other.cinema;
        this.screeningDate = other.screeningDate;
        this.hall = other.hall;
        this.startTime = other.startTime;
        this.availableSeats = other.availableSeats;
        this.bookedSeats = other.bookedSeats;
        this.endTime = other.endTime;
    }

    // Getters and Setters
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public String getScreeningDate() {
        return screeningDate;
    }

    public void setScreeningDate(String screeningDate) {
        this.screeningDate = screeningDate;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(int bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

