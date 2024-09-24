package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.*;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import il.cshaifasweng.OCSFMediatorExample.server.dataTypes.LoggedInUser;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static il.cshaifasweng.OCSFMediatorExample.server.Main.session;
import static il.cshaifasweng.OCSFMediatorExample.server.Server.addNotification;
import static il.cshaifasweng.OCSFMediatorExample.server.Server.removeNotification;

public class TicketDAO {
    public Session session;
    public static int ticketcounter = 0;
    public TicketDAO(Session session) {
        this.session = session;
    }

    public Message getMyTickets(Message request, LoggedInUser loggedInUser) {
        Transaction transaction = null;
        List<MovieTicket> tickets = null;

        try {
            // Begin transaction
            transaction = session.beginTransaction();

            // Fetch the user's tickets
            tickets = session.createQuery("from MovieTicket where user.id = :id", MovieTicket.class)
                    .setParameter("id", loggedInUser.getUserId())
                    .getResultList();

            // Initialize lazy-loaded associations if needed
            for (MovieTicket ticket : tickets) {
                Hibernate.initialize(ticket.getSeat()); // Example initialization
            }

            // Commit transaction
            transaction.commit();

            // Return success message with tickets
            return new Message(MessageType.GET_MY_TICKETS_RESPONSE)
                    .setSuccess(true)
                    .setDataObject(tickets);

        } catch (Exception e) {
            // Rollback transaction if there is an exception
            if (transaction != null) {
                transaction.rollback();
            }

            // Log the exception for debugging
            e.printStackTrace();

            // Return error message
            return new Message(MessageType.GET_MY_TICKETS_RESPONSE)
                    .setSuccess(false)
                    .setMessage("An error occurred while fetching tickets.");
        }
    }
    public Message getAllTickets(Message request) {
        Message message = new Message(MessageType.GET_ALL_TICKETS_RESPONSE);
        List<MovieTicket> tickets = session.createQuery("from MovieTicket ", MovieTicket.class).list();

        return message.setSuccess(true)
                .setMessage("All  tickets fetched successfully")
                .setDataObject(tickets);
    }

    public Message removeTicket(Message request, LoggedInUser loggedInUser) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            // Fetch the ticket to be deleted from the request
            MovieTicket ticketToDelete = (MovieTicket) request.getDataObject();

            // Retrieve the ticket from the database
            MovieTicket ticket = session.get(MovieTicket.class, ticketToDelete.getId());

            // Check if the ticket exists
            if (ticket == null) {
                if (transaction != null) transaction.rollback();
                return new Message(MessageType.REMOVE_TICKET_RESPONSE)
                        .setSuccess(false)
                        .setMessage("Ticket not found");
            }

            // Retrieve the seat associated with this ticket
            Seat seat = ticket.getSeat();

            // Check if the seat exists and set its availability to true
            if (seat != null) {
                seat.setAvailable(true);
                session.update(seat); // Update the seat's availability in the database
            }

            // Cancel the notification
            removeNotification(ticket.getNotificationId());

            // Delete the ticket
            session.delete(ticket);

            // Commit the transaction
            transaction.commit();

            // Fetch the updated list of tickets for the user
            List<MovieTicket> updatedTickets = session.createQuery("from MovieTicket where user.id = :id", MovieTicket.class)
                    .setParameter("id", loggedInUser.getUserId())
                    .getResultList();

            // Return a success message with the updated tickets
            return new Message(MessageType.REMOVE_TICKET_RESPONSE)
                    .setSuccess(true)
                    .setMessage("Ticket removed successfully, seat is now available")
                    .setDataObject(updatedTickets);

        } catch (Exception e) {
            // Rollback if there's any exception
            if (transaction != null) transaction.rollback();
            e.printStackTrace(); // Log the exception for debugging
            return new Message(MessageType.REMOVE_TICKET_RESPONSE)
                    .setSuccess(false)
                    .setMessage("An error occurred while removing the ticket");
        }
    }

    public Message purchaseTickets(Message request, LoggedInUser loggedInUser) {
        Message response = new Message(MessageType.PURCHASE_TICKETS_RESPONSE);

        Screening screeningFromUser = (Screening) request.getDataObject();
        Set<Seat> seats = screeningFromUser.getSeats();

        User user = DatabaseController.getInstance(session).getUsersManager().getUserById(loggedInUser.getUserId());
        if (user == null) {
            response.setSuccess(false)
                    .setMessage("User not found");
            return response;
        }

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

        Seat seatFromUser = seats.iterator().next();
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

        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            if (transaction == null || !transaction.isActive()) {
                transaction = session.beginTransaction();
            }

            LocalDateTime purchaseTime = LocalDateTime.now();

            String message = "Reminder: Your movie '" + screening.getMovie().getTitle() + "' starts in 1 hour!";
            String notificationId = addNotification(message, screening.getStartingAt().minusHours(1), loggedInUser);


            MovieTicket ticket = new MovieTicket(user, screening, seat);
            ticket.setIsUsed(false);
            ticket.setRefunded(false);
            ticket.setBundleTicket(false);
            ticket.setId(ticketcounter++);
            ticket.setNotificationId(notificationId);
            ticket.setTicketPurchaseDay(purchaseTime);
            seat.setAvailable(false);

            user.setNumberOfTicketsPurchased(user.getNumberOfTicketsPurchased() + 1);

            session.update(seat);
            session.save(ticket);
            session.update(user);

            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }

            response.setDataObject(ticket);

            response.setSuccess(true)
                    .setMessage("Purchase successful! A confirmation email has been sent to your inbox.");
            return response;

        } catch (Exception e) {
            // Rollback transaction if there is an exception
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            // Log the exception for debugging
            e.printStackTrace();

            // Return error message
            return new Message(MessageType.PURCHASE_TICKETS_RESPONSE)
                    .setSuccess(false)
                    .setMessage("An error occurred while processing the ticket purchase.");
        }
    }

    public void setSession(Session session) {
        this.session = session;
    }
}