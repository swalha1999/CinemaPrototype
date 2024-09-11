package il.cshaifasweng.OCSFMediatorExample.client.events;

import java.time.LocalDateTime;

public class PurchaseMovieEvent {

    private String movieTitle;
    private String movieLink;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String userEmail;

    public PurchaseMovieEvent(String movieTitle, String movieLink, LocalDateTime startTime, LocalDateTime endTime, String userEmail) {
        this.movieTitle = movieTitle;
        this.movieLink = movieLink;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userEmail = userEmail;
    }

    // Method to generate an email message for confirmation
    public String generateEmailMessage() {
        return String.format(
                "Dear customer,\n\nThank you for your purchase! Below are the details of your movie purchase:\n\n" +
                        "Movie Title: %s\n" +
                        "Watch Link: %s\n" +
                        "Available From: %s\n" +
                        "Available Until: %s\n\n" +
                        "Enjoy your movie!\nBest regards,\nCinema Team",
                movieTitle, movieLink, startTime.toString(), endTime.toString()
        );
    }

    // Getters and setters (optional if needed)
    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieLink() {
        return movieLink;
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
