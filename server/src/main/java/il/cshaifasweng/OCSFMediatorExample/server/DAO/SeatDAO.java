package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.*;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class SeatDAO {

    private Session session;

    public SeatDAO(Session session) {
        this.session = session;
    }

    public Message getSeatsForScreening(Message request) {
        Message response = new Message(MessageType.GET_SEATS_FOR_SCREENING_RESPONSE);
        Screening screening = (Screening) request.getDataObject();

        Query<Seat> query = session.createQuery("from Seat where screening = :screening", Seat.class);
        query.setParameter("screening", screening);
        List<Seat> allSeats = query.getResultList();

        return response.setSuccess(true)
                .setMessage("All seats fetched successfully")
                .setDataObject(allSeats);
    }

    public void setSession(Session session) {
        this.session = session;
    }
}