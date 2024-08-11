package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

import java.util.List;

public class GetScreeningForMovieEvent {

    private final boolean isSuccess;
    private final String message;
    private final List<Screening> screenings;

    public GetScreeningForMovieEvent(Message message) {
        this.isSuccess = message.isSuccess();
        this.message = message.getMessage();
        this.screenings = ((List<Screening>) message.getDataObject());
    }
    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<Screening> getScreenings() {
        return screenings;
    }
}
