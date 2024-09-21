package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.*;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import il.cshaifasweng.OCSFMediatorExample.server.dataTypes.LoggedInUser;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
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

    public Message getScreeningForHall(Message request) {
        Message response = new Message(MessageType.GET_SCREENING_FOR_HALL_RESPONSE);
        Hall hall = (Hall) request.getDataObject();

        Query<Screening> query = session.createQuery("from Screening where hall = :hall", Screening.class);
        query.setParameter("hall", hall);
        List<Screening> allScreening = query.getResultList();

        return response.setSuccess(true)
                .setMessage("All screenings fetched successfully")
                .setDataObject(allScreening);
    }

    public Message addScreening(Message request) {
        Message response = new Message(MessageType.ADD_SCREENING_RESPONSE);
        Screening screeningToAdd = (Screening) request.getDataObject();

        Hall hall = session.get(Hall.class, screeningToAdd.getHall().getId());
        if (hall == null) {
            return response.setSuccess(false)
                    .setMessage("Hall not found")
                    .setDataObject(null);
        }

        Cinema cinema = session.get(Cinema.class, hall.getCinema().getId());
        if (cinema == null) {
            return response.setSuccess(false)
                    .setMessage("Cinema not found")
                    .setDataObject(null);
        }



        Screening screening = new Screening();
        screening.setMovie(screeningToAdd.getMovie());
        screening.setPrice(screeningToAdd.getPrice());
        screening.setStartingAt(screeningToAdd.getStartingAt());
        screening.setHall(hall);
        screening.setCinema(cinema);

        session.beginTransaction();  // Start transaction

        for (int i = 0; i < 100; i++) {
            Seat seat = new Seat();
            seat.setSeatLocationX(i % 10);
            seat.setSeatLocationY(i / 10);
            seat.setAvailable(true);
            seat.setScreening(screening);
            screening.addSeat(seat);
            session.save(seat);
        }

        session.save(screening);     // Save the screening object
        session.flush();             // Flush session
        session.getTransaction().commit(); // Commit transaction
        return response.setSuccess(true)
                .setMessage("Screening added successfully")
                .setDataObject(screening);
    }

    public Message removeScreening(Message request) {
        Message response = new Message(MessageType.REMOVE_SCREENING_RESPONSE);
        Screening screening = session.get(Screening.class, ((Screening) request.getDataObject()).getId());

        if (screening == null) {
            return response.setSuccess(false)
                    .setMessage("Screening not found")
                    .setDataObject(null);
        }

        session.beginTransaction();
        session.delete(screening);
        session.flush();
        session.getTransaction().commit();

        return response.setSuccess(true)
                .setMessage("Screening removed successfully")
                .setDataObject(screening);
    }

    public Message updateScreening(Message request) {
        Message response = new Message(MessageType.UPDATE_SCREENING_RESPONSE);
        Screening screeningFromUser = (Screening) request.getDataObject();

        Screening screening = session.get(Screening.class, screeningFromUser.getId());

        if (screening == null) {
            return response.setSuccess(false)
                    .setMessage("Screening not found")
                    .setDataObject(null);
        }

        screening.setMovie(screeningFromUser.getMovie() == null ? screening.getMovie() : screeningFromUser.getMovie());
        screening.setHall(screeningFromUser.getHall() == null ? screening.getHall() : screeningFromUser.getHall());
        screening.setPrice(screeningFromUser.getPrice() == 0 ? screening.getPrice() : screeningFromUser.getPrice());
        screening.setStartingAt(screeningFromUser.getStartingAt() == null ? screening.getStartingAt() : screeningFromUser.getStartingAt());

        session.beginTransaction();
        session.update(screening);
        session.flush();
        session.getTransaction().commit();

        return response.setSuccess(true)
                .setMessage("Screening updated successfully")
                .setDataObject(screening);
    }

    public Screening getScreeningById(int id) {
        Transaction transaction = null;
        Screening screening = null;
        try {
            transaction = session.beginTransaction();
            screening = session.get(Screening.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return screening;
    }

    public Message getMyScreenings(Message request, LoggedInUser loggedInUser) {
        try {
            // Ensure we are fetching screenings linked to the user's movie tickets
            List<Screening> screenings = session.createQuery(
                            "select t.screening from MovieTicket t where t.user.id = :userId", Screening.class)
                    .setParameter("userId", loggedInUser.getUserId())  // Use the logged-in user's ID
                    .getResultList();

            return new Message(MessageType.GET_MY_SCREENINGS_RESPONSE).setDataObject(screenings);

        } catch (Exception e) {
            e.printStackTrace();
            return new Message(MessageType.ERROR);
        }
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
