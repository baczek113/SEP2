package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ViewModel.ManageUsersViewModel;

public class ManageUsersViewController {

    @FXML private TableView<User> tableView;
    @FXML private TableColumn<User, String> title; // Username
    @FXML private TableColumn<User, String> year;  // Role
    @FXML private TableColumn<User, String> status;  // Status

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Button logout;
    @FXML private Button deactivate;

    private ViewHandler viewHandler;
    private ManageUsersViewModel viewModel;

    private final ObservableList<User> dummyUsers = FXCollections.observableArrayList();

    public void init(ViewHandler viewHandler, ManageUsersViewModel viewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;

        // Bind table columns
        title.setCellValueFactory(new PropertyValueFactory<>("username"));
        year.setCellValueFactory(new PropertyValueFactory<>("role"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Add dummy data
        dummyUsers.add(new User("admin", "Scrum Master", "Active"));
        dummyUsers.add(new User("dev1", "Developer", "Active"));

        tableView.setItems(dummyUsers);

        roleComboBox.setItems(FXCollections.observableArrayList("Developer", "Scrum Master", "Product Owner"));

        setupSelectionListener();
    }

    @FXML
    private void add() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            showAlert("Please fill in all fields.");
            return;
        }

        dummyUsers.add(new User(username, role, "Active"));
        clearFields();
    }

    @FXML
    private void removeUser() {
        User selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            dummyUsers.remove(selected);
        } else {
            showAlert("Please select a user to remove.");
        }
    }

    @FXML
    private void save() {
        User selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setUsername(usernameField.getText());
            selected.setRole(roleComboBox.getValue());
            tableView.refresh();
            showAlert("User updated successfully.");
        } else {
            showAlert("Please select a user to update.");
        }
    }

    @FXML
    private void deactivate() {
        User selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setStatus("Inactive");
            tableView.refresh();
            showAlert("User deactivated successfully.");
        } else {
            showAlert("Please select a user to deactivate.");
        }
    }
    @FXML
    private void activate() {
        User selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setStatus("Active");
            tableView.refresh();
            showAlert("User activated successfully.");
        } else {
            showAlert("Please select a user to deactivate.");
        }
    }

    @FXML
    private void logOut() {
        viewHandler.openView("Login");
    }

    private void setupSelectionListener() {
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                usernameField.setText(newSelection.getUsername());
                roleComboBox.setValue(newSelection.getRole());
            }
        });
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        roleComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
