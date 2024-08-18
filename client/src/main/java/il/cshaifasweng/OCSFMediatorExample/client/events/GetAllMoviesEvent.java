package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

import java.util.List;

public class GetAllMoviesEvent  {

    private final boolean isSuccess;
    private final String message;
    private final List<Movie> movies;

    public GetAllMoviesEvent(Message message) {
        this.isSuccess = message.isSuccess();
        this.message = message.getMessage();
        this.movies = ((List<Movie>) message.getDataObject());
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<Movie> getMovies() {
        return movies;
    }

}
