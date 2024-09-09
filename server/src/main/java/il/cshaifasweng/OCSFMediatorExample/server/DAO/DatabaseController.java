package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import org.hibernate.Session;

public class DatabaseController {

    private static DatabaseController instance;

    private Session session;
    private final MovieDAO movies;
    private final UserDAO users;
    private final TicketDAO tickets;
    private final ScreeningDAO screenings;
    private final CinemaDAO cinemas;
    private final HallDAO halls;
    private final SeatDAO seats;

    private DatabaseController(Session session) {
        this.session = session;
        this.movies = new MovieDAO(session);
        this.users = new UserDAO(session);
        this.tickets = new TicketDAO(session);
        this.screenings = new ScreeningDAO(session);
        this.cinemas = new CinemaDAO(session);
        this.halls = new HallDAO(session);
        this.seats = new SeatDAO(session);
    }

    public static synchronized DatabaseController getInstance(Session session) {
        if (instance == null) {
            instance = new DatabaseController(session);
        }
        return instance;
    }

    public static synchronized DatabaseController getInstance() {
        return instance;
    }

    public MovieDAO getMoviesManager() {
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

    public HallDAO getHallsManager() {
        return halls;
    }

    public SeatDAO getSeatsManager() {
        return seats;
    }

    public void setSession(Session session) {
        this.session = session;
        this.movies.setSession(session);
        this.users.setSession(session);
        this.tickets.setSession(session);
        this.screenings.setSession(session);
        this.cinemas.setSession(session);
        this.halls.setSession(session);
        this.seats.setSession(session);
        // add more DAOs here if needed
    }
}
