package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.SupportTicketStatus;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import org.hibernate.Session;

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
    public Message addSupportTicket(Message request) {
        SupportTicket ticketFromUser = (SupportTicket) request.getDataObject();
        SupportTicket ticket = new SupportTicket();
        ticket.setName(ticketFromUser.getName());
        ticket.setEmail(ticketFromUser.getEmail());
        ticket.setSubject(ticketFromUser.getSubject());
        ticket.setDescription(ticketFromUser.getDescription());
        ticket.setStatus(SupportTicketStatus.OPEN);  // default status

        session.save(ticket);
        session.flush();

        return new Message(MessageType.ADD_SUPPORT_TICKET_RESPONSE)
                .setSuccess(true)
                .setMessage("Support ticket added successfully")
                .setDataObject(ticket);
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
