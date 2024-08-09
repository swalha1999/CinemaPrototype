package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {
    int id = 0; // TODO : deprecated field need to be removed

    MessageType type = MessageType.NONE;
    MessageVersion version = MessageVersion.V1;
    LocalDateTime timeStamp = LocalDateTime.now();
    Object dataObject = "No data";

    String message = "no message"; //TODO : deprecated field need to be removed

    public Message(Object dataObject, MessageType type) {
        this.version = MessageVersion.V2;
        this.type = type;
        this.id = type.ordinal();
        this.timeStamp = LocalDateTime.now();
        this.dataObject = dataObject;
    }

    public Message(int id, String message) {
        this.version = MessageVersion.V1;
        this.id = id;
        this.timeStamp = LocalDateTime.now();
        this.message = message;
        this.dataObject = "No data";
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setMessage(String message) {
        this.message = message;
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
                "id=" + id +
                ", type=" + type.toString() +
                ", version=" + version.toString() +
                ", timeStamp=" + timeStamp.format( java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") ) +
                ", dataObject=" + dataObject.toString() +
                ", message='" + message + '\'' +
                '}';
    }

}
