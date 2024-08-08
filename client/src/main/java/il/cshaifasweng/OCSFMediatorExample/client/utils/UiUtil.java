package il.cshaifasweng.OCSFMediatorExample.client.utils;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

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
}
