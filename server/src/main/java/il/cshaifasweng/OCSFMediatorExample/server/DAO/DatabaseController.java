package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import org.hibernate.Session;

// this call is the holder of the session and all the DAOs
public class DatabaseController {

    private Session session;
    private MovieDAO movies;
    private UserDAO users;
    private TicketDAO tickets;
    private ScreeningDAO screenings;

    public DatabaseController(Session session) {
        this.session = session;
        this.movies = new MovieDAO(session);
        this.users = new UserDAO(session);
        this.tickets = new TicketDAO(session);
        this.screenings = new ScreeningDAO(session);
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

    public TicketDAO getTicketsManager() {
        return tickets;
    }

    public ScreeningDAO getScreeningsManager() {
        return screenings;
    }


    public void setSession(Session session) {
        this.session = session;
        this.movies = new MovieDAO(session);
        this.users = new UserDAO(session);
        this.tickets = new TicketDAO(session);
        this.screenings = new ScreeningDAO(session);
        // add more DAOs here
    }


}
