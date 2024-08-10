package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
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

    public Message removeMovie(Message request) {
        Message response = new Message(MessageType.REMOVE_MOVIE_RESPONSE);

        Movie movie = session.get(Movie.class, ((Movie) request.getDataObject()).getMovieId());

        if (movie == null) {
            return response.setSuccess(false).setMessage("Movie not found");
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
            System.err.println("Failed to remove movie");
            return response.setSuccess(false).setMessage("Failed to remove movie");
        }

        return response.setSuccess(true).setMessage("Movie removed successfully").setDataObject(movie);
    }

    public Message getMovie(Message getMovieRequest) {
        Message response = new Message(MessageType.GET_MOVIE_RESPONSE);

        Movie movie = session.get(Movie.class, ((Movie) getMovieRequest.getDataObject()).getMovieId() );

        if (movie != null) {
            return response.setSuccess(true).setMessage("Movie found").setDataObject(movie);
        }

        return response.setSuccess(false).setMessage("Movie not found");
    }

    public Message updateMovie(Message request) {
        Message response = new Message(MessageType.UPDATE_MOVIE_RESPONSE);
        Movie movie;
        Movie updatedMovie = ((Movie) request.getDataObject());
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            movie = session.get(Movie.class, updatedMovie.getMovieId());


            if (movie != null) {
                movie.copy(updatedMovie); // copy the none null fields from updatedMovie to movie
                session.update(movie);
                session.flush();
            }
            else {
                return response.setSuccess(false).setMessage("Movie not found");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Failed to update movie");
            return response.setSuccess(false).setMessage("Failed to update movie");
        }

        return response
                .setSuccess(true)
                .setMessage("Movie updated successfully")
                .setDataObject(movie);
    }
}
