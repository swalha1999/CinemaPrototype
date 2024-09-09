package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.events.MessageEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.Objects;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static il.cshaifasweng.OCSFMediatorExample.client.utils.UiUtil.showNotification;

public class CinemaMain extends Application {

    private static Scene scene;
    private Client client;

    // hashtable to save the loaded fxml files to avoid loading them again
    private static final Hashtable<String, Parent> fxml = new Hashtable<String, Parent>();
    private static final Hashtable<String, Pane> fxmlScenes = new Hashtable<String, Pane>();

    public static Stage cinemaStage;

    @Override
    public void start(Stage stage) throws IOException {
        cinemaStage = stage;
        EventBus.getDefault().register(this);
        Pane movieDetails = loadFXMLPane("MovieDetails");
        //TODO: when main starts it runs initialize for "MovieDetails" and in there is a call to server, but there is no client, thus making an exception.
        // for now its turned off.
        scene = new Scene(loadFXML("Host"), 1300, 900);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) {
        scene.setRoot(loadFXML(fxml));
    }

    // this function is used to load the main scene fxml file
    public static Parent loadFXML(String fxml){

        try {
            if (!CinemaMain.fxml.containsKey(fxml)) {
                FXMLLoader fxmlLoader = new FXMLLoader(CinemaMain.class.getResource(fxml + ".fxml"));
                CinemaMain.fxml.put(fxml, fxmlLoader.load());
            }
            return CinemaMain.fxml.get(fxml);
        } catch (IOException e) {
            System.err.println("Error loading fxml file: " + fxml);
            e.printStackTrace();
        }
        return null;
    }

    // this function is used to load fxml files that are not the main scene
    // this helps to avoid reloading the scene every time we want to change the view
    public static Pane loadFXMLPane(String fxml) {
        if (CinemaMain.fxmlScenes.containsKey(fxml)) {
            return CinemaMain.fxmlScenes.get(fxml);
        }
        else {
            try {
                Pane pane = FXMLLoader.load(
                        Objects.requireNonNull(
                                CinemaMain.class.getResource(fxml + ".fxml")
                        )
                );
                CinemaMain.fxmlScenes.put(fxml, pane);
                System.out.println("Loaded fxml file at url: " + Objects.requireNonNull(CinemaMain.class.getResource(fxml + ".fxml")).getPath());
                return pane;
            } catch (IOException e) {
                System.err.println("Error loading fxml file: " + fxml);
                e.printStackTrace();
            }
            return null;
        }
    }

    public static void clearAllUICache() {
        CinemaMain.fxmlScenes.clear();
        CinemaMain.fxml.clear();
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