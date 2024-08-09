package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs.RemoveUserPatch;

public class RemoveUserEvent extends RemoveUserPatch {
    public RemoveUserEvent(RemoveUserPatch removeUserPatch) {
        super(removeUserPatch);
    }
}
