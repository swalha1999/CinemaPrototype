package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import org.hibernate.Session;

// this call is the holder of the session and all the DAOs
public class DatabaseController {

    private Session session;
    private final MovieDAO movies;
    private final UserDAO users;
    private final TicketDAO tickets;
    private final ScreeningDAO screenings;
    private final CinemaDAO cinemas;

    public DatabaseController(Session session) {
        this.session = session;
        this.movies = new MovieDAO(session);
        this.users = new UserDAO(session);
        this.tickets = new TicketDAO(session);
        this.screenings = new ScreeningDAO(session);
        this.cinemas = new CinemaDAO(session);
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

    public CinemaDAO getCinemasManager() {
        return cinemas;
    }


    public void setSession(Session session) {
        this.session = session;
        this.movies.setSession(session);
        this.users.setSession(session);
        this.tickets.setSession(session);
        this.screenings.setSession(session);
        this.cinemas.setSession(session);
        // add more DAOs here
    }


}
