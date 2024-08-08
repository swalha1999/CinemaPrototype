package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.UserRole;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.*;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs.NewUserAddedPatch;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.*;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.*;
import il.cshaifasweng.OCSFMediatorExample.server.DAO.DatabaseController;
import il.cshaifasweng.OCSFMediatorExample.server.DAO.UserDAO;
import il.cshaifasweng.OCSFMediatorExample.server.dataTypes.LoggedInUser;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;
import org.hibernate.Session;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.Movie;

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
            case GET_ALL_USERS_REQUEST:
                handleGetAllUsersRequest(request, client);
                break;
            case GET_MY_TICKETS_REQUEST:
                handleGetMyTicketsRequest(request, client);
                break;
            case BLOCK_USER_REQUEST:
                handleBlockUserRequest(request, client);
                break;
            case UNBLOCK_USER_REQUEST:
                handleUnblockUserRequest(request, client);
                break;
            case REMOVE_USER_REQUEST:
                handleRemoveUserRequest(request, client);
                break;
                //TODO: add more cases here

            default:
                sendErrorMessage(client, "Error! Unknown message received");
                break;
        }
    }

    //TODO: DEPRECATED REMOVE SAFELY
    private void handleAddClient(ConnectionToClient client, Message response) {
        SubscribedClient connection = new SubscribedClient(client);
        openClients.add(connection);
        response.setMessage("client added successfully");
        sendResponse(client, response);
    }

    //TODO: DEPRECATED REMOVE SAFELY
    private void handleGetAllMovies(ConnectionToClient client, Message response) {
        response.setMovies(database.getMoviesManger().getMovies());
        sendResponse(client, response);
    }

    //TODO: DEPRECATED REMOVE SAFELY
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

    //TODO: DEPRECATED REMOVE SAFELY
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

    //TODO: DEPRECATED REMOVE SAFELY
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

    //TODO: DEPRECATED REMOVE SAFELY
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

	//TODO: DEPRECATED REMOVE SAFELY
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
                    loginResponse.getUserId(),
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
        logoutRequest.setUsername(sessionKeys.get(logoutRequest.getSessionKey()).getUsername());

        System.out.println("Logout request received:" + logoutRequest.toString()); //TODO: remove this line debug only
        LogoutResponse logoutResponse = database.getUsersManager().logoutUser(logoutRequest);
        sessionKeys.remove(logoutRequest.getSessionKey());
        System.out.println("Logout response: " + logoutResponse.toString()); //TODO: remove this line debug only

        sendResponse(client, new Message(logoutResponse, MessageType.LOGOUT_RESPONSE));
    }

    private void handleRegisterRequest(Message request, ConnectionToClient client) {
        RegisterRequest registerRequest = (RegisterRequest) request.getDataObject();
        System.out.println("Register request received:" + registerRequest.toString()); //TODO: remove this line debug only
        RegisterResponse registerResponse = database.getUsersManager().registerUser(registerRequest);
        sendResponse(client, new Message(registerResponse, MessageType.REGISTER_RESPONSE));

        // send a patch to all the logged-in admins to notify them that a new user has been added
        if (registerResponse.isSuccess()) {
            NewUserAddedPatch newUserAddedPatch = new NewUserAddedPatch()
                    .setSuccess(true)
                    .setMessage("New user added successfully")
                    .setUser(database.getUsersManager().getUserById(registerResponse.getUserId()));

            sendToAllAdmins(new Message(newUserAddedPatch, MessageType.NEW_USER_ADDED_PATCH));
        }
    }

    private void sendResponse(ConnectionToClient client, Message response) {
        try {
            client.sendToClient(response);
        } catch (IOException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }

    private void handleGetAllUsersRequest(Message request, ConnectionToClient client) {
        GetAllUsersRequest getAllUsersRequset = (GetAllUsersRequest) request.getDataObject();
        LoggedInUser loggedInUser = sessionKeys.get(getAllUsersRequset.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }


        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER :
            case MANAGER_OF_ALL_BRANCHES :
            case BRANCH_MANAGER :
            case CUSTOMER_SERVICE:
                break;
            case CONTENT_MANAGER:
            case USER:
            default:
                sendErrorMessage(client, "Error! User does not have permission to get all users");
                return;
        }


        getAllUsersRequset.setUsername(sessionKeys.get(getAllUsersRequset.getSessionKey()).getUsername()); // add the username for faster access

        System.out.println("Get all users request received:" + getAllUsersRequset.toString()); //TODO: remove this line debug only
        GetAllUsersResponse getAllUsersResponse = database.getUsersManager().getAllUsers(getAllUsersRequset);
        System.out.println("Get all users response: " + getAllUsersResponse.toString()); //TODO: remove this line debug only
        sendResponse(client, new Message(getAllUsersResponse, MessageType.GET_ALL_USERS_RESPONSE));

    }

    private void handleGetMyTicketsRequest(Message request, ConnectionToClient client) {
        GetMyTicketsRequest getMyTicketsRequest = (GetMyTicketsRequest) request.getDataObject();
        LoggedInUser loggedInUser = sessionKeys.get(getMyTicketsRequest.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }

        getMyTicketsRequest.setUsername(loggedInUser.getUsername());
        getMyTicketsRequest.setUserId(loggedInUser.getUserId());

        System.out.println("Get my tickets request received:" + getMyTicketsRequest.toString()); //TODO: remove this line debug only
        GetMyTicketsResponse getMyTicketsResponse = database.getTicketsManager().getMyTickets(getMyTicketsRequest);
        System.out.println("Get my tickets response: " + getMyTicketsResponse.toString()); //TODO: remove this line debug only

        sendResponse(client, new Message(getMyTicketsResponse, MessageType.GET_MY_TICKETS_RESPONSE));
    }

    private void handleBlockUserRequest(Message request, ConnectionToClient client) {
        BlockUserRequest blockUserRequest = (BlockUserRequest) request.getDataObject();
        LoggedInUser loggedInUser = sessionKeys.get(blockUserRequest.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }

        blockUserRequest.setUsername(loggedInUser.getUsername());
        blockUserRequest.setUserId(loggedInUser.getUserId());

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER :
            case MANAGER_OF_ALL_BRANCHES :
            case BRANCH_MANAGER :
                break;
            case CUSTOMER_SERVICE:
            case CONTENT_MANAGER:
            case USER:
            default:
                sendErrorMessage(client, "Error! User does not have permission to block users");
                return;
        }

        System.out.println("Block user request received:" + blockUserRequest.toString()); //TODO: remove this line debug only
        BlockUserResponse blockUserResponse = database.getUsersManager().blockUser(blockUserRequest);
        System.out.println("Block user response: " + blockUserResponse.toString()); //TODO: remove this line debug only

        sendResponse(client, new Message(blockUserResponse, MessageType.BLOCK_USER_RESPONSE));
    }

    private void handleUnblockUserRequest(Message request, ConnectionToClient client) {
        UnblockUserRequest unblockUserRequest = (UnblockUserRequest) request.getDataObject();
        LoggedInUser loggedInUser = sessionKeys.get(unblockUserRequest.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }

        unblockUserRequest.setUsername(loggedInUser.getUsername());
        unblockUserRequest.setUserId(loggedInUser.getUserId());

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER :
            case MANAGER_OF_ALL_BRANCHES :
            case BRANCH_MANAGER :
                break;
            case CUSTOMER_SERVICE:
            case CONTENT_MANAGER:
            case USER:
            default:
                sendErrorMessage(client, "Error! User does not have permission to unblock users");
                return;
        }

        System.out.println("Unblock user request received:" + unblockUserRequest.toString()); //TODO: remove this line debug only
        UnblockUserResponse unblockUserResponse = database.getUsersManager().unblockUser(unblockUserRequest);
        System.out.println("Unblock user response: " + unblockUserResponse.toString()); //TODO: remove this line debug only

        sendResponse(client, new Message(unblockUserResponse, MessageType.UNBLOCK_USER_RESPONSE));
    }

    private void handleRemoveUserRequest(Message request, ConnectionToClient client) {
        RemoveUserRequest removeUserRequest = (RemoveUserRequest) request.getDataObject();
        LoggedInUser loggedInUser = sessionKeys.get(removeUserRequest.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }

        removeUserRequest.setUsername(loggedInUser.getUsername());
        removeUserRequest.setUserId(loggedInUser.getUserId());

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
                break;
            case BRANCH_MANAGER:
            case CUSTOMER_SERVICE:
            case CONTENT_MANAGER:
            case USER:
            default:
                sendErrorMessage(client, "Error! User does not have permission to remove users");
                return;
        }

        System.out.println("Remove user request received:" + removeUserRequest.toString()); //TODO: remove this line debug only
        RemoveUserResponse removeUserResponse = database.getUsersManager().removeUser(removeUserRequest);
        System.out.println("Remove user response: " + removeUserResponse.toString()); //TODO: remove this line debug only

        sendResponse(client, new Message(removeUserResponse, MessageType.REMOVE_USER_RESPONSE));
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

    public void sendToAllAdmins(Message message) {
        // here we send to all the clients that registered to our server
        for (LoggedInUser loggedInUser : sessionKeys.values()) {
            if (loggedInUser.getRole() == UserRole.SYSTEM_MANAGER || loggedInUser.getRole() == UserRole.MANAGER_OF_ALL_BRANCHES || loggedInUser.getRole() == UserRole.BRANCH_MANAGER) {
                try {
                    loggedInUser.getClient().sendToClient(message);
                    System.out.println("Sent to client: " + loggedInUser.getClient().getName() + " the message: " + message.getMessage());
                } catch (IOException e1) {
                    System.out.println("Error sending message to this client: " + e1.getMessage());

                    // if the user fail to respond we will remove him from the logged-in users list
                    sessionKeys.remove(loggedInUser.getSessionKey());
                    //TODO : indicate the user that he is logged out

                }
            }
        }
    }
}