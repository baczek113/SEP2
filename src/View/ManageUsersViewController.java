package View;

import DataModel.Employee;
import javafx.application.Platform;
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
    @FXML private TableColumn<Employee, String> title;  // Username
    @FXML private TableColumn<Employee, String> year;   // Role
    @FXML private TableColumn<Employee, String> status; // Status

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Label passwordLabel;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Button activate;
    @FXML private Button deactivate;
    @FXML private Button saveUser;
    @FXML private Button addUser;
    @FXML private Button logoutButton;

    private boolean isEditMode = false;
    private Employee editingUser = null;

    private ViewHandler viewHandler;
    private ManageUsersViewModel viewModel;

    public void init(ViewHandler viewHandler, ManageUsersViewModel viewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;

        viewModel.addListener(this::updateEmployHandler);
        updateEmployee();

        logoutButton.setOnAction(e -> logOut());

        title.setCellValueFactory(new PropertyValueFactory<>("username"));
        year.setCellValueFactory(cellData -> {
            String roleName = "";
            if (cellData.getValue().getRole() != null) {
                switch (cellData.getValue().getRole().getRole_name().toLowerCase()) {
                    case "product_owner" -> roleName = "Product Owner";
                    case "scrum_master" -> roleName = "Scrum Master";
                    case "developer" -> roleName = "Developer";
                    default -> roleName = cellData.getValue().getRole().getRole_name();
                }
            }
            return new ReadOnlyStringWrapper(roleName);
        });
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        roleComboBox.setItems(FXCollections.observableArrayList("Product Owner", "Scrum Master", "Developer"));

        setupSelectionListener();
    }

    @FXML
    private void add() {
        clearFields();
        passwordField.setVisible(true);
        passwordLabel.setVisible(true);
        passwordField.setEditable(true);
        isEditMode = false;
        editingUser = null;
    }

    @FXML
    private void edit() {
        Employee selected = tableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Please select a user to edit.");
            return;
        }

        usernameField.setText(selected.getUsername());
        passwordField.setVisible(false);
        passwordLabel.setVisible(false);

        if (selected.getRole() != null) {
            String roleName = selected.getRole().getRole_name();
            switch (roleName.toLowerCase()) {
                case "product_owner" -> roleComboBox.setValue("Product Owner");
                case "scrum_master" -> roleComboBox.setValue("Scrum Master");
                case "developer" -> roleComboBox.setValue("Developer");
                default -> roleComboBox.setValue(roleName);
            }
        } else {
            roleComboBox.setValue(null);
        }

        editingUser = selected;
        isEditMode = true;
    }

    @FXML
    private void save() {
        String username = usernameField.getText().trim();
        String selectedRole = roleComboBox.getValue();
        String password = passwordField.getText().trim();

        String error = viewModel.validateInput(username, password, selectedRole, isEditMode, editingUser);
        if (error != null) {
            showAlert(error);
            return;
        }

        int roleId = mapRoleToId(selectedRole);

        if (isEditMode && editingUser != null) {
            viewModel.updateEmployee(editingUser, username, roleId);
        } else {
            viewModel.addEmployee(username, password, roleId);
        }

        clearFields();
        tableView.getSelectionModel().clearSelection();
        isEditMode = false;
        editingUser = null;
    }

    @FXML
    private void activate() {
        Employee selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            viewModel.activateEmployee(selected);
            clearFields();
        }
    }

    @FXML
    private void deactivate() {
        Employee selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            viewModel.deactivateEmployee(selected);
            clearFields();
        }
    }

    private void setupSelectionListener() {
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            // Optional: add live preview or enable/disable buttons
        });
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        passwordField.setEditable(true);
        roleComboBox.getSelectionModel().clearSelection();
        isEditMode = false;
        editingUser = null;
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private int mapRoleToId(String role) {
        if (role == null) {
            throw new IllegalArgumentException("Role must be selected.");
        }
        return switch (role.toLowerCase()) {
            case "product owner", "product_owner" -> 2;
            case "scrum master", "scrum_master" -> 3;
            case "developer" -> 4;
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };
    }

    private void updateEmployee() {
        ObservableList<Employee> allEmployees = viewModel.getEmployees();
        ObservableList<Employee> withoutAdmin = allEmployees.filtered(employee ->
                employee.getRole() != null && !employee.getRole().getRole_name().equalsIgnoreCase("admin"));
        tableView.setItems(withoutAdmin);
    }

    private void updateEmployHandler(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("employees")) {
            updateEmployee();
        } else if (event.getPropertyName().equals("cannotDeactivateEmployee") ||
                event.getPropertyName().equals("cannotChangeRole")) {
            Platform.runLater(() -> showAlert("This employee is assigned to an active project"));
        }
    }

    private void logOut() {
        viewModel.logOut();
        viewHandler.openView("Login");
    }
}