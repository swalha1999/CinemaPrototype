package il.cshaifasweng.OCSFMediatorExample.entities.messages;


import java.io.Serializable;

/**
 * This enum represents the version of the message.
 * V1 is the old version of the message. and its deprecated. and every type of message are in the same class and that is not good.
 * V2 is the new version of the message. it contains a dataObject field that can be any object that extends the Serializable interface.
 * this object can be any type of object that the message needs to send. you can see like LoginRequest, LoginResponse, etc.
 *
 * V3 the idea after V3 is that we send the entity object itself and not the dataObject. and this will allow to not make a request and response and patch
 * for every entity.
 */
public enum MessageVersion implements Serializable {
    V1, // TODO: this needs to DEPRECATED and every new message should be the newish version
    V2,  // THE NEWEST VERSION
    V3,  // TESTING
}


