package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class RemoveCinemaEvent {
    private final String message;
    private final Cinema cinema;

    public RemoveCinemaEvent(Message message) {
        this.message = message.getMessage();
        this.cinema = (Cinema) message.getDataObject();
    }

    public String getMessage() {
        return message;
    }

    public Cinema getCinema() {
        return cinema;
    }

}
