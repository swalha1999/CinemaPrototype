package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

import java.util.List;

public class GetCinemaSupportTicketsEvent {
    private final boolean success;
    private final String message;
    private final List<SupportTicket> supportTickets;
    public GetCinemaSupportTicketsEvent(Message message) {
        this.success = message.isSuccess();
        this.message = message.getMessage();
        this.supportTickets = ((List<SupportTicket>) message.getDataObject());
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<SupportTicket> getSupportTickets() {
        return supportTickets;
    }
}
