package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.GetMovieResponse;

public class GetMovieEvent extends GetMovieResponse {
    public GetMovieEvent(GetMovieResponse response) {
        super(response);
    }
}
