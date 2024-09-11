package il.cshaifasweng.OCSFMediatorExample.client.events;

import java.time.LocalDateTime;

public class PurchaseMovieEvent {
    private String movieTitle;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String userEmail;

    public PurchaseMovieEvent(String movieTitle, LocalDateTime startTime, LocalDateTime endTime, String userEmail) {
        this.movieTitle = movieTitle;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userEmail = userEmail;
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
}
