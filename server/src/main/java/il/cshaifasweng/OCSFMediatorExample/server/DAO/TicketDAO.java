package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
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

    public Message purchaseTicket(Message request) {
        MovieTicket ticketToBuy = (MovieTicket) request.getDataObject();

        // check if the user exists
        User user = session.get(User.class, ticketToBuy.getUser().getId());
        if (user == null) {
            return new Message(MessageType.ERROR).setMessage("User does not exist to purchase ticket");
        }

        // check if the screening exists
        Screening screening = session.get(Screening.class, ticketToBuy.getScreening().getId());
        if (screening == null) {
            return new Message(MessageType.ERROR).setMessage("Screening does not exist to purchase ticket");
        }

        // TODO: check if the seat is available

        MovieTicket ticket = new MovieTicket();
        ticket.setUser(user);
        ticket.setScreening(screening);

        session.save(ticket);

        return new Message(MessageType.PURCHASE_TICKET_REQUEST).setDataObject(ticket);
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
