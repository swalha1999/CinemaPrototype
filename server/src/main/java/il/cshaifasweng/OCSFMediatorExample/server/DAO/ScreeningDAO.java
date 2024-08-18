package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Screening;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ScreeningDAO {

    private Session session;

    public ScreeningDAO(Session session) {
        this.session = session;
    }


    public Message getAllScreenings(Message request) {
        Message response = new Message(MessageType.GET_ALL_SCREENINGS_RESPONSE);

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Screening> query = builder.createQuery(Screening.class);
        query.from(Screening.class);
        List<Screening> allScreening = session.createQuery(query).getResultList();

        return response.setSuccess(true)
                .setMessage("All screenings fetched successfully")
                .setDataObject(allScreening);
    }

    public Message getAllScreeningsForMovie(Message request) {
        Message response = new Message(MessageType.GET_SCREENING_FOR_MOVIE_RESPONSE);
        Movie movie = (Movie) request.getDataObject();

        Query<Screening> query = session.createQuery("from Screening where movie = :movie", Screening.class);
        query.setParameter("movie", movie);
        List<Screening> allScreening = query.getResultList();

        return response.setSuccess(true)
                .setMessage("All screenings fetched successfully")
                .setDataObject(allScreening);
    }


    public void setSession(Session session) {
        this.session = session;
    }
}
