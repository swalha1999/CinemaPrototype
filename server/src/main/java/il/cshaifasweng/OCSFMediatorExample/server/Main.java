package il.cshaifasweng.OCSFMediatorExample.server;

import java.io.IOException;

import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class Main
{

    private static Session session;
    private static Server server;

    private static SessionFactory getSessionFactory() throws HibernateException {

        Configuration configuration = new Configuration();

//         Add ALL of your entities here. You can also try adding a wholepackage.
        configuration.addAnnotatedClass(Movie.class);


        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static void main( String[] args ) throws IOException
    {
        SessionFactory sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
        server = new Server(3000, session);
        System.out.println("server is listening");
        server.listen();
    }
}
