package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.UserRole;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.*;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.*;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.*;
import il.cshaifasweng.OCSFMediatorExample.server.DAO.DatabaseController;
import il.cshaifasweng.OCSFMediatorExample.server.dataTypes.LoggedInUser;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.Session;

import java.io.IOException;
import java.util.HashMap;

public class Server extends AbstractServer {
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

        if (request.getVersion() == MessageVersion.V3) {
            handleV3Message(request, client);
        } else if (request.getVersion() == MessageVersion.V2) {
			handleV2Message(request, client);
        } else {
            sendErrorMessage(client, "Error! Unknown message version");
        }
    }

    private void handleV3Message(Message request, ConnectionToClient client) {

        switch (request.getType()) {

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
            case GET_ALL_MOVIES_REQUEST:
                handleGetAllMoviesRequest(request, client);
                break;
            case ADD_MOVIE_REQUEST:
                handleAddMovieRequest(request, client);
                break;
            case REMOVE_MOVIE_REQUEST:
                handleRemoveMovieRequest(request, client);
                break;
            case GET_MOVIE_REQUEST:
                handleGetMovieRequest(request, client);
                break;
            case UPDATE_MOVIE_REQUEST:
                handleUpdateMovieRequest(request, client);
                break;
            //TODO: add more cases here

            default:
                sendErrorMessage(client, "Error! Unknown message received Check if there is a case for it");
                break;
        }
    }

    private void handleV2Message(Message request, ConnectionToClient client) {
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

            default:
                sendErrorMessage(client, "Error! Unknown message received Check if there is a case for it this is not supported in V2");
                break;
        }
    }

    private void handleLoginRequest(Message request, ConnectionToClient client) {
        LoginRequest loginRequest = (LoginRequest) request.getDataObject();
        System.out.println("Login request received:" + loginRequest.toString()); //TODO: remove this line debug only

        logOutUser(loginRequest.getUsername(), "User has logged in from another device");

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

        // V2 request
        RegisterRequest registerRequest = (RegisterRequest) request.getDataObject();
        System.out.println("Register request received:" + registerRequest.toString()); //TODO: remove this line debug only
        RegisterResponse registerResponse = database.getUsersManager().registerUser(registerRequest);
        sendResponse(client, new Message(registerResponse, MessageType.REGISTER_RESPONSE));

        // send a patch to all the logged-in admins to notify them that a new user has been added
        if (registerResponse.isSuccess()) {
            // V3 patch
            Message newUserAddedPatch = new Message(MessageType.NEW_USER_ADDED_PATCH)
                    .setSuccess(true)
                    .setMessage("New user added successfully")
                    .setDataObject(database.getUsersManager().getUserById(registerResponse.getUserId()));

            sendToAllAdmins(newUserAddedPatch);
        }
    }

    private void handleGetAllUsersRequest(Message request, ConnectionToClient client) {

        LoggedInUser loggedInUser = sessionKeys.get(request.getSessionKey());
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
                sendErrorMessage(client, "Error! User does not have permission for this action");
                return;
        }

        request.setUsername(sessionKeys.get(request.getSessionKey()).getUsername()); // add the username for faster access
        request.setUserId(sessionKeys.get(request.getSessionKey()).getUserId()); // add the user id for faster access

        System.out.println("Get all users request received:" + request.toString()); //TODO: remove this line debug only
        Message response = database.getUsersManager().getAllUsers(request);
        System.out.println("Get all users response: " + response.toString()); //TODO: remove this line debug only
        sendResponse(client, response);

    }

    private void handleGetMyTicketsRequest(Message request, ConnectionToClient client) {
        LoggedInUser loggedInUser = sessionKeys.get(request.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }

        request.setUsername(loggedInUser.getUsername());
        request.setUserId(loggedInUser.getUserId());

        System.out.println("Get my tickets request received:" + request.toString()); //TODO: remove this line debug only
        Message response = database.getTicketsManager().getMyTickets(request);
        System.out.println("Get my tickets response: " + response.toString()); //TODO: remove this line debug only

        sendResponse(client, response);
    }

    private void handleBlockUserRequest(Message request, ConnectionToClient client) {

        LoggedInUser loggedInUser = sessionKeys.get(request.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }

        request.setUsername(loggedInUser.getUsername());
        request.setUserId(loggedInUser.getUserId());

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

        System.out.println("Block user request received:" + request.toString()); //TODO: remove this line debug only
        Message response = database.getUsersManager().blockUser(request);
        System.out.println("Block user response: " + response.toString()); //TODO: remove this line debug only

        sendResponse(client, response);
        if (response.isSuccess()) {
            // log out the user
            logOutUser(((User) request.getDataObject()).getUsername(), "User has been blocked from the system");
            //TODO: send a patch to all the logged-in admins to notify them that a user has been blocked
        }
    }

    private void handleUnblockUserRequest(Message request, ConnectionToClient client) {

        LoggedInUser loggedInUser = sessionKeys.get(request.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }

        request.setUsername(loggedInUser.getUsername());
        request.setUserId(loggedInUser.getUserId());

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

        System.out.println("Unblock user request received:" + request.toString()); //TODO: remove this line debug only
        Message response = database.getUsersManager().unblockUser(request);
        System.out.println("Unblock user response: " + response.toString()); //TODO: remove this line debug only
        sendResponse(client, response);

        //TODO: send a patch to all the logged-in admins to notify them that a user has been unblocked
    }

    private void handleRemoveUserRequest(Message request, ConnectionToClient client) {

        LoggedInUser loggedInUser = sessionKeys.get(request.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }

        request.setUsername(loggedInUser.getUsername());
        request.setUserId(loggedInUser.getUserId());

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

        System.out.println("Remove user request received:" + request.toString()); //TODO: remove this line debug only
        Message response = database.getUsersManager().removeUser(request);
        System.out.println("Remove user response: " + response.toString()); //TODO: remove this line debug only

        sendResponse(client,response);

        // send a patch to all the logged-in admins to notify them that a user has been removed
        if (response.isSuccess()) {
            // TODO: update this to lock out the user and remove him from the logged-in users list
            response.setMessageType(MessageType.REMOVE_USER_PATCH);
            sendToAllAdmins(response);
            logOutUser(((User) response.getDataObject()).getUsername(), "User has been removed from the system");
        }

    }

    private void handleGetAllMoviesRequest(Message request, ConnectionToClient client) {

        LoggedInUser loggedInUser = sessionKeys.get(request.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }

        request.setUsername(loggedInUser.getUsername());
        request.setUserId(loggedInUser.getUserId());

        System.out.println("Get all movies request received:" + request.toString()); //TODO: remove this line debug only
        Message response = database.getMoviesManger().getAllMovies(request);
        System.out.println("Get all movies response: " + response.toString()); //TODO: remove this line debug only

        sendResponse(client, response);
    }

    private void handleAddMovieRequest(Message request, ConnectionToClient client) {

        LoggedInUser loggedInUser = sessionKeys.get(request.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }

        request.setUsername(loggedInUser.getUsername());
        request.setUserId(loggedInUser.getUserId());

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
            case CONTENT_MANAGER:
                break;
            case BRANCH_MANAGER:
            case CUSTOMER_SERVICE:
            case USER:
            default:
                sendErrorMessage(client, "Error! User does not have permission to add movies");
                return;
        }

        System.out.println("Add movie request received:" + request.toString()); //TODO: remove this line debug only
        Message response = database.getMoviesManger().addMovie(request);
        System.out.println("Add movie response: " + response.toString()); //TODO: remove this line debug only

        sendResponse(client, response);

        // send a patch to all the logged-in users
        if (response.isSuccess()) {
            //TODO: update this to V3
            response.setMessageType(MessageType.ADD_MOVIE_PATCH);
            sendToAllLoggedInUsers(response);
        }

    }

    private void handleRemoveMovieRequest(Message request, ConnectionToClient client) {
        LoggedInUser loggedInUser = sessionKeys.get(request.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }

        request.setUsername(loggedInUser.getUsername());
        request.setUserId(loggedInUser.getUserId());

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
            case CONTENT_MANAGER:
                break;
            case BRANCH_MANAGER:
            case CUSTOMER_SERVICE:
            case USER:
            default:
                sendErrorMessage(client, "Error! User does not have permission to remove movies");
                return;
        }

        System.out.println("Remove movie request received:" + request.toString()); //TODO: remove this line debug only
        Message response = database.getMoviesManger().removeMovie(request);
        System.out.println("Remove movie response: " + response.toString()); //TODO: remove this line debug only

        sendResponse(client, response);

        // send a patch to all the logged-in users
        if (response.isSuccess()) {
            //TODO: update this to V3
            response.setMessageType(MessageType.REMOVE_MOVIE_PATCH);
            sendToAllLoggedInUsers(response);
        }
    }

    private void handleGetMovieRequest(Message request, ConnectionToClient client) {

        LoggedInUser loggedInUser = sessionKeys.get(request.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }

        request.setUsername(loggedInUser.getUsername());
        request.setUserId(loggedInUser.getUserId());

        System.out.println("Get movie request received:" + request.toString()); //TODO: remove this line debug only
        Message response = database.getMoviesManger().getMovie(request);
        System.out.println("Get movie response: " + response.toString()); //TODO: remove this line debug only

        sendResponse(client, response);
    }

    private void handleUpdateMovieRequest(Message request, ConnectionToClient client) {

        LoggedInUser loggedInUser = sessionKeys.get(request.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }

        request.setUsername(loggedInUser.getUsername());
        request.setUserId(loggedInUser.getUserId());

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
            case CONTENT_MANAGER:
                break;
            case BRANCH_MANAGER:
            case CUSTOMER_SERVICE:
            case USER:
            default:
                sendErrorMessage(client, "Error! User does not have permission to update movies");
                return;
        }

        System.out.println("Update movie request received:" + request.toString()); //TODO: remove this line debug only
        Message response = database.getMoviesManger().updateMovie(request);
        System.out.println("Update movie response: " + response.toString()); //TODO: remove this line debug only

        sendResponse(client, response);

        if (response.isSuccess()) {
            response.setMessageType(MessageType.UPDATE_MOVIE_PATCH);
            sendToAllLoggedInUsers(response);
        }
    }

    private boolean sendResponse(ConnectionToClient client, Message response) {
        try {
            client.sendToClient(response);
            return true;
        } catch (IOException e) {
            System.out.println("Error sending message: " + e.getMessage());
            return false;
        }
    }

    private void sendErrorMessage(ConnectionToClient client, String errorMessage) {
        try {
            client.sendToClient(new Message(errorMessage, MessageType.ERROR));
        } catch (IOException e) {
            System.out.println("Error sending error message: " + e.getMessage());
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

                LogoutRequest logoutRequest = new LogoutRequest(loggedInUser.getSessionKey());
                handleLogoutRequest(new Message(logoutRequest, MessageType.LOGOUT_REQUEST), loggedInUser.getClient());
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

    //TODO: the message dosn't reach the client
    public void logOutUser(String username, String message) {
        for (LoggedInUser loggedInUser : sessionKeys.values()) {
            if (loggedInUser.getUsername().equals(username)) {
                LogoutRequest logoutRequest = new LogoutRequest(loggedInUser.getSessionKey());
                handleLogoutRequest(new Message(logoutRequest, MessageType.LOGOUT_REQUEST), loggedInUser.getClient());
            }
        }
    }

}