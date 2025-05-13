package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ViewModel.EditProjectViewModel;

public class EditProjectViewController {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox<String> statusComboBox;
    @FXML private ComboBox<String> statusComboBox1; // Scrum Master ComboBox

    @FXML private TableView<String> availableUsersTable;
    @FXML private TableColumn<String, String> availableUsernameColumn;

    @FXML private TableView<String> assignedUsersTable;
    @FXML private TableColumn<String, String> assignedUsernameColumn;

    @FXML private Button addUserButton;
    @FXML private Button removeUserButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private ViewHandler viewHandler;
    private EditProjectViewModel viewModel;

    private final ObservableList<String> availableUsers = FXCollections.observableArrayList();
    private final ObservableList<String> assignedUsers = FXCollections.observableArrayList();

    public void init(ViewHandler viewHandler, EditProjectViewModel viewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;

        // Setup table columns
        availableUsernameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));
        assignedUsernameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));

        // Dummy data (replace with viewModel later)
        availableUsers.addAll("dev1", "dev2", "dev3");
        assignedUsers.addAll("dev4");

        availableUsersTable.setItems(availableUsers);
        assignedUsersTable.setItems(assignedUsers);

        // Status and Scrum Master choices (can be fetched from model later)
        statusComboBox.setItems(FXCollections.observableArrayList("Planning", "Active", "Completed"));
        statusComboBox1.setItems(FXCollections.observableArrayList("scrum1", "scrum2")); // Scrum Masters

        // Action buttons
        addUserButton.setOnAction(e -> assignUser());
        removeUserButton.setOnAction(e -> unassignUser());
        saveButton.setOnAction(e -> saveProject());
        cancelButton.setOnAction(e -> viewHandler.openView("ManageProjects"));
    }

    private void assignUser() {
        String selected = availableUsersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            availableUsers.remove(selected);
            assignedUsers.add(selected);
        }
    }

    private void unassignUser() {
        String selected = assignedUsersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            assignedUsers.remove(selected);
            availableUsers.add(selected);
        }
    }

    private void saveProject() {
        // TODO: Replace with model logic
        System.out.println("Saving project with:");
        System.out.println("Title: " + titleField.getText());
        System.out.println("Description: " + descriptionField.getText());
        System.out.println("Status: " + statusComboBox.getValue());
        System.out.println("SCRUM Master: " + statusComboBox1.getValue());
        System.out.println("Start: " + startDatePicker.getValue());
        System.out.println("End: " + endDatePicker.getValue());
        System.out.println("Assigned Users: " + assignedUsers);
        viewHandler.openView("ManageProjects");
    }
}
