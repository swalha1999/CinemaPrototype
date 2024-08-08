package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.LoginResponse;

public class LoginEvent extends LoginResponse {

    public LoginEvent(LoginResponse loginResponse) {
        super(loginResponse);
    }

}
