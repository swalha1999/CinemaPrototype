package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.LogoutResponse;

public class LogoutEvent extends LogoutResponse {
    public LogoutEvent(LogoutResponse logoutResponse) {
        super(logoutResponse);
    }
}
