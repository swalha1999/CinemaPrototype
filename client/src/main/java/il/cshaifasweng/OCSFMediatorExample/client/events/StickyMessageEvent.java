package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.Messages.Message;

public class StickyMessageEvent {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public StickyMessageEvent(Message message) {
        this.message = message;
    }
}
