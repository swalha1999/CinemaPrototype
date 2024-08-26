package il.cshaifasweng.OCSFMediatorExample.client.events;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

import java.util.List;
public class GetMyTicketsEvent {

    private final boolean success;
    private final String message;
    private final List<MovieTicket> tickets;
    public GetMyTicketsEvent(Message message) {
        this.success = message.isSuccess();
        this.message = message.getMessage();
        this.tickets = ((List<MovieTicket>) message.getDataObject());
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<MovieTicket> getTickets() {
        return tickets;
    }

}
