package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieDetails;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

import java.util.List;

public class GetAllMoviesDetailsEvent {
    private final boolean isSuccess;
    private final String message;
    private final List<MovieDetails> movies;

    public GetAllMoviesDetailsEvent(Message message) {
        this.isSuccess = message.isSuccess();
        this.message = message.getMessage();
        this.movies = ((List<MovieDetails>) message.getDataObject());
    }
    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<MovieDetails> getMoviesDetails() {
        return movies;
    }
}
