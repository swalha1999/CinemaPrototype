package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.GetMovieRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.RemoveMovieRequest;
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


    public Message getAllMovies(Message request) {
        List<Movie> movies = getMovies();
        return new Message(MessageType.GET_ALL_MOVIES_RESPONSE)
                .setDataObject(movies);
    }


    public Message addMovie(Message request) {
        Message response = new Message(MessageType.ADD_MOVIE_RESPONSE);

        Movie movie = ((Movie) request.getDataObject());

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
            System.err.println("Failed to add movie");
            return response.setSuccess(false).setMessage("Failed to add movie");
        }

        return response
                .setSuccess(true)
                .setMessage("Movie added successfully")
                .setDataObject(movie);

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
