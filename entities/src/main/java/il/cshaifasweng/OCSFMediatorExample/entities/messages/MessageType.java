package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serializable;

public enum MessageType implements Serializable {
    NONE,
    ERROR,
    STRING,

    //AUTH
    LOGIN_REQUEST,
    LOGIN_RESPONSE,
    LOGOUT_REQUEST,
    LOGOUT_RESPONSE,
    REGISTER_REQUEST,
    REGISTER_RESPONSE,

    // USERS
    GET_ALL_USERS_REQUEST,
    GET_ALL_USERS_RESPONSE,
    BLOCK_USER_REQUEST,
    BLOCK_USER_RESPONSE,
    UNBLOCK_USER_REQUEST,
    UNBLOCK_USER_RESPONSE,
    REMOVE_USER_REQUEST,
    REMOVE_USER_RESPONSE,
    NEW_USER_ADDED_PATCH,
    REMOVE_USER_PATCH,
    UPDATED_USER_PATCH,
    CHANGE_USER_ROLE_REQUEST,
    CHANGE_USER_ROLE_RESPONSE,

    // MOVIES
    GET_ALL_MOVIES_REQUEST,
    GET_ALL_MOVIES_RESPONSE,
    ADD_MOVIE_REQUEST,
    ADD_MOVIE_RESPONSE,
    ADD_MOVIE_PATCH,
    REMOVE_MOVIE_REQUEST,
    REMOVE_MOVIE_RESPONSE,
    REMOVE_MOVIE_PATCH,
    UPDATE_MOVIE_REQUEST,
    UPDATE_MOVIE_RESPONSE,
    UPDATE_MOVIE_PATCH,
    GET_MOVIE_REQUEST,
    GET_MOVIE_RESPONSE,
    GET_COMING_SOON_MOVIES_REQUEST,
    GET_COMING_SOON_MOVIES_RESPONSE,

    // SCREENINGS
    ADD_SCREENING_REQUEST,
    ADD_SCREENING_RESPONSE,
    ADD_SCREENING_PATCH,
    REMOVE_SCREENING_REQUEST,
    REMOVE_SCREENING_RESPONSE,
    REMOVE_SCREENING_PATCH,
    UPDATE_SCREENING_REQUEST,
    UPDATE_SCREENING_RESPONSE,
    UPDATE_SCREENING_PATCH,
    GET_ALL_SCREENINGS_REQUEST,
    GET_ALL_SCREENINGS_RESPONSE,
    GET_SCREENING_FOR_MOVIE_REQUEST,
    GET_SCREENING_FOR_MOVIE_RESPONSE,
    GET_SCREENING_FOR_HALL_REQUEST,
    GET_SCREENING_FOR_HALL_RESPONSE,


    // TICKETS
    GET_MY_TICKETS_REQUEST,
    GET_MY_TICKETS_RESPONSE,
    PURCHASE_TICKETS_REQUEST,
    PURCHASE_TICKETS_RESPONSE,

    // CINEMAS
    GET_ALL_CINEMAS_REQUEST,
    GET_ALL_CINEMAS_RESPONSE,
    ADD_CINEMA_REQUEST,
    ADD_CINEMA_RESPONSE,
    ADD_CINEMA_PATCH,
    REMOVE_CINEMA_REQUEST,
    REMOVE_CINEMA_RESPONSE,
    REMOVE_CINEMA_PATCH,
    UPDATE_CINEMA_REQUEST,
    UPDATE_CINEMA_RESPONSE,
    UPDATE_CINEMA_PATCH,
    GET_CINEMA_HALLS_REQUEST,
    GET_CINEMA_HALLS_RESPONSE,
    CINEMA_TICKETS_INFO_REQUEST,
    SHOW_CINEMA_INFO_RESPONSE,

    // HALLS
    ADD_HALL_REQUEST,
    ADD_HALL_RESPONSE,
    ADD_HALL_PATCH,
    REMOVE_HALL_REQUEST,
    REMOVE_HALL_RESPONSE,
    REMOVE_HALL_PATCH,
    UPDATE_HALL_REQUEST,
    UPDATE_HALL_RESPONSE,
    UPDATE_HALL_PATCH,
    GET_ALL_HALLS_REQUEST,
    GET_ALL_HALLS_RESPONSE,
    GET_HALL_REQUEST,
    GET_HALL_RESPONSE,

    // SEATS
    GET_SEATS_FOR_SCREENING_REQUEST,
    GET_SEATS_FOR_SCREENING_RESPONSE,

    NOTIFY_CLIENTS,
    NOTIFICATION,

    // Support Tickets
    GET_ALL_SUPPORT_TICKETS_REQUEST,
    GET_ALL_SUPPORT_TICKETS_RESPONSE,
    ADD_SUPPORT_TICKET_REQUEST,
    ADD_SUPPORT_TICKET_RESPONSE,
    ADD_SUPPORT_TICKET_PATCH,
    CLOSE_SUPPORT_TICKET_REQUEST,
    REMOVE_TICKET_REQUEST,
    REMOVE_TICKET_RESPONSE,
    GET_MY_SCREENINGS_REQUEST,
    GET_MY_SCREENINGS_RESPONSE,
    USER_TICKET_REMOVED_PATCH,
    DELETE_SUPPORT_TICKET_RESPONSE,
    SEND_SUPPORT_TICKET_REQUEST,
    CINEMA_SUPPORT_TICKETS_INFO_REQUEST,
    CINEMA_SUPPORT_TICKETS_INFO_RESPONSE,
    CINEMA_TICKETS_INFO_RESPONSE,


    GET_USER_INFO_REQUEST,
    GET_USER_INFO_RESPONSE,
    GET_ALL_TICKETS_REQUEST,
    GET_ALL_TICKETS_RESPONSE, PURCHASE_TICKETS_BUNDLE_REQUEST, PURCHASE_TICKETS_BUNDLE_RESPONSE,

}

