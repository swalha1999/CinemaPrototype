package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.AddMovieResponse;

public class AddMoviesEvent extends AddMovieResponse {


    public AddMoviesEvent(AddMovieResponse addMovieResponse) {
        super(addMovieResponse);
    }
}
