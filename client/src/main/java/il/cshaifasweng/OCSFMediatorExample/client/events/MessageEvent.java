package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class MessageEvent {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public MessageEvent(Message message) {
        this.message = message;
    }
}
