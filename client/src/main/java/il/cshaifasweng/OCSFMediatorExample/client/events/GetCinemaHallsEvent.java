package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Hall;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

import java.util.ArrayList;
import java.util.List;

public class GetCinemaHallsEvent {
    private boolean isSuccess;
    private String message;
    private List<Hall> halls;

    public GetCinemaHallsEvent(Message message) {
        this.isSuccess = message.isSuccess();
        this.message = message.getMessage();
        this.halls = (List<Hall>) message.getDataObject();
    }
}
