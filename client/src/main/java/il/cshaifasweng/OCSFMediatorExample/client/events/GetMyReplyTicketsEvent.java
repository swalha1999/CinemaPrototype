package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class GetMyReplyTicketsEvent {
    private final boolean isSuccess;
    private final String message;
    private final SupportTicket replyTicket;

    public GetMyReplyTicketsEvent(Message message) {
        this.isSuccess = message.isSuccess();
        this.message = message.getMessage();
        this.replyTicket = (SupportTicket) message.getDataObject();
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public SupportTicket getReplyTicket() {
        return replyTicket;
    }
}
