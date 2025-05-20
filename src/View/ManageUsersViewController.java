package View;

import Model.Employee;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
                    default -> roleName = cellData.getValue().getRole().getRole_name(); // fallback
                }
            }
            return new ReadOnlyStringWrapper(roleName);
        });
        status.setCellValueFactory(new PropertyValueFactory<>("status"));


        roleComboBox.setItems(FXCollections.observableArrayList("Product Owner", "Scrum Master", "Developer"));

        setupSelectionListener();


    }
    private int mapRoleToId(String role) {
        return switch (role.toLowerCase()) {
            case "product owner", "product_owner" -> 2;
            case "scrum master", "scrum_master" -> 3;
            case "developer" -> 4;
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };
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
        roleComboBox.setValue(selected.getRole().getRole_name());

        editingUser = selected;
        isEditMode = true;
    }



    @FXML
    private void add() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        int role = mapRoleToId(roleComboBox.getValue());

        passwordField.setVisible(true);
        passwordLabel.setVisible(true);

        if (username != null && password != null)
        {
            viewModel.addEmployee(username, password, role);
            clearFields();
        }
    }



    @FXML
    private void save() {
        String username = usernameField.getText();
        String selectedRole = roleComboBox.getValue();

        if (username.isEmpty() || selectedRole == null) {
            showAlert("Please fill in all fields.");
            return;
        }

        int roleId = mapRoleToId(selectedRole);

        if (isEditMode && editingUser != null) {
            // Update logic
            viewModel.updateEmployee(editingUser, username, roleId);
        } else {
            // Add logic
            String password = passwordField.getText();
            if (password.isEmpty()) {
                showAlert("Please enter a password.");
                return;
            }
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
    private void updateEmployee()
    {
        ObservableList<Employee> allEmployees = viewModel.getEmployees();

        ObservableList<Employee> withoutAdmin = allEmployees.filtered(employee -> employee.getRole() != null
                && !employee.getRole().getRole_name().equals("admin"));

        tableView.setItems(withoutAdmin);
    }

    private void updateEmployHandler(PropertyChangeEvent event)
    {
        if(event.getPropertyName().equals("employees")) {
            updateEmployee();
        }
        else if(event.getPropertyName().equals("cannotDeactivateEmployee"))
        {
            Platform.runLater(() -> showAlert("This employee is assigned to an active project"));
        }
    }

    private void logOut()
    {
        viewModel.logOut();
        viewHandler.openView("Login");
    }
}
