package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.UserRole;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.*;
import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs.AddMoviePatch;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs.NewUserAddedPatch;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs.RemoveMoviePatch;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs.RemoveUserPatch;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.*;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.*;
import il.cshaifasweng.OCSFMediatorExample.server.DAO.DatabaseController;
import il.cshaifasweng.OCSFMediatorExample.server.DAO.UserDAO;
import il.cshaifasweng.OCSFMediatorExample.server.dataTypes.LoggedInUser;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;
import org.hibernate.Session;


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


                //TODO: add more cases here

            default:
                sendErrorMessage(client, "Error! Unknown message received Check if there is a case for it");
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

        // send a patch to all the logged-in admins to notify them that a user has been removed
        if (removeUserResponse.isSuccess()) {
            RemoveUserPatch newUserAddedPatch = new RemoveUserPatch()
                    .setSuccess(true)
                    .setMessage("User removed successfully")
                    .setUsername(removeUserRequest.getUsernameToRemove());

            sendToAllAdmins(new Message(newUserAddedPatch, MessageType.REMOVE_USER_PATCH));
        }

    }

    private void handleGetAllMoviesRequest(Message request, ConnectionToClient client) {
        GetAllMoviesRequest getAllMoviesRequest = (GetAllMoviesRequest) request.getDataObject();
        LoggedInUser loggedInUser = sessionKeys.get(getAllMoviesRequest.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }

        getAllMoviesRequest.setUsername(loggedInUser.getUsername());
        getAllMoviesRequest.setUserId(loggedInUser.getUserId());

        System.out.println("Get all movies request received:" + getAllMoviesRequest.toString()); //TODO: remove this line debug only
        GetAllMoviesResponse getAllMoviesResponse = database.getMoviesManger().getAllMovies(getAllMoviesRequest);
        System.out.println("Get all movies response: " + getAllMoviesResponse.toString()); //TODO: remove this line debug only

        sendResponse(client, new Message(getAllMoviesResponse, MessageType.GET_ALL_MOVIES_RESPONSE));
    }

    private void handleAddMovieRequest(Message request, ConnectionToClient client) {
        AddMovieRequest addMovieRequest = (AddMovieRequest) request.getDataObject();
        LoggedInUser loggedInUser = sessionKeys.get(addMovieRequest.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }

        addMovieRequest.setUsername(loggedInUser.getUsername());
        addMovieRequest.setUserId(loggedInUser.getUserId());

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

        System.out.println("Add movie request received:" + addMovieRequest.toString()); //TODO: remove this line debug only
        AddMovieResponse addMovieResponse = database.getMoviesManger().addMovie(addMovieRequest);
        System.out.println("Add movie response: " + addMovieResponse.toString()); //TODO: remove this line debug only

        sendResponse(client, new Message(addMovieResponse, MessageType.ADD_MOVIE_RESPONSE));

        // send a patch to all the logged-in users
        if (addMovieResponse.isSuccess()) {
            AddMoviePatch addMoviePatch = new AddMoviePatch()
                    .setMessage("Movie added successfully")
                    .setMovie(addMovieResponse.getMovie());

            sendToAllLoggedInUsers(new Message(addMoviePatch, MessageType.ADD_MOVIE_PATCH));
        }

    }

    private void handleRemoveMovieRequest(Message request, ConnectionToClient client) {
        RemoveMovieRequest removeMovieRequest = (RemoveMovieRequest) request.getDataObject();
        LoggedInUser loggedInUser = sessionKeys.get(removeMovieRequest.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }

        removeMovieRequest.setUsername(loggedInUser.getUsername());
        removeMovieRequest.setUserId(loggedInUser.getUserId());

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

        System.out.println("Remove movie request received:" + removeMovieRequest.toString()); //TODO: remove this line debug only
        RemoveMovieResponse removeMovieResponse = database.getMoviesManger().removeMovie(removeMovieRequest);
        System.out.println("Remove movie response: " + removeMovieResponse.toString()); //TODO: remove this line debug only

        sendResponse(client, new Message(removeMovieResponse, MessageType.REMOVE_MOVIE_RESPONSE));

        // send a patch to all the logged-in users
        if (removeMovieResponse.isSuccess()) {
            RemoveMoviePatch removeMoviePatch = new RemoveMoviePatch()
                    .setMessage("Movie removed from the system")
                    .setMovie(removeMovieResponse.getMovie());

            sendToAllLoggedInUsers(new Message(removeMoviePatch, MessageType.REMOVE_MOVIE_PATCH));
        }
    }

    private void handleGetMovieRequest(Message request, ConnectionToClient client) {
        GetMovieRequest getMovieRequest = (GetMovieRequest) request.getDataObject();
        LoggedInUser loggedInUser = sessionKeys.get(getMovieRequest.getSessionKey());

        if (loggedInUser == null) {
            sendErrorMessage(client, "Error! User is not logged in");
            return;
        }

        getMovieRequest.setUsername(loggedInUser.getUsername());
        getMovieRequest.setUserId(loggedInUser.getUserId());

        System.out.println("Get movie request received:" + getMovieRequest.toString()); //TODO: remove this line debug only
        GetMovieResponse getMovieResponse = database.getMoviesManger().getMovie(getMovieRequest);
        System.out.println("Get movie response: " + getMovieResponse.toString()); //TODO: remove this line debug only

        sendResponse(client, new Message(getMovieResponse, MessageType.GET_MOVIE_RESPONSE));
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