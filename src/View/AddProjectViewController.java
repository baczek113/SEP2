package View;

import Model.Employee;
import ViewModel.AddProjectViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AddProjectViewController {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private ComboBox<String> statusComboBox1; // Scrum Master selection

    @FXML private TableView<Employee> availableUsersTable;
    @FXML private TableColumn<Employee, String> availableUsernameColumn;

    @FXML private TableView<Employee> assignedUsersTable;
    @FXML private TableColumn<Employee, String> assignedUsernameColumn;

    @FXML private Button addUserButton;
    @FXML private Button removeUserButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private ViewHandler viewHandler;
    private AddProjectViewModel viewModel;

    private final ObservableList<Employee> availableUsers = FXCollections.observableArrayList();
    private final ObservableList<Employee> assignedUsers = FXCollections.observableArrayList();

    public void init(ViewHandler viewHandler, AddProjectViewModel viewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;

        // Setup table columns
        availableUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        assignedUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        // Populate table data
        availableUsersTable.setItems(availableUsers);
        assignedUsersTable.setItems(assignedUsers);

        // Populate status options
        statusComboBox.setItems(FXCollections.observableArrayList("Planning", "Active", "Finished"));
        statusComboBox1.setItems(FXCollections.observableArrayList("scrum", "dev1", "dev2")); // usernames
    }

    @FXML
    private void addUser() {
        Employee selected = availableUsersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            availableUsers.remove(selected);
            assignedUsers.add(selected);
        }
    }

    @FXML
    private void removeUser() {
        Employee selected = assignedUsersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            assignedUsers.remove(selected);
            availableUsers.add(selected);
        }
    }

    @FXML
    private void save() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String status = statusComboBox.getValue();
        String scrumMaster = statusComboBox1.getValue();
        String startDate = startDatePicker.getValue() != null ? startDatePicker.getValue().toString() : null;
        String endDate = endDatePicker.getValue() != null ? endDatePicker.getValue().toString() : null;

        if (title.isEmpty() || description.isEmpty() || status == null || scrumMaster == null || startDate == null || endDate == null) {
            showAlert("Please fill all fields.");
            return;
        }

        // TODO: call viewModel.saveProject(...) when backend is ready
        showAlert("Project saved (not really yet, dummy logic).");
        viewHandler.openView("ManageProjects");
    }

    @FXML
    private void cancel() {
        viewHandler.openView("ManageProjects");
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
