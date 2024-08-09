package il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;

public class RemoveMoviePatch implements Patch {
    private String Message;
    private Movie movie;

    public RemoveMoviePatch() {
        this.Message = "RemoveMoviePatch";
        this.movie = null;
    }

    public RemoveMoviePatch(RemoveMoviePatch removeUserPatch) {
        this.Message = "RemoveMoviePatch";
        this.movie = removeUserPatch.getMovie();
    }

    public Movie getMovie() {
        return movie;
    }

    public RemoveMoviePatch setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    @Override
    public String getMessage() {
        return Message;
    }

    @Override
    public RemoveMoviePatch setMessage(String message) {
        this.Message = message;
        return this;
    }

    public String toString() {
        return "RemoveMoviePatch{" +
                "movie=" + movie +
                ", Message='" + Message + '\'' +
                '}';
    }

}
