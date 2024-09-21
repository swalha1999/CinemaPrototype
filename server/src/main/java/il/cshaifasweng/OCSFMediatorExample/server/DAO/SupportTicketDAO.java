package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicketStatus;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import il.cshaifasweng.OCSFMediatorExample.server.dataTypes.LoggedInUser;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

//    // Fetch a single support ticket by ID
//    public Message getSupportTicket(Message request) {
//        Message message = new Message(MessageType.GET_SUPPORT_TICKET_RESPONSE);
//        SupportTicket ticket = (SupportTicket) request.getDataObject();
//        SupportTicket ticketFromDB = session.get(SupportTicket.class, ticket.getId());
//
//        if (ticketFromDB != null) {
//            return message.setSuccess(true)
//                    .setMessage("Support ticket fetched successfully")
//                    .setDataObject(ticketFromDB);
//        } else {
//            return message.setSuccess(false)
//                    .setMessage("Support ticket not found");
//        }
//    }

    // Add a new support ticket
    public Message addSupportTicket(Message request, LoggedInUser loggedInUser) {
        // Create the response message
        Message response = new Message(MessageType.ADD_SUPPORT_TICKET_RESPONSE);

        // Log for debugging
        System.out.println("Starting addSupportTicket...");

        // Null check for loggedInUser
        if (loggedInUser == null || loggedInUser.getUserId() == 0) {
            response.setSuccess(false).setMessage("Logged in user information is missing.");
            System.err.println("LoggedInUser is null or userId is 0");
            return response;
        }

        // Extract the support ticket details from the request
        SupportTicket ticketFromUser = (SupportTicket) request.getDataObject();
        if (ticketFromUser == null) {
            response.setSuccess(false).setMessage("Support ticket data is missing.");
            System.err.println("Support ticket data is missing from the request.");
            return response;
        }

        // Fetch the user from the database using the loggedInUser information
        User user = DatabaseController.getInstance(session).getUsersManager().getUserById(loggedInUser.getUserId());
        if (user == null) {
            response.setSuccess(false).setMessage("User not found.");
            System.err.println("User with ID: " + loggedInUser.getUserId() + " not found.");
            return response;
        }

        Transaction transaction = null;
        try {
            // Start a new transaction
            transaction = session.beginTransaction();
            System.out.println("Transaction started.");

            // Create a new support ticket and populate its fields
            SupportTicket ticket = new SupportTicket();
            ticket.setName(ticketFromUser.getName());
            ticket.setEmail(ticketFromUser.getEmail());
            ticket.setSubject(ticketFromUser.getSubject());
            ticket.setDescription(ticketFromUser.getDescription());
            ticket.setStatus(SupportTicketStatus.OPEN);  // Default status as OPEN
            ticket.setUser(user);  // Associate the ticket with the user

            // Log the ticket details before saving
            System.out.println("Saving ticket: " + ticket.getSubject() + " for user: " + user.getUsername());

            // Save the ticket in the database
            session.save(ticket);

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
            // Log the exception
            System.err.println("Error while adding support ticket: " + e.getMessage());
            e.printStackTrace();

            // Return error message
            response.setSuccess(false).setMessage("An error occurred while adding the support ticket.");
            return response;
        }
    }







//    // Update a support ticket
//    public Message updateSupportTicket(Message request) {
//        SupportTicket ticketFromUser = (SupportTicket) request.getDataObject();
//        SupportTicket ticketFromDB = session.get(SupportTicket.class, ticketFromUser.getId());
//
//        if (ticketFromDB != null) {
//            ticketFromDB.setName(ticketFromUser.getName());
//            ticketFromDB.setEmail(ticketFromUser.getEmail());
//            ticketFromDB.setSubject(ticketFromUser.getSubject());
//            ticketFromDB.setDescription(ticketFromUser.getDescription());
//            ticketFromDB.setStatus(ticketFromUser.getStatus());
//
//            session.update(ticketFromDB);
//            session.flush();
//
//            return new Message(MessageType.UPDATE_SUPPORT_TICKET_RESPONSE)
//                    .setSuccess(true)
//                    .setMessage("Support ticket updated successfully")
//                    .setDataObject(ticketFromDB);
//        } else {
//            return new Message(MessageType.UPDATE_SUPPORT_TICKET_RESPONSE)
//                    .setSuccess(false)
//                    .setMessage("Support ticket not found");
//        }
//    }

    // Delete a support ticket
    public Message deleteSupportTicket(Message request) {
        SupportTicket ticketFromUser = (SupportTicket) request.getDataObject();
        SupportTicket ticketFromDB = session.get(SupportTicket.class, ticketFromUser.getId());

        if (ticketFromDB != null) {
            session.delete(ticketFromDB);
            session.flush();

            return new Message(MessageType.DELETE_SUPPORT_TICKET_RESPONSE)
                    .setSuccess(true)
                    .setMessage("Support ticket deleted successfully");
        } else {
            return new Message(MessageType.DELETE_SUPPORT_TICKET_RESPONSE)
                    .setSuccess(false)
                    .setMessage("Support ticket not found");
        }
    }
}
