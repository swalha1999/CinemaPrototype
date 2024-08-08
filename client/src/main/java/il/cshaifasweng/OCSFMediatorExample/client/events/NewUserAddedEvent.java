package il.cshaifasweng.OCSFMediatorExample.client.events;

import il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs.NewUserAddedPatch;

public class NewUserAddedEvent extends NewUserAddedPatch {
    public NewUserAddedEvent(NewUserAddedPatch patch) {
        super(patch);
    }
}
