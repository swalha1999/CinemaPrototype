package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {

    MessageType type = MessageType.NONE;
    MessageVersion version = MessageVersion.V1;
    LocalDateTime timeStamp = LocalDateTime.now();
    String message = "no message";
    Object dataObject = "No data";


    public Message(Object dataObject, MessageType type) {
        this.version = MessageVersion.V2;
        this.type = type;
        this.timeStamp = LocalDateTime.now();
        this.dataObject = dataObject;
    }

    public Message(MessageType type, MessageVersion version) {
        this.type = type;
        this.version = version;
        this.timeStamp = LocalDateTime.now();
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public Message setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getDataObject() {
        return dataObject;
    }

    public Message setDataObject(Object dataObject) {
        this.dataObject = dataObject;
        return this;
    }

    public MessageType getType() {
        return type;
    }

    public MessageVersion getVersion() {
        return version;
    }

    public String toString() {
        return "Message{" +
                ", type=" + type.toString() +
                ", version=" + version.toString() +
                ", timeStamp=" + timeStamp.format( java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") ) +
                ", message='" + message + '\'' +
                ", dataObject=" + dataObject.toString() +
                '}';
    }

}
