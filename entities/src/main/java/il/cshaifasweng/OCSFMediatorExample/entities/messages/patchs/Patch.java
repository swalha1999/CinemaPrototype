package il.cshaifasweng.OCSFMediatorExample.entities.messages.patchs;

import java.io.Serializable;

public interface Patch extends Serializable {
        String getMessage();
        boolean isSuccess();
        Patch setMessage(String message);
        Patch setSuccess(Boolean success);
}
