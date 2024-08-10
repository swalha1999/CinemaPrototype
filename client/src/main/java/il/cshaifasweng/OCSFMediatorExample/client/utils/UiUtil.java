package il.cshaifasweng.OCSFMediatorExample.client.utils;

import il.cshaifasweng.OCSFMediatorExample.client.events.ShowNotificationEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;

public class UiUtil {
    public static void showInfo(String message) {
        System.out.println("INFO: " + message);
    }

    public static void showError(String message) {
        System.out.println("ERROR: " + message);
    }

    public static void showWarning(String message) {
        System.out.println("WARNING: " + message);
    }

    public static double getLabelWidth(Label movieTitle) {
        Text text = new Text(movieTitle.getText());
        text.setFont(movieTitle.getFont());
        return text.getLayoutBounds().getWidth();
    }

    public static void showSideUI(String UIName){
        EventBus.getDefault().post(new ShowSideUIEvent(UIName));
    }

    public static void showNotification(String message, boolean Success){
        EventBus.getDefault().post(new ShowNotificationEvent(message, Success));
    }

}
