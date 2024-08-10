package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs.AddMoviePatch;

public class AddMoviesEvent extends AddMoviePatch {
    public AddMoviesEvent(AddMoviePatch addMovieResponse) {
        super(addMovieResponse);
    }
}
