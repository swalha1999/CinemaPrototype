package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

import java.util.List;

public class GetMyResolvedTicketEvent {

    private final boolean isSuccess;
    private final String message;
    private final List<SupportTicket> resolvedTickets;

    public GetMyResolvedTicketEvent(Message message) {
        this.isSuccess = message.isSuccess();
        this.message = message.getMessage();
        this.resolvedTickets = (List<SupportTicket>) message.getDataObject();
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<SupportTicket> getReplyTicket() {
        return resolvedTickets;
    }
}
