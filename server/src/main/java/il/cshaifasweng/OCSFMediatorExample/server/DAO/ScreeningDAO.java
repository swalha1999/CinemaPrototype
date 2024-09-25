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

    public Message addPriceChangeRequest( Message req, LoggedInUser loggedInUser) {
        Message response = new Message(MessageType.ADD_PRICE_CHANGE_RESPONSE);
        PriceChangeRequest priceChangeRequestFromUser = (PriceChangeRequest) req.getDataObject();

        User contentManager = session.get(User.class, loggedInUser.getUserId());

        if (priceChangeRequestFromUser.getScreening() == null) {
            return response.setSuccess(false)
                    .setMessage("Screening not found");
        }

        if (priceChangeRequestFromUser.getNewPrice() == 0) {
            return response.setSuccess(false)
                    .setMessage("New price not found");
        }

        if (contentManager == null) {
            return response.setSuccess(false)
                    .setMessage("Content Manager not found");
        }

        if (contentManager.getRole() != UserRole.CONTENT_MANAGER) {
            return response.setSuccess(false)
                    .setMessage("User is not a content manager");
        }

        //get screening from the database
        Screening screening = session.get(Screening.class, priceChangeRequestFromUser.getScreening().getId());
        if (screening == null) {
            return response.setSuccess(false)
                    .setMessage("Screening not found");
        }

        // get the movie from the screening
        Movie movie = session.get(Movie.class, screening.getMovie().getId());

        PriceChangeRequest priceChangeRequest = new PriceChangeRequest();
        priceChangeRequest.setMovie(movie);
        priceChangeRequest.setScreening(screening);
        priceChangeRequest.setNewPrice(priceChangeRequestFromUser.getNewPrice());
        priceChangeRequest.setContentManager(contentManager);

        session.beginTransaction();
        session.save(priceChangeRequest);
        session.getTransaction().commit();

        return response.setSuccess(true)
                .setMessage("Price change request added successfully")
                .setDataObject(priceChangeRequest);
    }

    public Message getAllPriceChangesRequest(Message req, LoggedInUser loggedInUser) {
        Message response = new Message(MessageType.GET_PRICE_CHANGES_RESPONSE);

        if(loggedInUser.getRole() != UserRole.MANAGER_OF_ALL_BRANCHES || loggedInUser.getRole() != UserRole.SYSTEM_MANAGER){
            return response.setSuccess(false)
                    .setMessage("User is not a manager");
        }

        //get all price change requests
        List<PriceChangeRequest> priceChangeRequests = session.createQuery("from PriceChangeRequest", PriceChangeRequest.class).getResultList();

        return response.setSuccess(true)
                .setMessage("Price change request added successfully")
                .setDataObject(priceChangeRequests);
    }

    public Message approvePriceChangeRequest( Message req, LoggedInUser loggedInUser) {
        Message response = new Message(MessageType.APPROVE_PRICE_CHANGE_RESPONSE);
        PriceChangeRequest priceChangeRequestFromUser = (PriceChangeRequest) req.getDataObject();

        PriceChangeRequest priceChangeRequest = session.get(PriceChangeRequest.class, priceChangeRequestFromUser.getId());

        if (priceChangeRequest == null) {
            return response.setSuccess(false)
                    .setMessage("Price change request not found");
        }

        if(loggedInUser.getRole() != UserRole.MANAGER_OF_ALL_BRANCHES || loggedInUser.getRole() != UserRole.SYSTEM_MANAGER){
            return response.setSuccess(false)
                    .setMessage("User is not a manager");
        }

        priceChangeRequest.getScreening().setPrice(priceChangeRequest.getNewPrice());

        session.beginTransaction();
        session.update(priceChangeRequest.getScreening());
        session.delete(priceChangeRequest);
        session.getTransaction().commit();

        return response.setSuccess(true)
                .setMessage("Price change request approved successfully")
                .setDataObject(priceChangeRequest);
    }

    public Message denyPriceChangeRequest( Message req, LoggedInUser loggedInUser) {
        Message response = new Message(MessageType.APPROVE_PRICE_CHANGE_RESPONSE);
        PriceChangeRequest priceChangeRequestFromUser = (PriceChangeRequest) req.getDataObject();

        PriceChangeRequest priceChangeRequest = session.get(PriceChangeRequest.class, priceChangeRequestFromUser.getId());

        if (priceChangeRequest == null) {
            return response.setSuccess(false)
                    .setMessage("Price change request not found");
        }

        if(loggedInUser.getRole() != UserRole.MANAGER_OF_ALL_BRANCHES || loggedInUser.getRole() != UserRole.SYSTEM_MANAGER){
            return response.setSuccess(false)
                    .setMessage("User is not a manager");
        }

        session.beginTransaction();
        session.delete(priceChangeRequest);
        session.getTransaction().commit();

        return response.setSuccess(true)
                .setMessage("Price change request denied successfully")
                .setDataObject(priceChangeRequest);
    }



    public void setSession(Session session) {
        this.session = session;
    }


}
