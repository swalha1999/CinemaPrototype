
### `Message`

Represents a message that can be sent between the client and server. It includes various fields and methods to handle different types of messages.
and the different versions of the message. mainly V1 and V2.
### `MessageType`

An enum that defines the different types of messages that can be sent.  
like LOGIN_REQUEST, LOGIN_RESPONSE, LOGOUT_REQUEST, LOGOUT_RESPONSE, etc.

### `MessageVersion`

An enum that represents the version of the message. It includes:
- `V1`: Deprecated version.
- `V2`: New version with improved structure.


## Example V2 
to use the new version of the message, you can create a new message like this: 

```java
Message loginRequest = new Message(MessageType.LOGIN_REQUEST);
loginRequest.setPassword("password");
loginRequest.setUsername("username");
client.sendToServer(loginRequest);
```
if you want to add a new type of message, you can add it to the `MessageType` enum and create a new message classes wiht 
the with the ending `Request` and `Response` like this: 

```java
import java.io.Serializable;

public class FooRequest implements Serializable {
    //TODO: add fields here
    //TODO: add getters and setters
}
```

```java
import java.io.Serializable;

public class FooResponse implements Serializable {
    //TODO: add fields here
    //TODO: add getters and setters
}
```
and modify the `Message` class to handle the new message type.

### `sessionKey`
important note: evey new request message should have a SessionKey except the login request message.

some time the session is expired, so the client should send a new login request message to get a new session key.
and we can add a new message type to handle the session expired message. 

### `Copy Constructor`

its very important to add a copy constructor to the message class to avoid the shallow copy of the message object.
and for every new Request and Response message class.
