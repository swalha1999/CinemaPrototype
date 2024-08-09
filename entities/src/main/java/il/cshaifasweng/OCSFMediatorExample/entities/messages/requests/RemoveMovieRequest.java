package il.cshaifasweng.OCSFMediatorExample.entities.messages.requests;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Request;

public class RemoveMovieRequest implements Request {
    private static final long serialVersionUID = -5386248350340650195L;

    private String sessionKey;
    private String username;
    private int userId;

    private int movieId;
    private String movieName;

    public RemoveMovieRequest() {
    }

    public RemoveMovieRequest(String sessionKey, int movieId) {
        this.sessionKey = sessionKey;
        this.movieId = movieId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public RemoveMovieRequest setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public RemoveMovieRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public RemoveMovieRequest setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getMovieId() {
        return movieId;
    }

    public RemoveMovieRequest setMovieId(int movieId) {
        this.movieId = movieId;
        return this;
    }

    public String getMovieName() {
        return movieName;
    }

    public RemoveMovieRequest setMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public String toString() {
        return "RemoveMovieRequest{" +
                "sessionKey='" + sessionKey + '\'' +
                ", username='" + username + '\'' +
                ", userId='" + userId + '\'' +
                ", movieId='" + movieId + '\'' +
                '}';
    }
}
