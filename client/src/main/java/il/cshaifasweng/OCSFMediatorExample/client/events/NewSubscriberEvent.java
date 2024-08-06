package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.Messages.Message;

public class NewSubscriberEvent {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public NewSubscriberEvent(Message message) {
        this.message = message;
    }
}
