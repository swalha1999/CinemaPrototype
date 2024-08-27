package il.cshaifasweng.OCSFMediatorExample.client.events;

public class ShowSideUIEvent {
    private final String UIName;
    private final Object firstObj;
    private final Object secondObj;

    public ShowSideUIEvent(String UIName) {
        this.UIName = UIName;
        this.firstObj = null;
        this.secondObj = null;
    }

    public ShowSideUIEvent(String UIName, Object dataForPage) {
        this.UIName = UIName;
        this.firstObj = dataForPage;
        this.secondObj = null;
    }

    public ShowSideUIEvent(String UIName, Object dataForPage, Object secondObj) {
        this.UIName = UIName;
        this.firstObj = dataForPage;
        this.secondObj = secondObj;
    }

    public String getUIName() {
        return UIName;
    }

    public Object getFirstObj() {
        return firstObj;
    }

    public Object getSecondObj() {
        return secondObj;
    }

}
