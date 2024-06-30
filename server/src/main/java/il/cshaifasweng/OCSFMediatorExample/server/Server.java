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
import java.util.ArrayList;
import java.util.List;

public class Server extends AbstractServer {
	private static final ArrayList<SubscribedClient> openClients = new ArrayList<>();
	private final Session session;

	public Server(int port, Session session) {
		super(port);
		this.session = session;
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Message request = (Message) msg;
		System.out.println(request.getId() + ": " + request.getMessage());

		try {
			if (request.getMessage().isBlank()){
				client.sendToClient(new Message(404,"Error! we got an empty message"));
			}
			else {
				switch (request.getMessage()) {
					case "add client":
						SubscribedClient connection = new SubscribedClient(client);
						openClients.add(connection);
						client.sendToClient(new Message(200,"client added successfully"));
						break;

					case "echo all":
						sendToAllClients(new Message(200, request.getMessage()));
						break;

					case "update all movies":
						sendToAllClients(new Message(200, request.getMessage(), getMovies()));
						break;

					//TODO: Add more cases as needed Here

					default:
						client.sendToClient(new Message(500,"Error! Unknown message received"));
						break;
				}
			}
		}
		catch (IOException e) {
			System.out.println("Error sending message: " + e.getMessage());
		}
	}

	public void sendToAllClients(Message message) {
		// here we send to all the clients that registered to our server
		for (SubscribedClient SubscribedClient : openClients) {
			try {
				SubscribedClient.getClient().sendToClient(message);
			} catch (IOException e1) {
				System.out.println("Error sending message to this client: " + e1.getMessage());
				//TODO:  remove the clients that fail to respond
			}
		}

	}

	public List<Movie> getMovies() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Movie> query = builder.createQuery(Movie.class);
		query.from(Movie.class);
		return session.createQuery(query).getResultList();
	}

}
