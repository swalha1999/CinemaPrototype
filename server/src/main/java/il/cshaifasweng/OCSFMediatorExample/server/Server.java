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
        this.database = DatabaseController.getInstance(session);
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        Message request = (Message) msg;
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.println("Message received: " + request.toString());

        if (request.getVersion() == MessageVersion.V3) {
            Message res = handleV3Message(request, client);
            System.out.println("Message response: " + res.toString());
        } else if (request.getVersion() == MessageVersion.V2) {
			handleV2Message(request, client);
        } else {
            sendErrorMessage(client, "Error! Unknown message version");
        }
    }

    private Message handleV3Message(Message request, ConnectionToClient client) {

        LoggedInUser loggedInUser = sessionKeys.get(request.getSessionKey());

        if (loggedInUser == null) {
            return sendErrorMessage(client, "Error! User is not logged in");
        }

        request.setUsername(loggedInUser.getUsername());
        request.setUserId(loggedInUser.getUserId());

        return switch (request.getType()) {
            //USERS
            case GET_ALL_USERS_REQUEST -> handleGetAllUsersRequest(request, client,loggedInUser);
            case BLOCK_USER_REQUEST -> handleBlockUserRequest(request, client, loggedInUser);
            case UNBLOCK_USER_REQUEST -> handleUnblockUserRequest(request, client, loggedInUser);
            case REMOVE_USER_REQUEST -> handleRemoveUserRequest(request, client, loggedInUser);
            case CHANGE_USER_ROLE_REQUEST -> handleChangeUserRoleRequest(request, client, loggedInUser);

            //TICKETS
            case GET_MY_TICKETS_REQUEST -> handleGetMyTicketsRequest(request, client, loggedInUser);
            case GET_ALL_MOVIES_REQUEST -> handleGetAllMoviesRequest(request, client, loggedInUser);
            case PURCHASE_TICKETS_REQUEST -> handlePurchaseTicketsRequest(request, client, loggedInUser);


            //MOVIES
            case ADD_MOVIE_REQUEST -> handleAddMovieRequest(request, client, loggedInUser);
            case REMOVE_MOVIE_REQUEST -> handleRemoveMovieRequest(request, client, loggedInUser);
            case GET_MOVIE_REQUEST -> handleGetMovieRequest(request, client, loggedInUser);
            case UPDATE_MOVIE_REQUEST -> handleUpdateMovieRequest(request, client, loggedInUser);

            //SCREENINGS
            case GET_ALL_SCREENINGS_REQUEST -> handleGetAllScreeningsRequest(request, client, loggedInUser);
            case GET_SCREENING_FOR_MOVIE_REQUEST -> handleGetScreeningForMovieRequest(request, client, loggedInUser);
            case GET_SCREENING_FOR_HALL_REQUEST -> handleGetScreeningForHallRequest(request, client, loggedInUser);
            case ADD_SCREENING_REQUEST -> handleAddScreeningRequest(request, client, loggedInUser);
            case REMOVE_SCREENING_REQUEST -> handleRemoveScreeningRequest(request, client, loggedInUser);
            case UPDATE_SCREENING_REQUEST -> handleUpdateScreeningRequest(request, client, loggedInUser);

            //CINEMAS
            case GET_ALL_CINEMAS_REQUEST -> handleGetAllCinemasRequest(request, client, loggedInUser);
            case GET_CINEMA_HALLS_REQUEST -> handleGetCinemaHallsRequest(request, client, loggedInUser);
            case ADD_CINEMA_REQUEST -> handleAddCinemaRequest(request, client, loggedInUser);
            case REMOVE_CINEMA_REQUEST -> handleRemoveCinemaRequest(request, client, loggedInUser);
            case UPDATE_CINEMA_REQUEST -> handleUpdateCinemaRequest(request, client, loggedInUser);

            //HALLS
            case ADD_HALL_REQUEST -> handleAddHallRequest(request, client, loggedInUser);

            //SEATS
            case GET_SEATS_FOR_SCREENING_REQUEST -> handleGetSeatsForScreeningRequest(request, client, loggedInUser);

            default -> sendErrorMessage(client, "Error! Unknown message received Check if there is a case for it");
        };
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

    private Message handleLoginRequest(Message request, ConnectionToClient client) {
        LoginRequest loginRequest = (LoginRequest) request.getDataObject();
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

        Message response = new Message(loginResponse, MessageType.LOGIN_RESPONSE);
		sendResponse(client, response);

        return response;
    }

    private Message handleLogoutRequest(Message request, ConnectionToClient client) {
        LogoutRequest logoutRequest = (LogoutRequest) request.getDataObject();
        logoutRequest.setUsername(sessionKeys.get(logoutRequest.getSessionKey()).getUsername());

        LogoutResponse logoutResponse = database.getUsersManager().logoutUser(logoutRequest);
        sessionKeys.remove(logoutRequest.getSessionKey());

        Message response = new Message(logoutResponse, MessageType.LOGOUT_RESPONSE);
        sendResponse(client, response);
        return response;

    }

    private Message handleRegisterRequest(Message request, ConnectionToClient client) {

        // V2 request
        RegisterRequest registerRequest = (RegisterRequest) request.getDataObject();
        RegisterResponse registerResponse = database.getUsersManager().registerUser(registerRequest);
        Message response = new Message(registerResponse, MessageType.REGISTER_RESPONSE);
        sendResponse(client,response);

        // send a patch to all the logged-in admins to notify them that a new user has been added
        if (registerResponse.isSuccess()) {
            // V3 patch
            Message newUserAddedPatch = new Message(MessageType.NEW_USER_ADDED_PATCH)
                    .setSuccess(true)
                    .setMessage("New user added successfully")
                    .setDataObject(database.getUsersManager().getUserById(registerResponse.getUserId()));

            sendToAllAdmins(newUserAddedPatch);
        }

        return response;
    }

    private Message handleGetAllUsersRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER :
            case MANAGER_OF_ALL_BRANCHES :
            case BRANCH_MANAGER :
            case CUSTOMER_SERVICE:
                break;
            case CONTENT_MANAGER:
            case USER:
            default:
                return sendErrorMessage(client, "Error! User does not have permission for this action");
        }

        request.setUsername(sessionKeys.get(request.getSessionKey()).getUsername()); // add the username for faster access
        request.setUserId(sessionKeys.get(request.getSessionKey()).getUserId()); // add the user id for faster access

        Message response = database.getUsersManager().getAllUsers(request);
        sendResponse(client, response);

        return response;
    }

    private Message handleGetMyTicketsRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {
        request.setUsername(loggedInUser.getUsername());
        request.setUserId(loggedInUser.getUserId());

        Message response = database.getTicketsManager().getMyTickets(request);

        sendResponse(client, response);
        return response;
    }

    private Message handleBlockUserRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER :
            case MANAGER_OF_ALL_BRANCHES :
            case BRANCH_MANAGER :
                break;
            case CUSTOMER_SERVICE:
            case CONTENT_MANAGER:
            case USER:
            default:
                return sendErrorMessage(client, "Error! User does not have permission to block users");
        }

        Message response = database.getUsersManager().blockUser(request);

        sendResponse(client, response);
        if (response.isSuccess()) {

            logOutUser(((User) request.getDataObject()).getUsername(), "User has been blocked from the system");
            response.setMessageType(MessageType.UPDATED_USER_PATCH);
            sendToAllAdmins(response);
        }

        return response.setMessageType(MessageType.BLOCK_USER_RESPONSE);
    }

    private Message handleUnblockUserRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {


        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER :
            case MANAGER_OF_ALL_BRANCHES :
            case BRANCH_MANAGER :
                break;
            case CUSTOMER_SERVICE:
            case CONTENT_MANAGER:
            case USER:
            default:
                return sendErrorMessage(client, "Error! User does not have permission to unblock users");
        }

        Message response = database.getUsersManager().unblockUser(request);
        sendResponse(client, response);

        if (response.isSuccess()) {
            response.setMessageType(MessageType.UPDATED_USER_PATCH);
            sendToAllAdmins(response);
        }

        return response.setMessageType(MessageType.UNBLOCK_USER_RESPONSE);
    }

    private Message handleRemoveUserRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
                break;
            case BRANCH_MANAGER:
            case CUSTOMER_SERVICE:
            case CONTENT_MANAGER:
            case USER:
            default:
                return sendErrorMessage(client, "Error! User does not have permission to remove users");
        }

        Message response = database.getUsersManager().removeUser(request);

        sendResponse(client,response);

        // send a patch to all the logged-in admins to notify them that a user has been removed
        if (response.isSuccess()) {
            response.setMessageType(MessageType.REMOVE_USER_PATCH);
            sendToAllAdmins(response);
            logOutUser(((User) response.getDataObject()).getUsername(), "User has been removed from the system");
        }

        return response.setMessageType(MessageType.REMOVE_USER_RESPONSE);

    }

    private Message handleGetAllMoviesRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {
        Message response = database.getMoviesManager().getAllMovies(request);
        sendResponse(client, response);
        return response;
    }

    private Message handleAddMovieRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
            case CONTENT_MANAGER:
                break;
            case BRANCH_MANAGER:
            case CUSTOMER_SERVICE:
            case USER:
            default:
                return sendErrorMessage(client, "Error! User does not have permission to add movies");
        }

        Message response = database.getMoviesManager().addMovie(request);

        sendResponse(client, response);

        // send a patch to all the logged-in users
        if (response.isSuccess()) {
            response.setMessageType(MessageType.ADD_MOVIE_PATCH);
            sendToAllLoggedInUsers(response);
        }

        return response.setMessageType(MessageType.ADD_MOVIE_RESPONSE);

    }

    private Message handleRemoveMovieRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
            case CONTENT_MANAGER:
                break;
            case BRANCH_MANAGER:
            case CUSTOMER_SERVICE:
            case USER:
            default:
                return sendErrorMessage(client, "Error! User does not have permission to remove movies");
        }

        Message response = database.getMoviesManager().removeMovie(request);
        sendResponse(client, response);

        // send a patch to all the logged-in users
        if (response.isSuccess()) {
            response.setMessageType(MessageType.REMOVE_MOVIE_PATCH);
            sendToAllLoggedInUsers(response);
        }

        return response.setMessageType(MessageType.REMOVE_MOVIE_RESPONSE);
    }

    private Message handleGetMovieRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        Message response = database.getMoviesManager().getMovie(request);
        sendResponse(client, response);

        return response;
    }

    private Message handleUpdateMovieRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
            case CONTENT_MANAGER:
                break;
            case BRANCH_MANAGER:
            case CUSTOMER_SERVICE:
            case USER:
            default:
                return sendErrorMessage(client, "Error! User does not have permission to update movies");
        }

        Message response = database.getMoviesManager().updateMovie(request);
        sendResponse(client, response);

        if (response.isSuccess()) {
            response.setMessageType(MessageType.UPDATE_MOVIE_PATCH);
            sendToAllLoggedInUsers(response);
        }
        return response.setMessageType(MessageType.UPDATE_MOVIE_RESPONSE);
    }

    private Message handleGetAllScreeningsRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
            case BRANCH_MANAGER:
            case CUSTOMER_SERVICE:
            case CONTENT_MANAGER:
                break;
            case USER:
            default:
                return sendErrorMessage(client, "Error! User does not have permission to this action");
        }

        Message response = database.getScreeningsManager().getAllScreenings(request);
        sendResponse(client, response);

        return response;
    }

    private Message handleGetScreeningForMovieRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        Message response = database.getScreeningsManager().getAllScreeningsForMovie(request);
        sendResponse(client, response);

        return response;
    }

    private Message handleGetAllCinemasRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
            case BRANCH_MANAGER:
            case CUSTOMER_SERVICE:
                break;
            case CONTENT_MANAGER:
            case USER:
            case NOT_LOGGED_IN:
            default:
                return sendErrorMessage(client, "Error! User does not have permission to this action");
        }

        Message response = database.getCinemasManager().getAllCinemas(request);
        sendResponse(client, response);

        return response;
    }

    private Message handleGetCinemaHallsRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
            case BRANCH_MANAGER:
            case CUSTOMER_SERVICE:
                break;
            case CONTENT_MANAGER:
            case USER:
            default:
                return sendErrorMessage(client, "Error! User does not have permission to this action");
        }

        Message response = database.getCinemasManager().getCinemaHalls(request);
        sendResponse(client, response);
        return response;
    }

    private Message handleAddCinemaRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
                break;
            case BRANCH_MANAGER:
            case CUSTOMER_SERVICE:
            case CONTENT_MANAGER:
            case USER:
            default:
                return sendErrorMessage(client, "Error! User does not have permission to add cinemas");
        }

        Message response = database.getCinemasManager().addCinema(request);

        sendResponse(client, response);

        // send a patch to all the logged-in admin
        if (response.isSuccess()) {
            response.setMessageType(MessageType.ADD_CINEMA_PATCH);
            sendToAllAdmins(response);
        }
        return response.setMessageType(MessageType.ADD_CINEMA_RESPONSE);
    }

    private Message handleRemoveCinemaRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
                break;
            case BRANCH_MANAGER:
            case CUSTOMER_SERVICE:
            case CONTENT_MANAGER:
            case USER:
            default:
                return sendErrorMessage(client, "Error! User does not have permission to add cinemas");
        }

        Message response = database.getCinemasManager().removeCinema(request);

        sendResponse(client, response);

        // send a patch to all the logged-in admin
        if (response.isSuccess()) {
            response.setMessageType(MessageType.REMOVE_CINEMA_PATCH);
            sendToAllAdmins(response);
        }

        return response.setMessageType(MessageType.REMOVE_CINEMA_RESPONSE);
    }

    private Message handleUpdateCinemaRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
                break;
            case BRANCH_MANAGER:
            case CUSTOMER_SERVICE:
            case CONTENT_MANAGER:
            case USER:
            default:
                return sendErrorMessage(client, "Error! User does not have permission to add cinemas");
        }

        Message response = database.getCinemasManager().updateCinema(request);
        sendResponse(client, response);

        // send a patch to all the logged-in admin
        if (response.isSuccess()) {
            response.setMessageType(MessageType.UPDATE_CINEMA_PATCH);
            sendToAllAdmins(request);
        }

        return response.setMessageType(MessageType.UPDATE_CINEMA_RESPONSE);
    }

    private Message handleChangeUserRoleRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
                break;
            case BRANCH_MANAGER:
            case CUSTOMER_SERVICE:
            case CONTENT_MANAGER:
            case USER:
            default:
                return sendErrorMessage(client, "Error! User does not have permission to change user roles");

        }

        Message response = database.getUsersManager().changeUserRole(request);
        sendResponse(client, response);

        // send a patch to all the logged-in admin
        if (response.isSuccess()) {
            logOutUser(((User) request.getDataObject()).getUsername(), "User role has been changed");
            response.setMessageType(MessageType.UPDATED_USER_PATCH);
            sendToAllAdmins(response);
        }

        return response.setMessageType(MessageType.CHANGE_USER_ROLE_RESPONSE);
    }

    private Message handleGetScreeningForHallRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        Message response = database.getScreeningsManager().getScreeningForHall(request);
        sendResponse(client, response);

        return response;
    }

    private Message handleAddHallRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

            switch (loggedInUser.getRole()) {
                case SYSTEM_MANAGER:
                case MANAGER_OF_ALL_BRANCHES:
                case BRANCH_MANAGER:
                    break;
                case CUSTOMER_SERVICE:
                case CONTENT_MANAGER:
                case USER:
                default:
                    return sendErrorMessage(client, "Error! User does not have permission to add halls");
            }

            Message response = database.getHallsManager().addHall(request);
            sendResponse(client, response);

            return response;
    }

    private Message handleAddScreeningRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
            case BRANCH_MANAGER:
                break;
            case CUSTOMER_SERVICE:
            case CONTENT_MANAGER:
            case USER:
            default:
                return sendErrorMessage(client, "Error! User does not have permission to add screenings");
        }

        Message response = database.getScreeningsManager().addScreening(request);
        sendResponse(client, response);

        if(response.isSuccess()) {
            response.setMessageType(MessageType.ADD_SCREENING_PATCH);
            sendToAllLoggedInUsers(response);
        }

        return response.setMessageType(MessageType.ADD_SCREENING_RESPONSE);
    }

    private Message handleRemoveScreeningRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
            case BRANCH_MANAGER:
            case CONTENT_MANAGER:
                break;
            case CUSTOMER_SERVICE:
            case USER:
            default:
                return sendErrorMessage(client, "Error! User does not have permission to remove screenings");
        }

        Message response = database.getScreeningsManager().removeScreening(request);
        sendResponse(client, response);

        if(response.isSuccess()) {
            response.setMessageType(MessageType.REMOVE_SCREENING_PATCH);
            sendToAllLoggedInUsers(response);
        }

        return response.setMessageType(MessageType.REMOVE_SCREENING_RESPONSE);
    }

    private Message handleUpdateScreeningRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {

        switch (loggedInUser.getRole()) {
            case SYSTEM_MANAGER:
            case MANAGER_OF_ALL_BRANCHES:
            case BRANCH_MANAGER:
            case CONTENT_MANAGER:
                break;
            case CUSTOMER_SERVICE:
            case USER:
            default:
                return sendErrorMessage(client, "Error! User does not have permission to update screenings");
        }

        Message response = database.getScreeningsManager().updateScreening(request);
        sendResponse(client, response);

        if(response.isSuccess()) {
            response.setMessageType(MessageType.UPDATE_SCREENING_PATCH);
            sendToAllLoggedInUsers(response);
        }

        return response.setMessageType(MessageType.UPDATE_SCREENING_RESPONSE);
    }

    private Message handleGetSeatsForScreeningRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {
        Message response = database.getSeatsManager().getSeatsForScreening(request);
        sendResponse(client, response);
        return response;
    }

    private Message handlePurchaseTicketsRequest(Message request, ConnectionToClient client, LoggedInUser loggedInUser) {
        Message response = database.getTicketsManager().purchaseTickets(request);
        sendResponse(client, response);
        return response;
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

    private Message sendErrorMessage(ConnectionToClient client, String errorMessage) {
        Message response = new Message(MessageType.ERROR);
        response.setMessage(errorMessage);
        try {
            client.sendToClient(response);
        } catch (IOException e) {
            System.out.println("Error sending error message: " + e.getMessage());
        }
        return response;
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

                }
            }
        }
    }

    public void logOutUser(String username, String message) {
        for (LoggedInUser loggedInUser : sessionKeys.values()) {
            if (loggedInUser.getUsername().equals(username)) {
                LogoutRequest logoutRequest = new LogoutRequest(loggedInUser.getSessionKey());
                handleLogoutRequest(new Message(logoutRequest, MessageType.LOGOUT_REQUEST), loggedInUser.getClient());
            }
        }
    }

    public UserRole addInfoToRequest(Message request) {
        LoggedInUser loggedInUser = sessionKeys.get(request.getSessionKey());
        if (loggedInUser == null) {
            return UserRole.NOT_LOGGED_IN;
        }
        request.setUsername(loggedInUser.getUsername());
        request.setUserId(loggedInUser.getUserId());
        return loggedInUser.getRole();
    }

}