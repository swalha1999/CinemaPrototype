package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.GetAllUsersResponse;

public class GetAllUsersEvent extends GetAllUsersResponse {

    public GetAllUsersEvent(GetAllUsersResponse getAllUsersEvent) {
        super(getAllUsersEvent);
    }

}