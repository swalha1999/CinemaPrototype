package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Seat;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;


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

    public Message purchaseTickets(Message request, int userID) {

        Message response = new Message(MessageType.PURCHASE_TICKETS_RESPONSE);
        Screening screeningFromUser = (Screening) request.getDataObject();
        Set<Seat> seats = screeningFromUser.getSeats();

        User user = DatabaseController.getInstance(session).getUsersManager().getUserById(userID);
        if (user == null) {
            return response.setSuccess(false)
                    .setMessage("User not found");
        }

        Screening screening = session.get(Screening.class, screeningFromUser.getId());
        if (screening == null) {
            return response.setSuccess(false)
                    .setMessage("Screening not found");
        }

        session.beginTransaction();
        for (Seat seatFromUser : seats) {

            Seat seat = session.get(Seat.class, seatFromUser.getId());

            if (seat == null || !seat.isAvailable()) {
                session.getTransaction().rollback();
                return response.setSuccess(false)
                        .setMessage("Seat is not available");
            }

            MovieTicket ticket = new MovieTicket();
            ticket.setScreening(screening);
            ticket.setSeat(seat);
            user.addTicket(ticket);
            seat.setAvailable(false);
            session.update(seat);
            session.save(ticket);
        }
        session.update(user);
        session.getTransaction().commit();

        return response.setSuccess(true)
                .setMessage("Tickets purchased successfully");
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
