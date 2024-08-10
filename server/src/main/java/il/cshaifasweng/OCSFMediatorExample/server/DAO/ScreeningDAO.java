package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import org.hibernate.Session;

public class ScreeningDAO {

    private Session session;

    public ScreeningDAO(Session session) {
        this.session = session;
    }



}
