package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

import java.util.List;

public class GetAllCinemasEvent {
    private final boolean isSuccess;
    private final String message;
    private final List<Cinema> cinemas;

    public GetAllCinemasEvent(Message message) {
        this.isSuccess = message.isSuccess();
        this.message = message.getMessage();
        this.cinemas = ((List<Cinema>) message.getDataObject());
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<Cinema> getCinemas() {
        return cinemas;
    }

}
