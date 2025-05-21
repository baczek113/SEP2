package View;

import DataModel.Employee;
import DataModel.EmployeeList;
import ViewModel.AddProjectViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import java.util.List;

public class AddProjectViewController {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private ComboBox<Employee> statusComboBox1;

    @FXML private TableView<Employee> availableUsersTable;
    @FXML private TableColumn<String, String> availableUsernameColumn;

    @FXML private TableView<Employee> assignedUsersTable;
    @FXML private TableColumn<String, String> assignedUsernameColumn;

    private ObservableList<Employee> availableUsers = FXCollections.observableArrayList();
    private ObservableList<Employee> assignedUsers = FXCollections.observableArrayList();

    @FXML private Button addUserButton;
    @FXML private Button removeUserButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private ViewHandler viewHandler;
    private AddProjectViewModel viewModel;

    public void init(ViewHandler viewHandler, AddProjectViewModel viewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;

        // Setup table columns
        availableUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        assignedUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        List<Employee> employees = viewModel.getEmployees();
        List<Employee> scrum_masters = new EmployeeList();

        for(Employee employee : employees) {
            if(employee.getRole().getRole_name().equals("developer"))
            {
                availableUsers.add(employee);
            }
            else if(employee.getRole().getRole_name().equals("scrum_master"))
            {
                scrum_masters.add(employee);
            }
        }
        statusComboBox1.getItems().addAll(scrum_masters);
        availableUsersTable.setItems(availableUsers);
        assignedUsersTable.setItems(assignedUsers);

        statusComboBox1.setConverter(new StringConverter<Employee>() {
            @Override
            public String toString(Employee object) {
                if(object != null) {
                    return object.getUsername();
                }
                return "";
            }
            @Override
            public Employee fromString(String string) {
                return null;
            }
        });
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
        Employee scrumMaster = statusComboBox1.getValue();

        if (title.isEmpty() || description.isEmpty() || scrumMaster == null) {
            showAlert("Please fill all fields.");
            return;
        }

        EmployeeList assignedEmployees = new EmployeeList();
        for(Employee employee : assignedUsers) {
            assignedEmployees.add(employee);
        }

        assignedEmployees.add(scrumMaster);
        assignedEmployees.add(viewModel.getLoggedEmployee());

        viewModel.saveProject(scrumMaster, title, description, assignedEmployees);

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
