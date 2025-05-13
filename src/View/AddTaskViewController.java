package View;


import ViewModel.AddTaskViewModel; // Replace with your actual ViewModel when implemented
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddTaskViewController {

    @FXML private TextField titleField;
    @FXML private TextField titleField1; // This is used as a description field
    @FXML private ComboBox<String> priorityComboBox;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private ViewHandler viewHandler;
    private AddTaskViewModel viewModel; // Replace with null or dummy if you don't use it yet

    public void init(ViewHandler viewHandler, AddTaskViewModel viewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;


        priorityComboBox.setItems(FXCollections.observableArrayList("Low", "Medium", "High"));
    }

    @FXML
    private void save() {
        String title = titleField.getText();
        String description = titleField1.getText();
        String priority = priorityComboBox.getValue();

        if (title.isEmpty() || description.isEmpty() || priority == null) {
            showAlert("Please fill in all fields.");
            return;
        }

        // TODO: Send this to the ViewModel / model manager
        System.out.println("Saving Task: " + title + ", " + description + ", " + priority);

        // Optionally reset or go back
        viewHandler.openView("Backlog");
    }

    @FXML
    private void cancle() {
        viewHandler.openView("Backlog");
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
