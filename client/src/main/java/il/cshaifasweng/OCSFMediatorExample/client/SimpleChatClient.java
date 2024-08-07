package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.events.MessageEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * JavaFX App
 */
public class SimpleChatClient extends Application {

    private static Scene scene;
    private SimpleClient client;

    // hashtable to save the loaded fxml files to avoid loading them again
    private static final Hashtable<String, Parent> fxmls = new Hashtable<String, Parent>();

    @Override
    public void start(Stage stage) throws IOException {
        EventBus.getDefault().register(this);
        scene = new Scene(loadFXML("host"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        if (fxmls.containsKey(fxml)) {
            scene.setRoot(fxmls.get(fxml));
        }
        else {
            fxmls.put(fxml, loadFXML(fxml));
            scene.setRoot(fxmls.get(fxml));
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SimpleChatClient.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }



    @Override
    public void stop() throws Exception {
        // TODO Auto-generated method stub
        EventBus.getDefault().unregister(this);
        super.stop();
    }


    @Subscribe
    public void onMessageEvent(MessageEvent message) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION,
                    String.format("Message:\nId: %d\nData: %s\nTimestamp: %s\n",
                            message.getMessage().getId(),
                            message.getMessage().getMessage(),
                            message.getMessage().getTimeStamp().format(dtf))
            );
            alert.setTitle("new message");
            alert.setHeaderText("New Message:");
            alert.show();
        });
    }


    public static void main(String[] args) {
        launch();
    }

}