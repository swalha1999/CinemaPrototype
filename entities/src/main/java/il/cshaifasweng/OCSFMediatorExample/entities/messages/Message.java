package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {
    int id; // TODO : deprecated field need to be removed

    MessageType type;
    MessageVersion version;
    LocalDateTime timeStamp;
    Object dataObject;

    User user; //TODO : deprecated field need to be removed
    String message; //TODO : deprecated field need to be removed
    String data; //TODO : deprecated field need to be removed
    List<Movie> movies; //TODO : deprecated field need to be removed

    public Message(Object dataObject, MessageType type) {
        this.version = MessageVersion.V2;
        this.type = type;
        this.id = type.ordinal();
        this.timeStamp = LocalDateTime.now();
        this.dataObject = dataObject;
    }

    public Message(int id, LocalDateTime timeStamp, String message) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.message = message;
        this.version = MessageVersion.V1;
    }

    public Message(int id, String message) {
        this.id = id;
        this.timeStamp = LocalDateTime.now();
        this.message = message;
        this.data = null;
        this.version = MessageVersion.V1;
    }

    public Message(int id, String message, String data) {
        this.id = id;
        this.timeStamp = LocalDateTime.now();
        this.message = message;
        this.data = data;
        this.version = MessageVersion.V1;
    }

    public Message(int id, String message, List<Movie> movies) {
        this.id = id;
        this.timeStamp = LocalDateTime.now();
        this.message = message;
        this.movies = movies;
        this.version = MessageVersion.V1;
    }

    public static Message createLoginRequest(String username, String password){
        Message message = new Message(2, "login");
        message.setUser(new User());
        message.getUser().setUsername(username);
        message.setData(password);
        return message;
    }
    public boolean addMovie(Movie movie) {
        if(this.movies==null){
            this.movies = new ArrayList<Movie>();
        }
        if (this.movies.contains(movie)){
            return false;
        }
        return movies.add(movie);
    }

    public boolean removeMovie(Movie movie) {
        return movies.remove(movie);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                ", user=" + user +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                ", movies=" + movies +
                '}';
    }

}
