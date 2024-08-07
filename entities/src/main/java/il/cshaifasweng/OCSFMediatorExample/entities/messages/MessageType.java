package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serializable;

public enum MessageType implements Serializable {
    ERROR,
    STRING,
    LOGIN_REQUEST,
    LOGIN_RESPONSE,
    LOGOUT_REQUEST,
    LOGOUT_RESPONSE,
    REGISTER_REQUEST,
    REGISTER_RESPONSE,
}

