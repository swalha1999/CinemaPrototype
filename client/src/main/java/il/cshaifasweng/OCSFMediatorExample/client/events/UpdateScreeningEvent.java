package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class UpdateScreeningEvent {

    private boolean isSuccess;
    private String message;
    private Screening screening;

    public UpdateScreeningEvent(Message message){
        this.isSuccess = message.isSuccess();
        this.message = message.getMessage();
        this.screening = (Screening) message.getDataObject();
    }

    public String getMessage() {
        return message;
    }

    public Screening getScreening() {
        return screening;
    }
}
