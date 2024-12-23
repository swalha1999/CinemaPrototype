package il.cshaifasweng.OCSFMediatorExample.client.utils;

import il.cshaifasweng.OCSFMediatorExample.client.CinemaMain;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowNotificationEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayInputStream;
import java.util.Objects;

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

    public static void showSideUI(String UIName, Object dataForPage){
        EventBus.getDefault().post(new ShowSideUIEvent(UIName, dataForPage));
    }

    public static void showSideUI(String UIName, Object dataForPage, Object dataForPage2){
        EventBus.getDefault().post(new ShowSideUIEvent(UIName, dataForPage, dataForPage2));
    }

    public static void showNotification(String message, boolean Success){
        EventBus.getDefault().post(new ShowNotificationEvent(message, Success));
    }

    public static Image getImage(String url) throws NullPointerException {
        if (url == null || url.isEmpty() || CinemaMain.class.getResource(url) == null) {
            throw new NullPointerException("Image not found");
        }else {
            return new Image(Objects.requireNonNull(CinemaMain.class.getResourceAsStream(url)));
        }
    }

    public static Image getImageFromBytes(byte[] imageBytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        return new Image(bis);
    }
}
