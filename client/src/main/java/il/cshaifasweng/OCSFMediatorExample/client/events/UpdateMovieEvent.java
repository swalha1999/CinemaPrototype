package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class UpdateMovieEvent {

    private final String message;
    private final Movie movie;

    public UpdateMovieEvent(Message message) {
        this.movie = (Movie) message.getDataObject();
        this.message = message.getMessage();
    }

    public Movie getMovie() {
        return this.movie;
    }

    public String getMessage() {
        return this.message;
    }
}
