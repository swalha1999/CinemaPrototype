package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class AddMoviesEvent {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public AddMoviesEvent(Message message) {
        this.message = message;
    }
}
