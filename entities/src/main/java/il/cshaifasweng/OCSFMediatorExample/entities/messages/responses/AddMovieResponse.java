package il.cshaifasweng.OCSFMediatorExample.entities.messages.responses;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Response;

public class AddMovieResponse implements Response {
    private String message;
    private boolean success;
    private Movie movie;

    public AddMovieResponse() {
        this.message = "AddMovieResponse";
        this.success = false;
    }

    public AddMovieResponse(AddMovieResponse addMovieResponse) {
        this.message = addMovieResponse.getMessage();
        this.success = addMovieResponse.isSuccess();
        this.movie = addMovieResponse.getMovie();
    }

    public AddMovieResponse(boolean success) {
        this.message = "AddMovieResponse";
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public AddMovieResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Movie getMovie() {
        return movie;
    }

    public AddMovieResponse setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public AddMovieResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String toString() {
        return "AddMovieResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
