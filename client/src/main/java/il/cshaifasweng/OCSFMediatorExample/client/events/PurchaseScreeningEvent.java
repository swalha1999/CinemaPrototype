package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class PurchaseScreeningEvent {

    private final String message;
    private final Boolean success;
    private final MovieTicket movieTicket;

    public PurchaseScreeningEvent(Message message) {
        this.message = message.getMessage();
        this.success = message.isSuccess();
        this.movieTicket = (MovieTicket) message.getDataObject();
    }

    public String getMessage() {
        return this.message;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public MovieTicket getTicket() {
        return this.movieTicket;
    }
}
