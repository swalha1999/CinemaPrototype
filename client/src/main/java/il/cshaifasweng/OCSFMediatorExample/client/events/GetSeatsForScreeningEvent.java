package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Seat;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

import java.util.List;

public class GetSeatsForScreeningEvent {

    private final boolean isSuccess;
    private final String message;
    private final List<Seat> seats;

    public GetSeatsForScreeningEvent(Message message) {
        this.isSuccess = message.isSuccess();
        this.message = message.getMessage();
        this.seats = ((List<Seat>) message.getDataObject());
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<Seat> getSeats() {
        return seats;
    }

}
