/**
 * Sample Skeleton for 'BranchManagerMain.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import static il.cshaifasweng.OCSFMediatorExample.client.CinemaMain.loadFXMLPane;

public class BranchManagerMain {

    @FXML // fx:id="AdminInbox"
    private Button AdminInbox; // Value injected by FXMLLoader

    @FXML // fx:id="CustomersBtn"
    private Button CustomersBtn; // Value injected by FXMLLoader

    @FXML // fx:id="DashBoardBtn"
    private Button DashBoardBtn; // Value injected by FXMLLoader

    @FXML // fx:id="LogOutBtn"
    private Button LogOutBtn; // Value injected by FXMLLoader

    @FXML // fx:id="MoviesBtn"
    private Button MoviesBtn; // Value injected by FXMLLoader

    @FXML // fx:id="ScreeningsBtn"
    private Button ScreeningsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="mainPane"
    private BorderPane mainPane; // Value injected by FXMLLoader

    @FXML // fx:id="rootPane"
    private BorderPane rootPane; // Value injected by FXMLLoader

    @FXML // fx:id="stackPaneMain"
    private StackPane stackPaneMain; // Value injected by FXMLLoader

    @FXML // fx:id="user"
    private Label user; // Value injected by FXMLLoader

    @FXML
    void logOut(ActionEvent event) {

    }
    @FXML
    void showDashBoard(ActionEvent event) {
        loadUI("BranchManagerDashBoard.fxml");
    }
    public void loadUI(String ui) {
        Platform.runLater(() ->
                {
                    mainPane.setCenter(loadFXMLPane(ui));
                }
        );

    }

}
