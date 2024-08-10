package il.cshaifasweng.OCSFMediatorExample.client.utils;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class NotificationPane {

    private final StackPane parentPane;

    public NotificationPane(StackPane parentPane) {
        this.parentPane = parentPane;
    }

    public void showNotification(String message, boolean isSuccess) {
        Platform.runLater(() -> {
            Label notificationLabel = new Label(message);
            notificationLabel.setStyle(isSuccess ?
                    "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-padding: 10; -fx-border-radius: 15; -fx-background-radius: 15;" :
                    "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 10; -fx-border-radius: 15; -fx-background-radius: 15;");

            // Adjust the position to be lower from the top
            StackPane.setAlignment(notificationLabel, javafx.geometry.Pos.TOP_CENTER);
            StackPane.setMargin(notificationLabel, new Insets(30, 0, 0, 0)); // Adjust the margin as needed

            // Add fade-in transition
            FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(0.5), notificationLabel);
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);

            // Add fade-out transition
            FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(5), notificationLabel);
            fadeOutTransition.setFromValue(1.0);
            fadeOutTransition.setToValue(0.0);
            fadeOutTransition.setOnFinished(event -> parentPane.getChildren().remove(notificationLabel));

            parentPane.getChildren().add(notificationLabel);
            fadeInTransition.play();
            fadeInTransition.setOnFinished(event -> fadeOutTransition.play()); // Start fade-out after fade-in
        });
    }
}
