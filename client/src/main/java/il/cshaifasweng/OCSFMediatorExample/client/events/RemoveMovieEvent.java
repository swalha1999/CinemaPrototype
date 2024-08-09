package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs.RemoveMoviePatch;

public class RemoveMovieEvent extends RemoveMoviePatch {
    public RemoveMovieEvent(RemoveMoviePatch removeMoviePatch) {
        super(removeMoviePatch);
    }
}
