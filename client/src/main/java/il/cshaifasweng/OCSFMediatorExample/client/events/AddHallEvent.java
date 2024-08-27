package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Hall;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class AddHallEvent {
    boolean isSuccess;
    String message;
    Hall hall;

    public AddHallEvent(Message message) {
        this.isSuccess = message.isSuccess();
        this.message = message.getMessage();
        this.hall = (Hall) message.getDataObject();
    }

    public String getMessage() {
        return message;
    }

    public Hall getHall() {
        return hall;
    }

}
