package View;

import Model.Employee;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ViewModel.ManageUsersViewModel;

import java.beans.PropertyChangeEvent;

public class ManageUsersViewController {

    @FXML private TableView<Employee> tableView;
    @FXML private TableColumn<Employee, String> title; // Username
    @FXML private TableColumn<Employee, String> year;  // Role
    @FXML private TableColumn<Employee, String> status;  // Status

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Button activate;
    @FXML private Button deactivate;
    @FXML private Button saveUser;
    @FXML private Button addUser;

    private ViewHandler viewHandler;
    private ManageUsersViewModel viewModel;

    public void init(ViewHandler viewHandler, ManageUsersViewModel viewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;

        viewModel.addListener(this::updateEmployHandler);
        updateEmploy();

        title.setCellValueFactory(new PropertyValueFactory<>("username"));
        year.setCellValueFactory(cellData -> {
            if (cellData.getValue().getRole() != null)
                return new ReadOnlyStringWrapper(cellData.getValue().getRole().getRole_name());
            else
                return new ReadOnlyStringWrapper("");
        });
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        ;

        roleComboBox.setItems(FXCollections.observableArrayList("Admin","Product Owner", "Scrum Master", "Developer"));

        setupSelectionListener();


    }
    private int mapRoleToId(String role) {
        return switch (role) {
            case "Admin" -> 1;
            case "Product Owner" -> 2;
            case "Scrum Master" -> 3;
            case "Developer" -> 4;
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };
    }

    @FXML
    private void add() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        int role = mapRoleToId(roleComboBox.getValue());

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Please fill in all fields.");
            return;
        }

        viewModel.addEmployee(username, password, role);
        clearFields();
    }



    @FXML
    private void save() {
        Employee selected = tableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Please select a user to update.");
            return;
        }

        String username = usernameField.getText();
        String selectedRole = roleComboBox.getValue();

        if (username.isEmpty() || selectedRole == null) {
            showAlert("Please fill in all fields.");
            return;
        }

        int roleId = mapRoleToId(selectedRole);

        viewModel.updateEmployee(selected, username, roleId);
        clearFields();
        tableView.getSelectionModel().clearSelection();
        showAlert("User updated successfully.");
    }


    @FXML
    private void activate() {
        Employee selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            viewModel.activateEmployee(selected);
            showAlert("User activated successfully.");
            clearFields();
        } else {
            showAlert("Please select a user to activate.");
        }
    }
    @FXML
    private void deactivate() {
        Employee selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            viewModel.deactivateEmployee(selected);
            showAlert("User deactivated successfully.");
            clearFields();
        } else {
            showAlert("Please select a user to deactivate.");
        }
    }



    private void setupSelectionListener() {
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                usernameField.setText(newSelection.getUsername());
                passwordField.clear(); // password can't be fetched, so we leave it empty
                roleComboBox.setValue(newSelection.getRole().getRole_name()); // update based on role name
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
    private void updateEmploy()
    {
        tableView.setItems(viewModel.getEmployees());
    }

    private void updateEmployHandler(PropertyChangeEvent event)
    {
        updateEmploy();
    }

}
