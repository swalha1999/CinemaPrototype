package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import org.hibernate.Session;

// this call is the holder of the session and all the DAOs
public class DatabaseController {

    private Session session;
    private MovieDAO movies;
    private UserDAO users;

    public DatabaseController(Session session) {
        this.session = session;
        this.movies = new MovieDAO(session);
        this.users = new UserDAO(session);
    }

    public MovieDAO getMoviesManger() {
        return movies;
    }

    public Session getSession() {
        return session;
    }

    public UserDAO getUsersManager() {
        return users;
    }


    public void setSession(Session session) {
        this.session = session;
        this.movies = new MovieDAO(session);
        this.users = new UserDAO(session);
        // add more DAOs here
    }
}
