package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.DAO.ScreeningDAO;
import il.cshaifasweng.OCSFMediatorExample.server.Server;
import il.cshaifasweng.OCSFMediatorExample.server.dataTypes.ScreeningTimer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Set;

public class Main {

    private static Session session;

    private static SessionFactory getSessionFactory() throws HibernateException {
        // ... existing implementation ...
    }

    private static void generateMovies() {
        // ... existing implementation ...
    }

    private static void generateScreening() {
        // ... existing implementation ...
    }

    private static void generateAdmin() {
        // ... existing implementation ...
    }

    private static void generateTestUser() {
        // ... existing implementation ...
    }

    public static void main(String[] args) {
        try {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();

            session.beginTransaction();
            generateMovies();
            generateAdmin();
            generateTestUser();
            generateScreening();
            session.getTransaction().commit();

            // Start the server
            Server server = new Server(3000, session);
            System.out.println("Server is listening at port 3000");
            server.listen();

            // Get the connected clients
           // Set<ConnectionToClient> connectedClients = server.getClientConnections();

            // Create ScreeningTimer
            ScreeningDAO screeningDAO = new ScreeningDAO(session);
           // ScreeningTimer screeningTimer = new ScreeningTimer(screeningDAO, connectedClients);
           // screeningTimer.start(); // Start the timer to check every minute
         // TODO : muhamad zbt alwd3 hen
            // Add shutdown hook to stop the timer on application exit
           // Runtime.getRuntime().addShutdownHook(new Thread(screeningTimer::stop));
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                session.getTransaction().rollback();
            }
        }
    }
}
