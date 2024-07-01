package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
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

    public Movie addMovie(Movie addedMovie) {
        Transaction transaction = null;
        Movie movie = null;
        try {
            transaction = session.beginTransaction();
            movie = new Movie(addedMovie.getName(), addedMovie.getDate());
            session.save(movie);
            session.flush();
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

    public Movie getMovie(int id) {
        return session.get(Movie.class, id);
    }
}
