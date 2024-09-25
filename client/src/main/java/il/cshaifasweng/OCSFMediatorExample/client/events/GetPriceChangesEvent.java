package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.PriceChangeRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

import java.util.List;

public class GetPriceChangesEvent {
    private final boolean success;
    private final String message;
    private final List<PriceChangeRequest> priceChangeRequests;

    public GetPriceChangesEvent(Message message) {
        this.success = message.isSuccess();
        this.message = message.getMessage();
        this.priceChangeRequests = ((List<PriceChangeRequest>) message.getDataObject());
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<PriceChangeRequest> getPriceChangeRequests() {
        return priceChangeRequests;
    }
}
