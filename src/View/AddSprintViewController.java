package View;

import Model.Project;
import Model.Task;
import ViewModel.AddSprintViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.time.LocalDate;

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
    private Project project;

    private final ObservableList<Task> availableTasks = FXCollections.observableArrayList();
    private final ObservableList<Task> assignedTasks = FXCollections.observableArrayList();

    public void init(ViewHandler viewHandler, AddSprintViewModel viewModel, Object obj) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.project = (Project) obj;
        // Setup columns (use getter names!)
        availableTasksColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assignedTasksColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Link lists to tables
        availableTasks.addAll(project.getBacklog());
        availableTasksTable.setItems(availableTasks);
        assignedTasksTable.setItems(assignedTasks);

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
        String name = titleField.getText();
        LocalDate localStartDate = startDatePicker.getValue();
        LocalDate localEndDate = endDatePicker.getValue();

        Date startDate = null;
        if (localStartDate != null) {
            startDate = Date.valueOf(localStartDate);
        }
        Date endDate = null;
        if (localEndDate != null) {
            endDate = Date.valueOf(localEndDate);
        }

        viewModel.addSprint(project, name, startDate, endDate);
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
