package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class GetMovieEvent  {

    private final Movie movie;
    private final boolean success;
    private final String message;

    public GetMovieEvent(Message response) {
        this.movie = (Movie) response.getDataObject();
        this.success = response.isSuccess();
        this.message = response.getMessage();
    }

    public Movie getMovie() {
        return movie;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
