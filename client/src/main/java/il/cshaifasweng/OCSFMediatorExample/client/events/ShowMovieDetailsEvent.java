package il.cshaifasweng.OCSFMediatorExample.client.events;

public class ShowMovieDetailsEvent {
    private final int movieId;

    public ShowMovieDetailsEvent(int movieId) {
        this.movieId = movieId;
    }

    public int getMovieId() {
        return movieId;
    }
}
