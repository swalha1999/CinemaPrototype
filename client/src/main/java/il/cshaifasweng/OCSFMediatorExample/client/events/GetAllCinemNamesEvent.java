package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

import java.util.List;

public class GetAllCinemNamesEvent {
    private final boolean isSuccess;
    private final String message;
    private List<String> cinemaNames;


    public GetAllCinemNamesEvent(Message message) {
        this.isSuccess = message.isSuccess();
        this.message = message.getMessage();
        this.cinemaNames= ((List<String>) message.getDataObject());
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getMovies() {
        return cinemaNames;
    }
}
