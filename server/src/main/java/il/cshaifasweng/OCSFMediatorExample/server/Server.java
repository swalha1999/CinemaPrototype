package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;
import org.hibernate.Session;

import il.cshaifasweng.OCSFMediatorExample.entities.Movie;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server extends AbstractServer {
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();
	private static Session session;

	public Server(int port, Session session) {
		super(port);
		this.session = session;
	}

	@Override
	protected void handleMessageFromClient(Object req, ConnectionToClient client) {
		Message reqMessage = (Message) req;
		Message response = new Message(reqMessage.getId(), reqMessage.getMessage());
		System.out.println(reqMessage.getId() + ": " + reqMessage.getMessage());
		for (SubscribedClient subscribedClient : SubscribersList) {
			System.out.println("there is server here: " + subscribedClient);
		}
		sendToAllClients(response);

	}

	public void sendToAllClients(Message message) {
		try {
			for (SubscribedClient SubscribedClient : SubscribersList) {
				SubscribedClient.getClient().sendToClient(message);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public List<Movie> getMovies() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Movie> query = builder.createQuery(Movie.class);
		query.from(Movie.class);
		return session.createQuery(query).getResultList();
	}

}
