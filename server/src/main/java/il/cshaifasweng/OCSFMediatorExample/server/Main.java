package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;

import il.cshaifasweng.OCSFMediatorExample.server.DAO.UserDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Session session;

    private static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter password for MYSQL DB connection:");
        String password = myObj.nextLine();  // Read user input
        myObj.close();
        configuration.setProperty("hibernate.connection.password", password);

        // Add ALL of your entities here. You can also try adding a whole package.
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Movie.class);
        configuration.addAnnotatedClass(Actor.class);
        configuration.addAnnotatedClass(MovieTicket.class);
        configuration.addAnnotatedClass(Screening.class);
        configuration.addAnnotatedClass(Hall.class);
        configuration.addAnnotatedClass(Seat.class);
        configuration.addAnnotatedClass(Cinema.class);

        // if the tables do not exist, hibernate will create them
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void generateMovies() throws Exception {
        Movie[] movies = new Movie[8];
        Date[] dates = new Date[8];

        Calendar calendar = Calendar.getInstance();

        calendar.set(2024, Calendar.AUGUST, 5);
        dates[0] = calendar.getTime();
        calendar.set(2024, Calendar.AUGUST, 6);
        dates[1] = calendar.getTime();
        calendar.set(2024, Calendar.AUGUST, 10);
        dates[2] = calendar.getTime();
        calendar.set(2024, Calendar.AUGUST, 7);
        dates[3] = calendar.getTime();
        calendar.set(2024, Calendar.AUGUST, 16);
        dates[4] = calendar.getTime();
        calendar.set(2024, Calendar.AUGUST, 18);
        dates[5] = calendar.getTime();
        calendar.set(2024, Calendar.AUGUST, 11);
        dates[6] = calendar.getTime();
        calendar.set(2024, Calendar.AUGUST, 6);
        dates[7] = calendar.getTime();

        movies[0] = new Movie("Mission: Impossible – Dead Reckoning Part Two", dates[0]);
        movies[1] = new Movie("Dune: Part Two", dates[1]);
        movies[2] = new Movie("Avatar 3", dates[2]);
        movies[3] = new Movie("The Marvels", dates[3]);
        movies[4] = new Movie("Fantastic Beasts 4", dates[4]);
        movies[5] = new Movie("Spider-Man: Beyond the Spider-Verse", dates[5]);
        movies[6] = new Movie("Guardians of the Galaxy Vol. 3", dates[6]);
        movies[7] = new Movie("Indiana Jones 5", dates[7]);

        for (Movie movie : movies) {
            // Check if the movie already exists in the database
            Query<Movie> query = session.createQuery("from Movie where name = :name", Movie.class);
            query.setParameter("name", movie.getName());
            List<Movie> existingMovies = query.list();

            if (existingMovies.isEmpty()) {
                session.save(movie);
                session.flush();
            }
        }
    }

    private static void generateAdmin() {
        User admin = new User()
            .setUsername("admin")
            .setSalt("salt");

        admin.setHashedPassword(UserDAO.hashPassword("admin", admin.getSalt()));
        admin.setRole(UserRole.SYSTEM_MANAGER);
        admin.setBlocked(false);

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(builder.equal(root.get("username"), "admin"));
        List<User> users = session.createQuery(criteria).getResultList();

        if (!users.isEmpty()) {
            return;
        }

        session.save(admin);
        session.flush();
    }

    public static void main(String[] args) {
        try {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();

            session.beginTransaction();
            generateMovies();
            generateAdmin();
            session.getTransaction().commit();

            // Start the server
            Server server = new Server(3000, session);
            System.out.println("Server is listening at port 3000");
            server.listen();
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                session.getTransaction().rollback();
            }
        }
    }
}