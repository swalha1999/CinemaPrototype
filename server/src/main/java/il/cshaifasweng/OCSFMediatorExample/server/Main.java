package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.*;
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
import java.time.LocalDateTime;
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

        // Helper method to create dates
        dates[0] = createDate(calendar, 2024, Calendar.AUGUST, 5);
        dates[1] = createDate(calendar, 2024, Calendar.AUGUST, 6);
        dates[2] = createDate(calendar, 2024, Calendar.AUGUST, 10);
        dates[3] = createDate(calendar, 2024, Calendar.AUGUST, 7);
        dates[4] = createDate(calendar, 2024, Calendar.AUGUST, 16);
        dates[5] = createDate(calendar, 2024, Calendar.AUGUST, 18);
        dates[6] = createDate(calendar, 2024, Calendar.AUGUST, 11);
        dates[7] = createDate(calendar, 2024, Calendar.AUGUST, 6);

        String[] movieTitles = {
                "Mission: Impossible – Dead Reckoning Part Two",
                "Dune: Part Two",
                "Avatar 3",
                "The Marvels",
                "Fantastic Beasts 4",
                "Spider-Man: Beyond the Spider-Verse",
                "Guardians of the Galaxy Vol. 3",
                "Indiana Jones 5"
        };

        String[] hebrewTitles = {
                "משימה בלתי אפשרית: דין ההרס חלק 2",
                "חולית: חלק שני",
                "אווטאר 3",
                "הפלאים",
                "חיות הפלא 4",
                "ספיידרמן: מעבר לעולם העכביש",
                "שומרי הגלקסיה: חלק 3",
                "אינדיאנה ג'ונס 5"
        };

        MovieGenre[] genres = {
                MovieGenre.ACTION,
                MovieGenre.ADVENTURE,
                MovieGenre.FANTASY,
                MovieGenre.SCI_FI,
                MovieGenre.FANTASY,
                MovieGenre.ACTION,
                MovieGenre.ACTION,
                MovieGenre.ADVENTURE
        };

        String[] descriptions = {
                "Ethan Hunt and his IMF team, along with some familiar allies, face a new and deadly threat.",
                "The next chapter in the Dune saga continues the story of Paul Atreides.",
                "The third installment in James Cameron's epic science fiction series.",
                "Carol Danvers, Kamala Khan, and Monica Rambeau team up for an intergalactic adventure.",
                "The continuation of Newt Scamander's adventures in the magical world.",
                "The final chapter of the Spider-Verse trilogy.",
                "The Guardians face their greatest threat yet in this epic conclusion.",
                "Indiana Jones returns for one last adventure."
        };


        for (int i = 0; i < movies.length; i++) {
            movies[i] = new Movie(movieTitles[i], dates[i]);
            movies[i].setGenre(genres[i]);
            movies[i].setHebrewTitle(hebrewTitles[i]);
            movies[i].setDescription(descriptions[i]);

            // Set some movies as online, some as coming soon, and some as both or neither
            if (i < 2) {
                movies[i].setOnlineMovie(true);
            } else if (i < 4) {
                movies[i].setComingSoon(true);
            }
            // The last two movies will be neither online nor coming soon

            // Check if the movie already exists in the database
            Query<Movie> query = session.createQuery("from Movie where name = :name", Movie.class);
            query.setParameter("name", movies[i].getName());
            List<Movie> existingMovies = query.list();

            if (existingMovies.isEmpty()) {
                session.save(movies[i]);
                session.flush();
            } else {
                movies[i] = existingMovies.getFirst(); // Use the existing movie
                // Update the existing movie's online and coming soon status
                movies[i].setOnlineMovie(movies[i].isOnlineMovie());
                movies[i].setComingSoon(movies[i].isComingSoon());
                session.update(movies[i]);
                session.flush();
            }
        }
        List<String> citys = List.of("TEL_AVIV", "JERUSALEM", "HAIFA");
        // Create and save Cinema and Hall entities
        for (String city : citys) {
            Cinema cinema = new Cinema("Cinema City " + city , city, "Rothschild 1", "03-1234567", "email");

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Cinema> criteria = builder.createQuery(Cinema.class);
            Root<Cinema> root = criteria.from(Cinema.class);
            criteria.select(root).where(builder.equal(root.get("city"), city));
            List<Cinema> cinemas = session.createQuery(criteria).getResultList();

            if (!cinemas.isEmpty()) {
                cinema = cinemas.getFirst();
            } else {

                User manager = new User()
                        .setUsername("manager" + city)
                        .setSalt(UserDAO.generateSalt());

                manager.setHashedPassword(UserDAO.hashPassword("password", manager.getSalt()));
                manager.setRole(UserRole.BRANCH_MANAGER);
                manager.setBlocked(false);
                session.save(manager);
                session.flush();

                cinema.setManager(manager);
                session.save(cinema);
                session.flush();
            }

            int numberOfHalls = cinema.getHalls().size();

            for (int i = numberOfHalls; i <= 10; i++) {
                Hall hall = new Hall("Hall" + i).setCinema(cinema);
                session.save(hall);
                session.flush();
            }
        }

        session.flush();
    }

    private static void generateScreening(){
        // check if there is screening in the system
        List<Screening> screenings = session.createQuery("from Screening", Screening.class).list();
        if (!screenings.isEmpty()) {
            return;
        }

        // Generate screenings for every movie
        List<Movie> movies = session.createQuery("from Movie", Movie.class).list();
        List<Cinema> cinemas = session.createQuery("from Cinema", Cinema.class).list();

        for (Movie movie : movies) {
            for (int j = 0; j < 100; j++) {
                Screening screening = new Screening();
                screening.setMovie(movie);
                screening.setCinema(cinemas.get(j % cinemas.size()));
                screening.setHall(cinemas.get(j % cinemas.size()).getHalls().stream().toList().get(j % cinemas.get(j % cinemas.size()).getHalls().size()));
                screening.setStartingAt(LocalDateTime.now());
                screening.setPrice(45);
                session.save(screening);
                session.flush();
            }
        }
    }


    private static Date createDate(Calendar calendar, int year, int month, int day) {
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    private static void generateAdmin() {
        User admin = new User()
                .setUsername("admin")
                .setSalt(UserDAO.generateSalt());

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

    private static void generateTestUser(){
        User user = new User()
                .setUsername("user")
                .setSalt(UserDAO.generateSalt());

        user.setHashedPassword(UserDAO.hashPassword("user", user.getSalt()));
        user.setRole(UserRole.USER);
        user.setBlocked(false);

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(builder.equal(root.get("username"), "user"));
        List<User> users = session.createQuery(criteria).getResultList();

        if (!users.isEmpty()) {
            return;
        }

        session.save(user);
        session.flush();
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
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                session.getTransaction().rollback();
            }
        }
    }
}