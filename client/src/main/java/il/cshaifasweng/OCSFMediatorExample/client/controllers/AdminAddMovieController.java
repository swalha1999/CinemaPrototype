package il.cshaifasweng.OCSFMediatorExample.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class AdminAddMovieController {

    @FXML
    private ImageView movieImageView;

    @FXML
    private TextField titleField;

    @FXML
    private TextField genreField;

    @FXML
    private TextField durationField;

    @FXML
    private DatePicker showingDatePicker;

    @FXML
    private Button importButton;

    @FXML
    private Button insertButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button clearButton;

    @FXML
    private TableView<?> moviesTable;

    @FXML
    private TableColumn<?, ?> titleColumn;

    @FXML
    private TableColumn<?, ?> genreColumn;

    @FXML
    private TableColumn<?, ?> durationColumn;

    @FXML
    private TableColumn<?, ?> showingDateColumn;

    // Add your methods for button actions here

    @FXML
    private void handleInsert() {
        // Insert movie into table
    }

    @FXML
    private void handleUpdate() {
        // Update selected movie
    }

    @FXML
    private void handleDelete() {
        // Delete selected movie
    }

    @FXML
    private void handleClear() {
        // Clear form fields
    }
}
