package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.server.DAO.DatabaseController;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;
import org.hibernate.Session;

import il.cshaifasweng.OCSFMediatorExample.entities.Movie;

import java.io.IOException;
import java.util.ArrayList;

public class Server extends AbstractServer {
	private static final ArrayList<SubscribedClient> openClients = new ArrayList<>();
	private final DatabaseController database;

	public Server(int port, Session session) {
		super(port);
		this.database = new DatabaseController(session);
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
				Message response = new Message(200,request.getMessage());
				switch (request.getMessage()) {

					case "add client":
						SubscribedClient connection = new SubscribedClient(client);
						openClients.add(connection);
						response.setMessage("client added successfully");
						client.sendToClient(response);
						break;

					case "echo all":
						sendToAllClients(response);
						break;

					case "get all movies":
						response.setMovies(database.getMoviesManger().getMovies());
						client.sendToClient(response);
						break;

					case "add movies":
						if (request.getMovies().isEmpty()){
							System.out.println("The list of movies to Add is Empty");
							break;
						}
						for (Movie movie : request.getMovies()) {
							response.addMovie(database.getMoviesManger().addMovie(movie));
							System.out.println("Movie added successfully" + movie.getName());
						}
						sendToAllClients(response);
						break;


					case "update movies":
						if (request.getMovies().isEmpty()){
							System.out.println("The list of movies to Edit is Empty");
							break;
						}
						for (Movie movie : request.getMovies()) {
							response.addMovie(database.getMoviesManger().editMovie(movie));
							System.out.println("Movie updated successfully" + movie.getName());
						}
						sendToAllClients(response);
						break;

					case "delete movies":
						if (request.getMovies().isEmpty()){
							System.out.println("The list of movies to delete is Empty");
							break;
						}
						for (Movie movie : request.getMovies()) {
							response.addMovie(database.getMoviesManger().deleteMovie(movie));
							System.out.println("Movie deleted successfully" + movie.getName());
						}
						sendToAllClients(response);
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
				System.out.println("Sent to client: " + SubscribedClient.getClient().getName() + " the message: " + message.getMessage());
			} catch (IOException e1) {
				System.out.println("Error sending message to this client: " + e1.getMessage());
				//TODO:  remove the clients that fail to respond
			}
		}
	}

}
