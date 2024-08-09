package il.cshaifasweng.OCSFMediatorExample.entities.messages.responses;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Response;

public class GetMovieResponse implements Response {

    private static final long serialVersionUID = -5386248350340650196L;

    private Movie movie;
    private boolean success;
    private String message;

    public GetMovieResponse() {
    }

    public GetMovieResponse(GetMovieResponse other) {
        this.movie = other.movie;
        this.success = other.success;
        this.message = other.message;
    }

    public Movie getMovie() {
        return movie;
    }

    public GetMovieResponse setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public GetMovieResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public GetMovieResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String toString() {
        return "GetMovieResponse{" +
                "movie=" + movie +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }




}
