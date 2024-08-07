package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.*;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import il.cshaifasweng.OCSFMediatorExample.server.DAO.DatabaseController;
import il.cshaifasweng.OCSFMediatorExample.server.DAO.UserDAO;
import il.cshaifasweng.OCSFMediatorExample.server.dataTypes.LoggedInUser;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;
import org.hibernate.Session;

import il.cshaifasweng.OCSFMediatorExample.entities.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Server extends AbstractServer {
	private static final ArrayList<SubscribedClient> openClients = new ArrayList<>(); //TODO: deprecated do not needed
	private static final HashMap<String, LoggedInUser> sessionKeys = new HashMap<>(); //this will be used to store the session keys for the logged-in users
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
				if (request.getVersion()  == MessageVersion.V1){
					Message response = new Message(200,request.getMessage());
					switch (request.getMessage()) {

						case "add client": // TODO: remove safely
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

						case "register a new user":
							if (request.getData().isEmpty()){
								System.out.println("The data to register a new user is Empty");
								break;
							}
							User user = new User();
							user.setUsername(request.getUser().getUsername());
							user.setFirstName(request.getUser().getFirstName());
							user.setLastName(request.getUser().getLastName());
							user.setEmail(request.getUser().getEmail());
							user.setSalt(UserDAO.generateSalt());
							user.setHashedPassword(UserDAO.hashPassword(request.getData(),user.getSalt()));
							database.getUsersManager().addUser(user);
							client.sendToClient(response);
							break;
						case "login":
							if (request.getData().isEmpty()){
								System.out.println("The data to login is Empty");
								break;
							}
							User userToLogin = database.getUsersManager().getUserbyUsername(request.getUser().getUsername());
							if (userToLogin != null && userToLogin.getHashedPassword().equals(UserDAO.hashPassword(request.getData(),userToLogin.getSalt()))){
								client.sendToClient(new Message(200,"Login successful"));
							}
							else {
								client.sendToClient(new Message(404,"Username or password is incorrect"));
							}
							break;

						//TODO: deprecated do not add any new messages here

						default:
							client.sendToClient(new Message(500,"Error! Unknown message received"));
							break;
					}
				}
				//TODO: migrate all the messages to the new version
				else if (request.getVersion() == MessageVersion.V2){
					Message response;
					switch (request.getType()){
						case LOGIN_REQUEST:
							LoginRequest loginRequest = (LoginRequest) request.getDataObject();
							LoginResponse loginResponse = database.getUsersManager().loginUser(loginRequest);
							if (loginResponse.isSuccess()){
								LoggedInUser loggedInUser = new LoggedInUser(
										loginResponse.getSessionKey(),
										loginResponse.getUsername(),
										loginResponse.getRole(),
										client
								);
								sessionKeys.put(loginResponse.getSessionKey(),loggedInUser);
							}
							response = new Message(MessageType.LOGIN_RESPONSE);
							response.setDataObject(loginResponse);
							client.sendToClient(response);
							break;

						case LOGOUT_REQUEST:
							LogoutRequest logoutRequest = (LogoutRequest) request.getDataObject();
							LogoutResponse logoutResponse = database.getUsersManager().logoutUser(logoutRequest);
							sessionKeys.remove(logoutRequest.getSessionKey());
							response = new Message(MessageType.LOGOUT_RESPONSE);
							response.setDataObject(logoutResponse);
							client.sendToClient(response);
							break;

						case REGISTER_REQUEST:
							RegisterRequest registerRequest = (RegisterRequest) request.getDataObject();
							RegisterResponse registerResponse = database.getUsersManager().registerUser(registerRequest);
							response = new Message(MessageType.REGISTER_RESPONSE);
							response.setDataObject(registerResponse);
							client.sendToClient(response);
							break;

						default:
							client.sendToClient(new Message(500,"Error! Unknown message received"));
							break;
					}
				}
				else{
					client.sendToClient(new Message(500,"Error! Unknown message version"));
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

	public void sendToAllLoggedInUsers(Message message) {
		// here we send to all the clients that registered to our server
		for (LoggedInUser loggedInUser : sessionKeys.values()) {
			try {
				loggedInUser.getClient().sendToClient(message);
				System.out.println("Sent to client: " + loggedInUser.getClient().getName() + " the message: " + message.getMessage());
			} catch (IOException e1) {
				System.out.println("Error sending message to this client: " + e1.getMessage());

				// if the user fail to respond we will remove him from the logged-in users list
				sessionKeys.remove(loggedInUser.getSessionKey());
			}
		}
	}

}
