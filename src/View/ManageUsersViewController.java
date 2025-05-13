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

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Button logout;

    private ViewHandler viewHandler;
    private ManageUsersViewModel viewModel;

    // Temporary dummy data (to be replaced with real model later)
    private final ObservableList<User> dummyUsers = FXCollections.observableArrayList();

    public void init(ViewHandler viewHandler, ManageUsersViewModel viewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;

        // Bind table columns to User properties
        title.setCellValueFactory(new PropertyValueFactory<>("username"));
        year.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Set table data to dummy list
        tableView.setItems(dummyUsers); // TODO: Replace with viewModel.getUsers()

        // Populate roles in combo box
        roleComboBox.setItems(FXCollections.observableArrayList("Developer", "Scrum Master", "Product Owner"));

        // Add sample users — for testing only
        dummyUsers.add(new User("admin", "Scrum Master"));
        dummyUsers.add(new User("dev1", "Developer"));
        setupSelectionListener();
    }

    @FXML
    private void add() {
        String username = usernameField.getText();
        String password = passwordField.getText(); // Will be used later with model
        String role = roleComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            showAlert("Please fill in all fields.");
            return;
        }

        // Add to dummy list — TODO: Replace with viewModel.addUser(username, password, role)
        dummyUsers.add(new User(username, role));
        clearFields();
    }

    @FXML
    private void removeUser() {
        User selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            dummyUsers.remove(selected); // TODO: Replace with viewModel.removeUser(selected)
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
    private void setupSelectionListener() {
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                usernameField.setText(newSelection.getUsername());
                roleComboBox.setValue(newSelection.getRole());
            }
        });
    }
    @FXML
    private void logOut ()
    {
        viewHandler.openView("Login");
    }

}
