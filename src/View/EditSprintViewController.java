package View;

import ViewModel.EditSprintViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class EditSprintViewController {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;

    @FXML private TableView<String> availableUsersTable;
    @FXML private TableColumn<String, String> availableUsernameColumn;

    @FXML private TableView<String> assignedUsersTable;
    @FXML private TableColumn<String, String> assignedUsernameColumn;

    @FXML private Button addUserButton;
    @FXML private Button removeUserButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private ViewHandler viewHandler;
    private EditSprintViewModel viewModel;

    private final ObservableList<String> availableTasks = FXCollections.observableArrayList();
    private final ObservableList<String> assignedTasks = FXCollections.observableArrayList();

    public void init(ViewHandler viewHandler, EditSprintViewModel viewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;

        // Set up table columns
        availableUsernameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));
        assignedUsernameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));

        // Dummy task data — TODO: replace with real data from model
        availableTasks.addAll("Login page", "Database schema", "API setup", "Unit tests");

        availableUsersTable.setItems(availableTasks);
        assignedUsersTable.setItems(assignedTasks);

        // Action listeners
        addUserButton.setOnAction(e -> assignTask());
        removeUserButton.setOnAction(e -> unassignTask());
        saveButton.setOnAction(e -> saveSprint());
        cancelButton.setOnAction(e -> viewHandler.openView("ManageSprints"));
    }

    private void assignTask() {
        String selected = availableUsersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            availableTasks.remove(selected);
            assignedTasks.add(selected);
        }
    }

    private void unassignTask() {
        String selected = assignedUsersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            assignedTasks.remove(selected);
            availableTasks.add(selected);
        }
    }

    private void saveSprint() {
        // TODO: Replace this with saving to model
        System.out.println("Saving sprint:");
        System.out.println("Title: " + titleField.getText());
        System.out.println("Description: " + descriptionField.getText());
        System.out.println("Start: " + startDatePicker.getValue());
        System.out.println("End: " + endDatePicker.getValue());
        System.out.println("Assigned tasks: " + assignedTasks);
        viewHandler.openView("ManageSprints");
    }
}
