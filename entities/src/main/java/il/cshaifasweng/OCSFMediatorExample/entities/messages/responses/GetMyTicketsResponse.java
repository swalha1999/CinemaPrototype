package il.cshaifasweng.OCSFMediatorExample.entities.messages.responses;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.MovieTicket;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Response;

import java.util.ArrayList;
import java.util.List;

public class GetMyTicketsResponse implements Response {

    private static final long serialVersionUID = -5386248350340650197L;

    private boolean success;
    private String message;
    private List<MovieTicket> tickets = new ArrayList<>();

    public GetMyTicketsResponse() {
    }

    public GetMyTicketsResponse(boolean success, String message, List<MovieTicket> tickets) {
        this.success = success;
        this.message = message;
        this.tickets = tickets;
    }

    public GetMyTicketsResponse(List<MovieTicket> tickets) {
        this.success = true;
        this.message = "Tickets retrieved successfully";
        this.tickets = tickets;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getTickets() {
        return tickets;
    }

    public GetMyTicketsResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public GetMyTicketsResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public GetMyTicketsResponse setTickets(List<MovieTicket> tickets) {
        this.tickets = tickets;
        return this;
    }

    public GetMyTicketsResponse addTicket(MovieTicket ticket) {
        this.tickets.add(ticket);
        return this;
    }

    public String toString() {
        return "GetMyTicketsResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", tickets=" + tickets +
                '}';
    }
}
