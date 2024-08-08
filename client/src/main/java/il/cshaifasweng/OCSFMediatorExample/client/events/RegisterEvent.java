package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.RegisterResponse;

public class RegisterEvent extends RegisterResponse {

    public RegisterEvent(RegisterResponse registerResponse) {
        super(registerResponse);
    }

}
