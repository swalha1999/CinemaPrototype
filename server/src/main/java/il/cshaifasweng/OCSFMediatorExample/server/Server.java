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
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.println("Message received: " + request.toString());

        if (request.getVersion() == MessageVersion.V1) {
            handleV1Message(request, client);
        } else if (request.getVersion() == MessageVersion.V2) {
			handleV2Message(request, client);
        } else {
            sendErrorMessage(client, "Error! Unknown message version");
        }
    }

    private void handleV1Message(Message request, ConnectionToClient client) {
        Message response = new Message(200, request.getMessage());
        switch (request.getMessage()) {
            case "add client":
                handleAddClient(client, response);
                break;
            case "echo all":
                sendToAllClients(response);
                break;
            case "get all movies":
                handleGetAllMovies(client, response);
                break;
            case "add movies":
                handleAddMovies(request, client, response);
                break;
            case "update movies":
                handleUpdateMovies(request, client, response);
                break;
            case "delete movies":
                handleDeleteMovies(request, client, response);
                break;
            case "register a new user":
                handleRegisterUser(request, client, response);
                break;
            case "login":
                handleLogin(request, client);
                break;
            default:
                sendErrorMessage(client, "Error! Unknown message received");
                break;
        }
    }

    private void handleV2Message(Message request, ConnectionToClient client) {
        Message response;
        switch (request.getType()) {
            case LOGIN_REQUEST:
                handleLoginRequest(request, client);
                break;
            case LOGOUT_REQUEST:
                handleLogoutRequest(request, client);
                break;
            case REGISTER_REQUEST:
                handleRegisterRequest(request, client);
                break;

			//TODO: add more cases for the other message types

            default:
                sendErrorMessage(client, "Error! Unknown message received");
                break;
        }
    }

    private void handleAddClient(ConnectionToClient client, Message response) {
        SubscribedClient connection = new SubscribedClient(client);
        openClients.add(connection);
        response.setMessage("client added successfully");
        sendResponse(client, response);
    }

    private void handleGetAllMovies(ConnectionToClient client, Message response) {
        response.setMovies(database.getMoviesManger().getMovies());
        sendResponse(client, response);
    }

    private void handleAddMovies(Message request, ConnectionToClient client, Message response) {
        if (request.getMovies().isEmpty()) {
            sendErrorMessage(client, "The list of movies to Add is Empty");
            return;
        }
        for (Movie movie : request.getMovies()) {
            response.addMovie(database.getMoviesManger().addMovie(movie));
            System.out.println("Movie added successfully" + movie.getName());
        }
        sendToAllClients(response);
    }

    private void handleUpdateMovies(Message request, ConnectionToClient client, Message response) {
        if (request.getMovies().isEmpty()) {
            sendErrorMessage(client, "The list of movies to Edit is Empty");
            return;
        }
        for (Movie movie : request.getMovies()) {
            response.addMovie(database.getMoviesManger().editMovie(movie));
            System.out.println("Movie updated successfully" + movie.getName());
        }
        sendToAllClients(response);
    }

    private void handleDeleteMovies(Message request, ConnectionToClient client, Message response) {
        if (request.getMovies().isEmpty()) {
            sendErrorMessage(client, "The list of movies to delete is Empty");
            return;
        }
        for (Movie movie : request.getMovies()) {
            response.addMovie(database.getMoviesManger().deleteMovie(movie));
            System.out.println("Movie deleted successfully" + movie.getName());
        }
        sendToAllClients(response);
    }

	// TODO: remove safely
    private void handleRegisterUser(Message request, ConnectionToClient client, Message response) {
        if (request.getData().isEmpty()) {
            sendErrorMessage(client, "The data to register a new user is Empty");
            return;
        }
        User user = new User();
        user.setUsername(request.getUser().getUsername());
        user.setFirstName(request.getUser().getFirstName());
        user.setLastName(request.getUser().getLastName());
        user.setEmail(request.getUser().getEmail());
        user.setSalt(UserDAO.generateSalt());
        user.setHashedPassword(UserDAO.hashPassword(request.getData(), user.getSalt()));
        database.getUsersManager().addUser(user);
        sendResponse(client, response);
    }

	//TODO: remove safely
    private void handleLogin(Message request, ConnectionToClient client) {
        if (request.getData().isEmpty()) {
            sendErrorMessage(client, "The data to login is Empty");
            return;
        }
        User userToLogin = database.getUsersManager().getUserbyUsername(request.getUser().getUsername());
        if (userToLogin != null && userToLogin.getHashedPassword().equals(UserDAO.hashPassword(request.getData(), userToLogin.getSalt()))) {
            sendResponse(client, new Message(200, "Login successful"));
        } else {
            sendErrorMessage(client, "Username or password is incorrect");
        }
    }

    private void handleLoginRequest(Message request, ConnectionToClient client) {
        LoginRequest loginRequest = (LoginRequest) request.getDataObject();
        System.out.println("Login request received:" + loginRequest.toString()); //TODO: remove this line debug only
        LoginResponse loginResponse = database.getUsersManager().loginUser(loginRequest);
        if (loginResponse.isSuccess()) {
            LoggedInUser loggedInUser = new LoggedInUser(
                    loginResponse.getSessionKey(),
                    loginResponse.getUsername(),
                    loginResponse.getRole(),
                    client
            );
            sessionKeys.put(loginResponse.getSessionKey(), loggedInUser);
        }
		System.out.println("Login response: " + loginResponse.toString()); //TODO: remove this line debug only
        sendResponse(client, new Message(loginResponse, MessageType.LOGIN_RESPONSE));
    }

    private void handleLogoutRequest(Message request, ConnectionToClient client) {
        LogoutRequest logoutRequest = (LogoutRequest) request.getDataObject();
        System.out.println("Logout request received:" + logoutRequest.toString()); //TODO: remove this line debug only
        LogoutResponse logoutResponse = database.getUsersManager().logoutUser(logoutRequest);
        sessionKeys.remove(logoutRequest.getSessionKey());
        sendResponse(client, new Message(logoutResponse, MessageType.LOGOUT_RESPONSE));
    }

    private void handleRegisterRequest(Message request, ConnectionToClient client) {
        RegisterRequest registerRequest = (RegisterRequest) request.getDataObject();
        System.out.println("Register request received:" + registerRequest.toString()); //TODO: remove this line debug only
        RegisterResponse registerResponse = database.getUsersManager().registerUser(registerRequest);
        sendResponse(client, new Message(registerResponse, MessageType.REGISTER_RESPONSE));
    }

    private void sendResponse(ConnectionToClient client, Message response) {
        try {
            client.sendToClient(response);
        } catch (IOException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }

    private void sendErrorMessage(ConnectionToClient client, String errorMessage) {
        try {
            client.sendToClient(new Message(500, errorMessage));
        } catch (IOException e) {
            System.out.println("Error sending error message: " + e.getMessage());
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

				// if the user fail to respond we will remove him from the openClients  users list
				openClients.remove(SubscribedClient);
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