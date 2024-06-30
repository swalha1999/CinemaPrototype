package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class StickyMessageEvent {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public StickyMessageEvent(Message message) {
        this.message = message;
    }
}
