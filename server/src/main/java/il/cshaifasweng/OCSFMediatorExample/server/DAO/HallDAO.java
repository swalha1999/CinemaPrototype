package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Hall;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import org.hibernate.Session;

import java.util.List;

public class HallDAO
{
    private Session session;

    public HallDAO(Session session) {
        this.session = session;
    }

    public HallDAO setSession(Session session) {
        this.session = session;
        return this;
    }

    public Session getSession() {
        return session;
    }

    public Message getAllHalls(Message request) {
        Message message = new Message(MessageType.GET_ALL_HALLS_RESPONSE);
        List<Hall> halls = session.createQuery("from Hall", Hall.class).list();

        return message.setSuccess(true)
                .setMessage("All halls fetched successfully")
                .setDataObject(halls);
    }

    public Message getHall(Message request) {
        Message message = new Message(MessageType.GET_HALL_RESPONSE);
        Hall hall = (Hall) request.getDataObject();
        Hall hallFromDB = session.get(Hall.class, hall.getId());
        return message.setSuccess(true)
                .setMessage("Hall fetched successfully")
                .setDataObject(hallFromDB);
    }

    public Message addHall(Message request) {
        Hall hallFromUser = (Hall) request.getDataObject();
        Cinema cinema = session.get(Cinema.class, hallFromUser.getCinema().getId());
        if (cinema == null) {
            return new Message(MessageType.ADD_HALL_RESPONSE)
                    .setSuccess(false)
                    .setMessage("Cinema not found")
                    .setDataObject(null);
        }
        Hall hall = new Hall();
        hall.setCinema(cinema);
        hall.setName(hallFromUser.getName());
        hall.setSeatsNum(100); //default value

        session.save(hall);
        session.flush();
        return new Message(MessageType.ADD_HALL_RESPONSE)
                .setSuccess(true)
                .setMessage("Hall added successfully")
                .setDataObject(hall);
    }
}
