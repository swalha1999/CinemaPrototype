package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serializable;

public class MakeAdminRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String request;
    public MakeAdminRequest(String request) {
        this.request = request;
    }
    public String getRequest() {
        return request;
    }
    public void setRequest(String request) {
        this.request = request;
    }
    public String toString() {
        return request;
    }
}
