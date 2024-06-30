/**
 * Sample Skeleton for 'primary.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import static il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient.setRoot;

public class PrimaryController {

    @FXML // fx:id="AddBtn"
    private Button AddBtn; // Value injected by FXMLLoader

    @FXML // fx:id="DeleteBtn"
    private Button DeleteBtn; // Value injected by FXMLLoader

    @FXML // fx:id="UpdateBtn"
    private Button UpdateBtn; // Value injected by FXMLLoader

    @FXML // fx:id="col_email"
    private TableColumn<?, ?> col_email; // Value injected by FXMLLoader

    @FXML // fx:id="col_username"
    private TableColumn<?, ?> col_username; // Value injected by FXMLLoader

    @FXML // fx:id="table_users"
    private TableView<?> table_users; // Value injected by FXMLLoader

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
        AddBtn.setOnAction(event -> EventBus.getDefault().post(new MessageEvent("Hello from EventBus!"))); // replace with server calls
        DeleteBtn.setOnAction(event -> EventBus.getDefault().postSticky(new StickyMessageEvent("Sticky Hello from EventBus!"))); // replace with server calls
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        System.out.println(event.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStickyMessageEvent(StickyMessageEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
    }

    public void cleanup() {
        EventBus.getDefault().unregister(this);
    }

    @FXML
    void AddMovies(ActionEvent event) throws IOException {

    }

    @FXML
    void Delete(ActionEvent event) {

    }

    @FXML
    void Update(ActionEvent event) throws IOException {
        
        setRoot("secondary");
    }

    @FXML
    void getSelected(MouseEvent event) {

    }

}
