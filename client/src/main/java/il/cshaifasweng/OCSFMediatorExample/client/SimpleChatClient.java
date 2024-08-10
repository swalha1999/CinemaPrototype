package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.events.MessageEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowNotificationEvent;
import il.cshaifasweng.OCSFMediatorExample.client.events.ShowSideUIEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.Objects;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showNotification;

public class SimpleChatClient extends Application {

    private static Scene scene;
    private SimpleClient client;

    // hashtable to save the loaded fxml files to avoid loading them again
    private static final Hashtable<String, Parent> fxml = new Hashtable<String, Parent>();
    private static final Hashtable<String, Pane> fxmlScenes = new Hashtable<String, Pane>();

    @Override
    public void start(Stage stage) throws IOException {
        EventBus.getDefault().register(this);
        Pane movieDetails = loadFXMLPane("MovieDetails");
        scene = new Scene(loadFXML("host"), 1200, 900);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) {
        scene.setRoot(loadFXML(fxml));
    }

    // this function is used to load the main scene fxml file
    public static Parent loadFXML(String fxml){

        try {
            if (!SimpleChatClient.fxml.containsKey(fxml)) {
                FXMLLoader fxmlLoader = new FXMLLoader(SimpleChatClient.class.getResource(fxml + ".fxml"));
                SimpleChatClient.fxml.put(fxml, fxmlLoader.load());
            }
            return SimpleChatClient.fxml.get(fxml);
        } catch (IOException e) {
            System.err.println("Error loading fxml file: " + fxml);
            e.printStackTrace();
        }
        return null;
    }

    // this function is used to load fxml files that are not the main scene
    // this helps to avoid reloading the scene every time we want to change the view
    public static Pane loadFXMLPane(String fxml) {
        if (SimpleChatClient.fxmlScenes.containsKey(fxml)) {
            return SimpleChatClient.fxmlScenes.get(fxml);
        }
        else {
            try {
                Pane pane = FXMLLoader.load(
                        Objects.requireNonNull(
                                SimpleChatClient.class.getResource(fxml + ".fxml")
                        )
                );
                SimpleChatClient.fxmlScenes.put(fxml, pane);
                System.out.println("Loaded fxml file at url: " + Objects.requireNonNull(SimpleChatClient.class.getResource(fxml + ".fxml")).getPath());
                return pane;
            } catch (IOException e) {
                System.err.println("Error loading fxml file: " + fxml);
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void stop() throws Exception {
        // TODO Auto-generated method stub
        EventBus.getDefault().unregister(this);
        super.stop();
    }


    @Subscribe
    public void onMessageEvent(MessageEvent messageEvent) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        Platform.runLater(() -> {
            showNotification( messageEvent.getMessage().getMessage(), messageEvent.getMessage().isSuccess());
        });
    }


    public static void main(String[] args) {
        launch();
    }

}