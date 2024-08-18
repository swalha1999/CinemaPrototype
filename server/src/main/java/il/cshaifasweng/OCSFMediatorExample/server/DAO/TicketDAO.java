package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import org.hibernate.Session;

import java.util.List;


public class TicketDAO {
    private Session session;

    public TicketDAO(Session session) {
        this.session = session;
    }

    public Message getMyTickets(Message request) {
        //TODO change to fetch the user and then get the tickets hash form the user class
        List<MovieTicket> tickets = session.createQuery("from MovieTicket where user = :id", MovieTicket.class)
                .setParameter("id", request.getUserId())
                .getResultList();
        return new Message(MessageType.GET_MY_TICKETS_RESPONSE).setDataObject(tickets);
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
