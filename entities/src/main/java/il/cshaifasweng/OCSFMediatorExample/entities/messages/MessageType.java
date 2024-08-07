package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serializable;

public enum MessageType implements Serializable {
    NONE,
    ERROR,
    STRING,
    LOGIN_REQUEST,
    LOGIN_RESPONSE,
    LOGOUT_REQUEST,
    LOGOUT_RESPONSE,
    REGISTER_REQUEST,
    REGISTER_RESPONSE,
    GET_ALL_USERS_REQUEST,
    GET_ALL_USERS_RESPONSE,
    GET_MY_TICKETS_REQUEST,
    GET_MY_TICKETS_RESPONSE,
    BLOCK_USER_REQUEST,
    BLOCK_USER_RESPONSE,
    UNBLOCK_USER_REQUEST,
    UNBLOCK_USER_RESPONSE,
    REMOVE_USER_REQUEST,
    REMOVE_USER_RESPONSE,
    NEW_USER_ADDED_PATCH,
    REMOVE_USER_PATCH,
    GET_ALL_MOVIES_REQUEST,
    GET_ALL_MOVIES_RESPONSE,
    ADD_MOVIE_REQUEST,
    ADD_MOVIE_RESPONSE,
    ADD_MOVIE_PATCH,
    REMOVE_MOVIE_REQUEST,
    REMOVE_MOVIE_RESPONSE,
    REMOVE_MOVIE_PATCH,

}

