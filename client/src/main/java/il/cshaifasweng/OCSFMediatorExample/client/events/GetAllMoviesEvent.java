package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.GetAllMoviesResponse;

public class GetAllMoviesEvent extends GetAllMoviesResponse {

    public GetAllMoviesEvent(GetAllMoviesResponse getAllMoviesEvent) {
        super(getAllMoviesEvent);
    }
}
