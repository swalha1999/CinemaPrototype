package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageVersion;

public class MessageEvent  {
    private Message message;

    public Message getMessage() {
        return message;
    }



    public MessageEvent(Message message) {
        this.message = message;
        message.setMessage(message.toString());
    }


}
