package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serializable;

public interface Request extends Serializable {

    String getSessionKey();
    Request setSessionKey(String sessionKey);

    String getUsername();
    Request setUsername(String username);

    int getUserId();
    Request setUserId(int userId);

}
