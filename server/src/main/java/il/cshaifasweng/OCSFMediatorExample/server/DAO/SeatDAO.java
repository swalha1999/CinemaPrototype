package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.*;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
        Screening screeningFromUser = (Screening) request.getDataObject();

        Screening screening = DatabaseController.getInstance(session).getScreeningsManager().getScreeningById(screeningFromUser.getId());
        if (screening == null) {
            return response.setSuccess(false)
                    .setMessage("Screening not found");
        }

        session.beginTransaction();
        Query<Seat> query = session.createQuery("from Seat where screening = :screening", Seat.class);
        query.setParameter("screening", screening);
        List<Seat> seats = query.getResultList();
        session.getTransaction().commit();

        for (Seat seat : seats) {
            System.out.println(seat.getId());
        }

        return response.setSuccess(true)
                .setMessage("All seats fetched successfully")
                .setDataObject(seats);
    }

    public Seat getSeatById(int id) {
        // make a query to get the seat by id
        Transaction transaction = null;
        Seat seat = null;
        try {
            transaction = session.beginTransaction();
            seat = session.get(Seat.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return seat;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
