package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicketStatus;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import il.cshaifasweng.OCSFMediatorExample.server.dataTypes.LoggedInUser;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class SupportTicketDAO {

    private Session session;

    public SupportTicketDAO(Session session) {
        this.session = session;
    }

    public SupportTicketDAO setSession(Session session) {
        this.session = session;
        return this;
    }

    public Session getSession() {
        return session;
    }

    // Fetch all support tickets
    public Message getAllSupportTickets(Message request) {
        Message message = new Message(MessageType.GET_ALL_SUPPORT_TICKETS_RESPONSE);
        List<SupportTicket> tickets = session.createQuery("from SupportTicket", SupportTicket.class).list();

        return message.setSuccess(true)
                .setMessage("All support tickets fetched successfully")
                .setDataObject(tickets);
    }


    public Message addSupportTicket(Message request, LoggedInUser loggedInUser) {
        Message response = new Message(MessageType.ADD_SUPPORT_TICKET_RESPONSE);
        System.out.println("Starting addSupportTicket...");

        // Null check for loggedInUser
        if (loggedInUser == null || loggedInUser.getUserId() == 0) {
            response.setSuccess(false).setMessage("Logged in user information is missing.");
            System.err.println("LoggedInUser is null or userId is 0");
            return response;
        }

        SupportTicket ticketFromUser = (SupportTicket) request.getDataObject();
        if (ticketFromUser == null) {
            response.setSuccess(false).setMessage("Support ticket data is missing.");
            System.err.println("Support ticket data is missing from the request.");
            return response;
        }

        // Fetch the user from the database
        User user = DatabaseController.getInstance(session).getUsersManager().getUserById(loggedInUser.getUserId());
        if (user == null) {
            response.setSuccess(false).setMessage("User not found.");
            System.err.println("User with ID: " + loggedInUser.getUserId() + " not found.");
            return response;
        }

        // Fetch the cinema by ID (assuming the cinema ID is set in ticketFromUser)
        Cinema cinema = DatabaseController.getInstance(session).getCinemaById(ticketFromUser.getCinema().getId());
        if (cinema == null) {
            response.setSuccess(false).setMessage("Cinema not found.");
            System.err.println("Cinema with ID: " + ticketFromUser.getCinema().getId() + " not found.");
            return response;
        }

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            System.out.println("Transaction started.");


            SupportTicket ticket = new SupportTicket();
            ticket.setName(ticketFromUser.getName());
            ticket.setEmail(ticketFromUser.getEmail());
            ticket.setSubject(ticketFromUser.getSubject());
            ticket.setDescription(ticketFromUser.getDescription());
            ticket.setStatus(SupportTicketStatus.OPEN);
            ticket.setUser(user);
            ticket.setCinema(cinema);
            ticket.setCreatedDate(LocalDateTime.now());

            // Log the ticket details before saving
            System.out.println("Saving ticket: " + ticket.getSubject() + " for user: " + user.getUsername());

            // Save the ticket in the database
            session.save(ticket);

            // Add the ticket to the cinema's support tickets
            cinema.addSupportTicket(ticket);

            // Commit the transaction after saving
            transaction.commit();
            System.out.println("Ticket saved successfully and transaction committed.");

            // Set response data object to the created ticket
            response.setDataObject(ticket);

            // Return a success message with the data object included
            response.setSuccess(true).setMessage("Support ticket added successfully.");
            return response;

        } catch (Exception e) {
            // Rollback transaction if there is an exception
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error while adding support ticket: " + e.getMessage());
            e.printStackTrace();

            response.setSuccess(false).setMessage("An error occurred while adding the support ticket.");
            return response;
        }
    }

    public Message deleteSupportTicket(Message request, LoggedInUser loggedInUser) {
        SupportTicket ticketFromUser = (SupportTicket) request.getDataObject();
        SupportTicket ticketFromDB = session.get(SupportTicket.class, ticketFromUser.getId());

        if (ticketFromDB == null) {
            return new Message(MessageType.DELETE_SUPPORT_TICKET_RESPONSE)
                    .setSuccess(false)
                    .setMessage("Support ticket not found");
        }

        session.beginTransaction();
        session.delete(ticketFromDB);
        session.getTransaction().commit();

        return new Message(MessageType.DELETE_SUPPORT_TICKET_RESPONSE)
                .setSuccess(true)
                .setMessage("Support ticket deleted successfully");

    }


}
