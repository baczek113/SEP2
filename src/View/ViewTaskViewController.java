package View;

import ViewModel.ViewTaskViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewTaskViewController {

    @FXML private Label nameLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label priorityLabel;

    @FXML private TableView<User> assignedUsersTable;
    @FXML private TableColumn<User, String> usernameColumn;

    @FXML private Button editButton;
    @FXML private Button cancelButton;

    private ViewHandler viewHandler;
    private ViewTaskViewModel viewModel;

    public void init(ViewHandler viewHandler, ViewTaskViewModel viewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;

        // TODO: Replace with values from the actual viewModel or model
        nameLabel.setText("Implement Login");
        descriptionLabel.setText("User should be able to login with credentials.");
        priorityLabel.setText("High");

        // Setup table
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        assignedUsersTable.getItems().add(new User("developer1", "Scrum Master", "Active")); // TODO: Replace with viewModel.getAssignedUsers()
    }

    @FXML
    private void edit() {
        viewHandler.openView("EditTask"); // Navigate to EditTask view
    }

    @FXML
    private void cancel() {
        viewHandler.openView("Backlog"); // Navigate back to task list
    }
}
