package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

import java.util.List;

public class GetAllTicketsEvent {
    private final boolean isSuccess;
    private final String message;
    private final List<MovieTicket> Tickets;

    public GetAllTicketsEvent(Message message) {
        this.isSuccess = message.isSuccess();
        this.message = message.getMessage();
        this.Tickets = ((List<MovieTicket>) message.getDataObject());
    }
    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<MovieTicket> getTickets() {
        return Tickets;
    }
}
