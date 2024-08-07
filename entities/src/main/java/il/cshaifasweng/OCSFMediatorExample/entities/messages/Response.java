package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serializable;

public interface Response extends Serializable {

    String getMessage();
    Response setMessage(String message);
    boolean isSuccess();

}
