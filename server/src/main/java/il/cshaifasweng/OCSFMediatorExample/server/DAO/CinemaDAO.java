package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Hall;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

public class CinemaDAO {

    private Session session;

    public CinemaDAO(Session session) {
        this.session = session;
    }

    public CinemaDAO setSession(Session session) {
        this.session = session;
        return this;
    }

    public Session getSession() {
        return session;
    }

    public Message getAllCinemas(Message request) {
        Message message = new Message(MessageType.GET_ALL_CINEMAS_RESPONSE);
        List<Cinema> cinemas = session.createQuery("from Cinema", Cinema.class).list();

        //add the user manager to the cinema
        for (Cinema cinema : cinemas) {
            cinema.setManager(session.get(Cinema.class, cinema.getId()).getManager());
        }

        return message.setSuccess(true)
                .setMessage("All cinemas fetched successfully")
                .setDataObject(cinemas);
    }

    public Message getCinemaHalls(Message request) {
        Message message = new Message(MessageType.GET_CINEMA_HALLS_RESPONSE);
        Cinema cinemaForUser = (Cinema) request.getDataObject();
        Cinema cinema = session.get(Cinema.class, cinemaForUser.getId());
        List<Hall> halls= cinema.getHalls().stream().toList();
        return message.setSuccess(true)
                .setMessage("All halls fetched successfully")
                .setDataObject(halls);
    }

    public Message addCinema(Message request) {
        //TODO: There is fucking bug here
        Cinema cinemaFromUser = (Cinema) request.getDataObject();

        // Get the user manager from the database by username
        User manager = DatabaseController.getInstance(session).getUsersManager().getUserbyUsername(cinemaFromUser.getManager().getUsername());

        if (manager == null) {
            return new Message(MessageType.ADD_CINEMA_RESPONSE)
                    .setSuccess(false)
                    .setMessage("Manager not found")
                    .setDataObject(null);
        }

        Cinema cinema = new Cinema(cinemaFromUser.getName(), cinemaFromUser.getCity(), cinemaFromUser.getAddress(), cinemaFromUser.getPhoneNumber(), cinemaFromUser.getEmail());
        cinema.setManager(manager);
        cinema.setName("yes planet");
        cinema.setAddress("iksal");
        cinema.setEmail("email");
        cinema.setPhoneNumber("902109841091");

        session.save(cinema);
        session.flush();
        return new Message(MessageType.ADD_CINEMA_RESPONSE)
                .setSuccess(true)
                .setMessage("Cinema added successfully")
                .setDataObject(cinema);
    }

    public Message removeCinema(Message request) {
        Cinema cinemaToDelete = (Cinema) request.getDataObject();

        Cinema cinema = session.get(Cinema.class, cinemaToDelete.getId());
        if (cinema == null) {
            return new Message(MessageType.REMOVE_CINEMA_RESPONSE)
                    .setSuccess(false)
                    .setMessage("Cinema not found")
                    .setDataObject(null);
        }

        // get all the halls of the cinema
        Set<Hall> halls = cinema.getHalls();
        for (Hall hall : halls) {
            session.delete(hall);
            session.flush();
        }

        session.delete(cinema);
        session.flush();
        return new Message(MessageType.REMOVE_CINEMA_RESPONSE)
                .setSuccess(true)
                .setMessage("Cinema removed successfully")
                .setDataObject(cinema);

    }

    public Message updateCinema(Message request) {
        Cinema cinemaFromUser = (Cinema) request.getDataObject();

        Cinema cinema = session.get(Cinema.class, cinemaFromUser.getId());
        cinema.setName(cinemaFromUser.getName() == null ? cinema.getName() : cinemaFromUser.getName());
        cinema.setCity(cinemaFromUser.getCity() == null ? cinema.getCity() : cinemaFromUser.getCity());
        cinema.setAddress(cinemaFromUser.getAddress() == null ? cinema.getAddress() : cinemaFromUser.getAddress());
        cinema.setPhoneNumber(cinemaFromUser.getPhoneNumber() == null ? cinema.getPhoneNumber() : cinemaFromUser.getPhoneNumber());
        cinema.setEmail(cinemaFromUser.getEmail() == null ? cinema.getEmail() : cinemaFromUser.getEmail());

        session.update(cinema);
        session.flush();
        return new Message(MessageType.UPDATE_CINEMA_RESPONSE)
                .setSuccess(true)
                .setMessage("Cinema updated successfully")
                .setDataObject(cinema);
    }



}
