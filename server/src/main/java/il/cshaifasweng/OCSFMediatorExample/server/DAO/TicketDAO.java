package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.GetAllUsersRequset;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.GetAllUsersResponse;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.GetMyTicketsRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.GetMyTicketsResponse;
import org.hibernate.Session;

import java.util.List;


public class TicketDAO {
    private final Session session;

    public TicketDAO(Session session) {
        this.session = session;
    }

    public GetMyTicketsResponse getMyTickets(GetMyTicketsRequest request) {
        List<MovieTicket> tickets = session.createQuery("from MovieTicket where user = :id", MovieTicket.class)
                .setParameter("id", request.getUserId())
                .getResultList();
        return new GetMyTicketsResponse(tickets);
    }

}
