package il.cshaifasweng.OCSFMediatorExample.entities.messages.responses;

import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Response;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class GetAllMoviesResponse implements Response {
    @Serial
    private static final long serialVersionUID = -5386248350340650300L;

    private boolean isSucceed = false;
    private String message;
    private List<Movie> movies = new ArrayList<>();

    public GetAllMoviesResponse() {
    }

    public GetAllMoviesResponse(GetAllMoviesResponse response) {
        this.isSucceed = response.isSucceed;
        this.message = response.message;
        this.movies = response.movies;
    }

    public GetAllMoviesResponse(List<Movie> movies) {
        this.movies = movies;
    }
    
    public GetAllMoviesResponse setMovies(List<Movie> movies) {
        this.movies = movies;
        return this;
    }

    public boolean isSuccess() {
        return isSucceed;
    }

    public GetAllMoviesResponse setSucceed(boolean isSucceed) {
        this.isSucceed = isSucceed;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public GetAllMoviesResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public GetAllMoviesResponse addMovie(Movie movie) {
        this.movies.add(movie);
        return this;
    }

    public String toString() {
        return "GetAllMoviesResponse{" +
                "movies='" + movies + '\'' +
                '}';
    }
}
