package il.cshaifasweng.OCSFMediatorExample.entities.messages.requests;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Request;

public class GetMovieRequest implements Request {
    private static final long serialVersionUID = -5386248350340650195L;

    private String sessionKey;
    private int userId;
    private String username;

    private String movieName;
    private int movieId;


    public GetMovieRequest() {
    }

    public GetMovieRequest(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public GetMovieRequest(String sessionKey, String movieName) {
        this.sessionKey = sessionKey;
        this.movieName = movieName;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public GetMovieRequest setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }

    public String getMovieName() {
        return movieName;
    }

    public GetMovieRequest setMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public GetMovieRequest setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public GetMovieRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getMovieId() {
        return movieId;
    }

    public GetMovieRequest setMovieId(int movieId) {
        this.movieId = movieId;
        return this;
    }


    public String toString() {
        return "GetMovieRequest{" +
                "sessionKey='" + sessionKey + '\'' +
                ", movieName='" + movieName + '\'' +
                '}';
    }
}
