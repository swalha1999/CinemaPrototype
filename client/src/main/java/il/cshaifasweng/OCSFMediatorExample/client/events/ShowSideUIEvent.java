package il.cshaifasweng.OCSFMediatorExample.client.events;

public class ShowSideUIEvent {
    private final String UIName;
    private final Object dataForPage;

    public ShowSideUIEvent(String UIName) {
        this.UIName = UIName;
        this.dataForPage = null;
    }

    public ShowSideUIEvent(String UIName, Object dataForPage) {
        this.UIName = UIName;
        this.dataForPage = dataForPage;
    }

    public String getUIName() {
        return UIName;
    }

    public Object getDataForPage() {
        return dataForPage;
    }

    public String toString() {
        return "ShowSideUIEvent{" +
                "UIName='" + UIName + '\'' +
                ", dataForPage=" + dataForPage +
                '}';
    }

}
