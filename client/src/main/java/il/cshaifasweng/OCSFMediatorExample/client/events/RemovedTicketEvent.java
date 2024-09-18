package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class RemovedTicketEvent {
    private final String message;
    private final MovieTicket ticket;

    public RemovedTicketEvent(Message message) {
        this.message = message.getMessage();
        this.ticket = (MovieTicket) message.getDataObject();
    }

    public String getMessage() {
        return message;
    }

    public MovieTicket getTicket() {
        return ticket;
    }

}
