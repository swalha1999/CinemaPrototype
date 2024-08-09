package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs.AddMoviePatch;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.AddMovieResponse;

public class AddMoviesEvent extends AddMoviePatch {
    public AddMoviesEvent(AddMoviePatch addMovieResponse) {
        super(addMovieResponse);
    }
}
