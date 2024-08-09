package il.cshaifasweng.OCSFMediatorExample.entities.messages.responses;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Response;

public class RemoveMovieResponse implements Response {
    private String message;
    private boolean success;
    private Movie movie;


    public RemoveMovieResponse() {
        this.message = "RemoveMovieResponse";
        this.success = false;
        this.movie = null;
    }

    public RemoveMovieResponse(boolean success) {
        this.message = "RemoveMovieResponse";
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public RemoveMovieResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Movie getMovie() {
        return movie;
    }

    public RemoveMovieResponse setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public RemoveMovieResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String toString() {
        return "RemoveMovieResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
