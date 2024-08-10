package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class AddMoviesEvent  {

    Movie movie;
    String Message;

    public AddMoviesEvent(Message message) {
        this.movie = (Movie) message.getDataObject();
        this.Message = message.getMessage();
    }

    public Movie getMovie() {
        return this.movie;
    }

    public String getMessage() {
        return this.Message;
    }

}
