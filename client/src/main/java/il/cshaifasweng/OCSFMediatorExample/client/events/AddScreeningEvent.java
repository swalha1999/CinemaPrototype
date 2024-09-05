package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class AddScreeningEvent {
    boolean isSuccess;
    String message;
    Screening screening;

    public AddScreeningEvent(Message message) {
        this.isSuccess = message.isSuccess();
        this.message = message.getMessage();
        this.screening = (Screening) message.getDataObject();
    }

    public String getMessage() {
        return message;
    }

    public Screening getHall() {
        return screening;
    }
}
