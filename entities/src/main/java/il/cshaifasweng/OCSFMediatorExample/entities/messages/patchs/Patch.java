package il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs;

import java.io.Serializable;

public interface Patch extends Serializable {
        String getMessage();
        Patch setMessage(String message);
}
