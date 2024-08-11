package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serializable;

public enum MessageType implements Serializable {
    NONE, //DONE on server
    ERROR, //DONE on server
    STRING, //DONE on server
    LOGIN_REQUEST, // DONE on server and client
    LOGIN_RESPONSE, // DONE on server and client
    LOGOUT_REQUEST, // DONE on server and client
    LOGOUT_RESPONSE, // DONE on server and client
    REGISTER_REQUEST, // DONE on server and client
    REGISTER_RESPONSE, // DONE on server and client
    GET_ALL_USERS_REQUEST, // DONE on server and client
    GET_ALL_USERS_RESPONSE, // DONE on server and client
    GET_MY_TICKETS_REQUEST, // TODO
    GET_MY_TICKETS_RESPONSE, // TODO
    BLOCK_USER_REQUEST, // DONE on server and client
    BLOCK_USER_RESPONSE, // DONE on server and client
    UNBLOCK_USER_REQUEST, // DONE on server and client
    UNBLOCK_USER_RESPONSE, // DONE on server and client
    REMOVE_USER_REQUEST, // DONE on server and client
    REMOVE_USER_RESPONSE, // DONE on server and client
    NEW_USER_ADDED_PATCH, // DONE on server and client
    REMOVE_USER_PATCH, // DONE on server and client
    GET_ALL_MOVIES_REQUEST, // DONE on server and client
    GET_ALL_MOVIES_RESPONSE, // DONE on server and client
    ADD_MOVIE_REQUEST, // DONE on server and client
    ADD_MOVIE_RESPONSE, // DONE on server and client
    ADD_MOVIE_PATCH, // DONE on server and client
    REMOVE_MOVIE_REQUEST, // DONE on server and client
    REMOVE_MOVIE_RESPONSE, // DONE on server and client
    REMOVE_MOVIE_PATCH, // DONE on server and client
    UPDATE_MOVIE_REQUEST, // DONE on server and client
    UPDATE_MOVIE_RESPONSE, // DONE on server and client
    UPDATE_MOVIE_PATCH, // DONE on server TODO: check on client
    GET_MOVIE_REQUEST,  // DONE on server and client
    GET_MOVIE_RESPONSE, // DONE on server and client
    GET_ALL_SCREENINGS_REQUEST, // DONE on server and client
    GET_ALL_SCREENINGS_RESPONSE, // DONE on server and client
    GET_SCREENING_FOR_MOVIE_REQUEST, // DONE on server and client
    GET_SCREENING_FOR_MOVIE_RESPONSE, // DONE on server and client
    GET_ALL_CINEMAS_REQUEST, // TODO: do on the server and client
    GET_ALL_CINEMAS_RESPONSE, // TODO: do on the server and client
    ADD_CINEMA_REQUEST, // TODO: do on the server and client
    ADD_CINEMA_RESPONSE, // TODO: do on the server and client
    ADD_CINEMA_PATCH, // TODO: do on the server and client

}

