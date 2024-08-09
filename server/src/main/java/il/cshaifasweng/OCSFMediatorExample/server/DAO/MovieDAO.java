package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.AddMovieRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.GetAllMoviesRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.GetMovieRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.RemoveMovieRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.AddMovieResponse;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.GetAllMoviesResponse;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.GetMovieResponse;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.RemoveMovieResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class MovieDAO {

    private Session session;

    public MovieDAO(Session session) {
        this.session = session;
    }

    public Movie deleteMovie(Movie deletedMovie) {
        Transaction transaction = null;
        Movie movie = null;
        try {
            transaction = session.beginTransaction();
            movie = session.get(Movie.class, deletedMovie.getId());
            if (movie != null) {
                session.delete(movie);
                session.flush();
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return movie;
    }

    public Movie editMovie(Movie editedMovie) {
        Transaction transaction = null;
        Movie movie = null;
        try {
            transaction = session.beginTransaction();
            movie = session.get(Movie.class, editedMovie.getId());
            if (movie != null) {
                movie.setName(editedMovie.getName());
                movie.setDate(editedMovie.getDate());
                session.update(movie);
                session.flush();
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return movie;
    }

    public List<Movie> getMovies() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Movie> query = builder.createQuery(Movie.class);
        query.from(Movie.class);
        return session.createQuery(query).getResultList();
    }


    public GetAllMoviesResponse getAllMovies(GetAllMoviesRequest getAllMoviesRequest) {
        List<Movie> movies = getMovies();
        return new GetAllMoviesResponse(movies).setSucceed(true).setMessage("Movies retrieved successfully");
    }


    public AddMovieResponse addMovie(AddMovieRequest addMovieRequest) {
        Movie movie = new Movie();
        movie.setDate(addMovieRequest.getReleaseDate());
        movie.setName(addMovieRequest.getName());
        movie.setDescription(addMovieRequest.getDescription());
        movie.setLanguage(addMovieRequest.getLanguage());
        movie.setGenre(addMovieRequest.getGenre());
        movie.setCountry(addMovieRequest.getCountry());
        movie.setHebrewTitle(addMovieRequest.getHebrewTitle());
        // TODO: movie.setImageUrl(addMovieRequest.getImageUrl());

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(movie);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return new AddMovieResponse().setSuccess(false).setMessage("Failed to add movie");
        }
        return new AddMovieResponse().setSuccess(true).setMessage("Movie added successfully").setMovie(movie);

    }

    public RemoveMovieResponse removeMovie(RemoveMovieRequest removeMovieRequest) {
        // get  the movie to be removed
        Movie movie = session.get(Movie.class, removeMovieRequest.getMovieId());
        if (movie == null) {
            return new RemoveMovieResponse().setSuccess(false).setMessage("Movie not found");
        }

        // delete the movie
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(movie);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return new RemoveMovieResponse().setSuccess(false).setMessage("Failed to remove movie");
        }

        return new RemoveMovieResponse().setSuccess(true).setMessage("Movie removed successfully").setMovie(movie);
    }

    public GetMovieResponse getMovie(GetMovieRequest getMovieRequest) {
        Movie movie = session.get(Movie.class, getMovieRequest.getMovieId());
        if (movie != null) {
            return new GetMovieResponse().setSuccess(true).setMessage("Movie retrieved successfully").setMovie(movie);
        }

        return new GetMovieResponse().setSuccess(false).setMessage("Movie not found");
    }

}
