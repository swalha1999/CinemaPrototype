package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import org.hibernate.Session;

public class CinemaDAO {

    private Session session;

    public CinemaDAO(Session session) {
        this.session = session;
    }

    public CinemaDAO setSession(Session session) {
        this.session = session;
        return this;
    }

    public Session getSession() {
        return session;
    }

    public Message getAllCinemas(Message request) {
        Message message = new Message(
    }



}
