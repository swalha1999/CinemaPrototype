package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serializable;

public enum MessageType implements Serializable {
    NONE, //DONE on server
    ERROR, //DONE on server
    STRING, //DONE on server

    //AUTH
    LOGIN_REQUEST, // DONE on server and client
    LOGIN_RESPONSE, // DONE on server and client
    LOGOUT_REQUEST, // DONE on server and client
    LOGOUT_RESPONSE, // DONE on server and client
    REGISTER_REQUEST, // DONE on server and client
    REGISTER_RESPONSE, // DONE on server and client

    // USERS
    GET_ALL_USERS_REQUEST, // DONE on server and client
    GET_ALL_USERS_RESPONSE, // DONE on server and client
    BLOCK_USER_REQUEST, // DONE on server and client
    BLOCK_USER_RESPONSE, // DONE on server and client
    UNBLOCK_USER_REQUEST, // DONE on server and client
    UNBLOCK_USER_RESPONSE, // DONE on server and client
    REMOVE_USER_REQUEST, // DONE on server and client
    REMOVE_USER_RESPONSE, // DONE on server and client
    NEW_USER_ADDED_PATCH, // DONE on server and client
    REMOVE_USER_PATCH, // DONE on server and client
    UPDATED_USER_PATCH, // DONE on server TODO: client
    CHANGE_USER_ROLE_REQUEST, // Done on server TODO client
    CHANGE_USER_ROLE_RESPONSE, //Done on server TODO: DONE!!!

    // MOVIES
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

    // SCREENINGS
    GET_ALL_SCREENINGS_REQUEST,
    GET_ALL_SCREENINGS_RESPONSE,
    GET_SCREENING_FOR_MOVIE_REQUEST,
    GET_SCREENING_FOR_MOVIE_RESPONSE,
    GET_SCREENING_FOR_HALL_REQUEST,
    GET_SCREENING_FOR_HALL_RESPONSE,



    // TICKETS
    GET_MY_TICKETS_REQUEST, // DONE on server TODO: client
    GET_MY_TICKETS_RESPONSE, // DONE on server TODO: client
    PURCHASE_TICKET_REQUEST, // DONE on server TODO: client

    // CINEMAS
    GET_ALL_CINEMAS_REQUEST, // Done on Server TODO: client
    GET_ALL_CINEMAS_RESPONSE, // Done on Server and client
    ADD_CINEMA_REQUEST, // Done one server TODO: client
    ADD_CINEMA_RESPONSE, // Done one server and client
    ADD_CINEMA_PATCH, // Done on the server TODO: client
    REMOVE_CINEMA_REQUEST, // Done on the server TODO: client
    REMOVE_CINEMA_RESPONSE, // Done on the server and client
    REMOVE_CINEMA_PATCH, // Done on the server TODO: client
    UPDATE_CINEMA_REQUEST, // Done on the server TODO: client
    UPDATE_CINEMA_RESPONSE, // Done on the server and client
    UPDATE_CINEMA_PATCH, // Done on the server TODO: client
    GET_CINEMA_HALLS_REQUEST, // Done on server TODO on client
    GET_CINEMA_HALLS_RESPONSE, // Done on client TODO on server

    // HALLS
    ADD_HALL_REQUEST, // TODO on server and client
    ADD_HALL_RESPONSE, // TODO on server and client
    ADD_HALL_PATCH, // TODO on server and client
    REMOVE_HALL_REQUEST, // TODO on server and client
    REMOVE_HALL_RESPONSE, // TODO on server and client
    REMOVE_HALL_PATCH, // TODO on server and client
    UPDATE_HALL_REQUEST, // TODO on server and client
    UPDATE_HALL_RESPONSE, // TODO on server and client
    UPDATE_HALL_PATCH, // TODO on server and client
    GET_ALL_HALLS_REQUEST, //TODO on server TODO on client
    GET_ALL_HALLS_RESPONSE, //TODO on server TODO on client
    GET_HALL_REQUEST, // TODO on server TODO on client
    GET_HALL_RESPONSE, // TODO on server TODO on client

}

