package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;

public class DeleteMoviesEvent {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public DeleteMoviesEvent(Message message) {
        this.message = message;
    }
}
