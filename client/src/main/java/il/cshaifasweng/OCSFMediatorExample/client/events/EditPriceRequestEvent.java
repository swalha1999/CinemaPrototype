package il.cshaifasweng.OCSFMediatorExample.client.events;


import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.PriceChangeRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class EditPriceRequestEvent {
    private final PriceChangeRequest request;
    private final boolean success;
    private final String message;

    public EditPriceRequestEvent (Message response) {
        this.request = (PriceChangeRequest) response.getDataObject();
        this.success = response.isSuccess();
        this.message = response.getMessage();
    }

    public PriceChangeRequest priceChangeRequest() {
        return request;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
