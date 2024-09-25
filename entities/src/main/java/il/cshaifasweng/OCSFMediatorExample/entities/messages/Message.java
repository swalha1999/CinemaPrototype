package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import org.hibernate.loader.plan.exec.internal.OneToManyLoadQueryDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {

    MessageType type = MessageType.NONE;
    MessageVersion version = MessageVersion.V1;
    LocalDateTime timeStamp = LocalDateTime.now();

    // the user that sent the message
    String SessionKey;
    String username;
    int userId;

    // the data sent in the message
    String message = "no message";
    Object dataObject = "No data";
    String dataType = "No data type";

    //another data object for the V3 version of the message
    Object dataObject2 = "No data";
    String dataType2 = "No data type";

    // for the V3 version of the message
    boolean success = false;


    public Message(Object dataObject, MessageType type) {
        this.version = MessageVersion.V2;
        this.type = type;
        this.timeStamp = LocalDateTime.now();
        setDataObject(dataObject);
    }

    public Message(MessageType type, MessageVersion version) {
        this.type = type;
        this.version = version;
        this.timeStamp = LocalDateTime.now();
    }

    // This is only for the V3 version of the message
    public Message(MessageType type) {
        this.type = type;
        this.version = MessageVersion.V3;
        this.timeStamp = LocalDateTime.now();
    }

    public Message() {

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
        this.dataType = dataObject == null ? "null" :  dataObject.getClass().getSimpleName();
        return this;
    }

    public MessageType getType() {
        return type;
    }

    public MessageVersion getVersion() {
        return version;
    }

    public String getSessionKey() {
        return SessionKey;
    }

    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }

    public Message setSessionKey(String SessionKey) {
        this.SessionKey = SessionKey;
        return this;
    }

    public Message setUsername(String username) {
        this.username = username;
        return this;
    }

    public Message setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getDataType() {
        return dataType;
    }

    public boolean isSuccess() {
        return success;
    }

    public Message setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Message setMessageType(MessageType type) {
        this.type = type;
        return this;
    }

    public Object getDataObject2() {
        return dataObject2;
    }

    public Message setDataObject2(Object dataObject2) {
        this.dataObject2 = dataObject2;
        this.dataType2 = dataObject2 == null ? "null" :  dataObject2.getClass().getSimpleName();
        return this;
    }

    public String getDataType2() {
        return dataType2;
    }



    public String toString() {
        return "Message{" +
                ", type=" + type.toString() +
                ", version=" + version.toString() +
                ", timeStamp=" + timeStamp.format( java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") ) +
                ", SessionKey='" + SessionKey + '\'' +
                ", username='" + username + '\'' +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                ", dataType='" + dataType + '\'' +
                ", dataObject=" + dataObject.toString() +
                '}';
    }


}
