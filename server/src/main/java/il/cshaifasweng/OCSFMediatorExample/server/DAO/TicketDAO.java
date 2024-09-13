package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Seat;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import org.hibernate.Session;

import java.util.ArrayList;
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
        // Create the response message
        Message response = new Message(MessageType.PURCHASE_TICKETS_RESPONSE);

        // Extract the screening from the request
        Screening screeningFromUser = (Screening) request.getDataObject();
        Set<Seat> seats = screeningFromUser.getSeats();

        // Fetch the user from the database
        User user = DatabaseController.getInstance(session).getUsersManager().getUserById(userID);
        if (user == null) {
            response.setSuccess(false)
                    .setMessage("User not found");
            return response;
        }

        // Fetch the screening from the database
        Screening screening = session.get(Screening.class, screeningFromUser.getId());
        if (screening == null) {
            response.setSuccess(false)
                    .setMessage("Screening not found");
            return response;
        }

        if (seats.isEmpty()) {
            response.setSuccess(false)
                    .setMessage("No seats selected");
            return response;
        }

        // Process each seat
        for (Seat seatFromUser : seats) {
            System.out.println("Seat From user ID: " + seatFromUser.getId());
            Seat seat = DatabaseController.getInstance(session).getSeatsManager().getSeatById(seatFromUser.getId());

            if (seat == null) {
                response.setSuccess(false)
                        .setMessage("Seat not found");
                return response;
            }

            if (!seat.isAvailable()) {
                response.setSuccess(false)
                        .setMessage("Seat is not available");
                return response;
            }

            // Begin transaction to process the ticket
            session.beginTransaction();
            MovieTicket ticket = new MovieTicket();
            ticket.setScreening(screening);
            ticket.setSeat(seat);
            user.addTicket(ticket);
            seat.setAvailable(false);
            session.update(seat);
            session.save(ticket);
            session.getTransaction().commit();
        }

        // Update user information in the database
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();

        // Prepare additional data for response if needed
        // Example: you might want to include the tickets purchased
        List<MovieTicket> purchasedTickets = new ArrayList<>(user.getTickets());

        // Set response data object
        response.setDataObject(purchasedTickets);

        // Return a success message with the data object included
        response.setSuccess(true)
                .setMessage("Tickets purchased successfully");
        return response;
    }


    public void setSession(Session session) {
        this.session = session;
    }
}
