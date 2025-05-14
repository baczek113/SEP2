package View;

import ViewModel.AddSprintViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AddSprintViewController {
    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;

    @FXML private TableView<Task> availableTasksTable;
    @FXML private TableColumn<Task, String> availableTasksColumn;

    @FXML private TableView<Task> assignedTasksTable;
    @FXML private TableColumn<Task, String> assignedTasksColumn;

    @FXML private Button addTaskButton;
    @FXML private Button removeUserButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private ViewHandler viewHandler;
    private AddSprintViewModel viewModel;

    private final ObservableList<Task> availableTasks = FXCollections.observableArrayList();
    private final ObservableList<Task> assignedTasks = FXCollections.observableArrayList();

    public void init(ViewHandler viewHandler, AddSprintViewModel viewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;

        // Setup columns (use getter names!)
        availableTasksColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assignedTasksColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Link lists to tables
        availableTasksTable.setItems(availableTasks);
        assignedTasksTable.setItems(assignedTasks);

        // Dummy data
        availableTasks.addAll(
                new Task("Login Feature", "dev1", "High", "Doing"),
                new Task("Fix Logout", "dev2", "Medium", "To Do")
        );
    }

    @FXML
    private void add() {
        Task selected = availableTasksTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            availableTasks.remove(selected);
            assignedTasks.add(selected);
        }
    }

    @FXML
    private void remove() {
        Task selected = assignedTasksTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            assignedTasks.remove(selected);
            availableTasks.add(selected);
        }
    }

    @FXML
    private void save() {
        // TODO: Implement save logic with viewModel or backend
        showAlert("Sprint saved successfully.");
        viewHandler.openView("ManageSprints");
    }

    @FXML
    private void cancel() {
        viewHandler.openView("ManageSprints");
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
