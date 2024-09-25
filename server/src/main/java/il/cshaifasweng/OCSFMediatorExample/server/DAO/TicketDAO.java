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
        // get the user from the request
        // if the ticket has 3 or more hours before the screening, remove the ticket and add to the balance of the user full price
        // if the ticket has less than 3 hours  and more than 1 hour before the screening, remove the ticket and add to the balance of the user half price
        // else don't remove the ticket and return a message that the ticket cannot be removed

        Message response = new Message(MessageType.REMOVE_TICKET_RESPONSE);

        MovieTicket ticketToDelete = (MovieTicket) request.getDataObject();
        MovieTicket ticket = session.get(MovieTicket.class, ticketToDelete.getId());

        if (ticket == null) {
            return response
                    .setSuccess(false)
                    .setMessage("Ticket not found");
        }

        if (ticket.getIsUsed()) {
            return response
                    .setSuccess(false)
                    .setMessage("Ticket already used");
        }

        if (ticket.getRefunded()) {
            return response
                    .setSuccess(false)
                    .setMessage("Ticket already refunded");
        }

        if (ticket.getBundleTicket()) {
            return response
                    .setSuccess(false)
                    .setMessage("Ticket is part of a bundle cannot be refunded");
        }

        // at this point we know that the ticket can be refunded
        // we free the seat to be available again
        Seat seat = ticket.getSeat();
        seat.setAvailable(true);
        removeNotification(ticket.getNotificationId());

        User user = ticket.getUser();
        if (user == null) {
            return response
                    .setSuccess(false)
                    .setMessage("User not found");
        }

        LocalDateTime screeningTime = ticket.getScreening().getStartingAt();
        LocalDateTime currentTime = LocalDateTime.now();
        long hoursUntilScreening = java.time.Duration.between(currentTime, screeningTime).toHours();

        if (hoursUntilScreening >= 3) {
            // Full refund
            user.setBalance(user.getBalance() + ticket.getScreening().getPrice());
        } else if (hoursUntilScreening >= 1) {
            // Half refund
            user.setBalance(user.getBalance() + (float) ticket.getScreening().getPrice() / 2);
        } else {
            return response
                    .setSuccess(false)
                    .setMessage("Ticket cannot be refunded less than 1 hour before the screening");
        }

        session.beginTransaction();
        session.delete(ticket);
        session.update(user);
        session.update(seat);
        session.getTransaction().commit();

        return response
                .setSuccess(true)
                .setMessage("Ticket removed successfully");

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


    public Message PurchaseBundleTickets(Message request, LoggedInUser loggedInUser) {

        Message response = new Message(MessageType.PURCHASE_TICKETS_BUNDLE_RESPONSE);

        User user  = DatabaseController.getInstance(session).getUsersManager().getUserById(loggedInUser.getUserId());

        user.setRemainingTicketsPurchasedByBundle(user.getRemainingTicketsPurchasedByBundle() + 20);
        user.setNumberOfBundlePurchased(user.getNumberOfBundlePurchased() + 1);

        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();

        return response;
    }

}