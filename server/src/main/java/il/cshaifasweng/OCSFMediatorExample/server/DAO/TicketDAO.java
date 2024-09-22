package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Seat;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import il.cshaifasweng.OCSFMediatorExample.server.dataTypes.LoggedInUser;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        // Create the response message
        Message response = new Message(MessageType.PURCHASE_TICKETS_RESPONSE);

        // Extract the screening from the request
        Screening screeningFromUser = (Screening) request.getDataObject();
        Set<Seat> seats = screeningFromUser.getSeats();

        // Fetch the user from the database using the loggedInUser information
        User user = DatabaseController.getInstance(session).getUsersManager().getUserById(loggedInUser.getUserId());
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

        // Assume only one seat is selected for the ticket
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
            // Begin transaction if none is active
            transaction = session.getTransaction();
            if (transaction == null || !transaction.isActive()) {
                transaction = session.beginTransaction();
            }


            String message = "Reminder: Your movie '" + screening.getMovie().getTitle() + "' starts in 1 hour!";
            String notificationId = addNotification(message,screening.getStartingAt().minusHours(1),loggedInUser);

            MovieTicket ticket = new MovieTicket(user, screening, seat);
            ticket.setIsUsed(false); // Initialize the ticket as not used
            ticket.setRefunded(false); // Initialize the ticket as not refunded
            ticket.setBundleTicket(false); // Initialize the ticket as not a bundle
            ticket.setId(ticketcounter++); // Increment the ticket counter
            ticket.setNotificationId(notificationId); // Initialize the notification ID to null
            seat.setAvailable(false);

            // Save the ticket and update the seat status in the database
            session.update(seat); // Update seat status in the database
            session.save(ticket); // Save the ticket in the database

            // Commit transaction
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }

            // Set response data object to the created ticket
            response.setDataObject(ticket);

            // Return a success message with the data object included
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