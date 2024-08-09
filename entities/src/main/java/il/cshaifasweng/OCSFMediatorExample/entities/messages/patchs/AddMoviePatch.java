package il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;

public class AddMoviePatch implements Patch{
    Movie movie;
    String Message;

    public AddMoviePatch() {
        this.Message = "AddMoviePatch";
        this.movie = null;
    }

    public AddMoviePatch(AddMoviePatch addMoviePatch) {
        this.Message = addMoviePatch.getMessage();
        this.movie = addMoviePatch.getMovie();
    }

    public Movie getMovie() {
        return movie;
    }

    public AddMoviePatch setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    @Override
    public String getMessage() {
        return Message;
    }

    @Override
    public AddMoviePatch setMessage(String message) {
        this.Message = message;
        return this;
    }

    public String toString() {
        return "AddMoviePatch{" +
                "movie=" + movie +
                ", Message='" + Message + '\'' +
                '}';
    }
}
