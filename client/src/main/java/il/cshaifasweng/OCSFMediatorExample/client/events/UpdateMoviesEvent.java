package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class UpdateMoviesEvent {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public UpdateMoviesEvent(Message message) {
        this.message = message;
    }
}
