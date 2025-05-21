package View;

import DataModel.Employee;
import DataModel.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ViewModel.ViewProjectViewModel;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewProjectViewController {

    @FXML private Label titleLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label statusLabel;
    @FXML private Label scrumMasterLabel;
    @FXML private Label startDateLabel;
    @FXML private Label endDateLabel;

    @FXML private TableView<Employee> assignedUsersTable;
    @FXML private TableColumn<String, String> assignedUsernameColumn;

    @FXML private Button backButton;
    @FXML private Button editButton;
    @FXML private  Label createdBy;

    private ViewHandler viewHandler;
    private ViewProjectViewModel viewModel;
    private Project selectedProject;

    private final ObservableList<Employee> assignedUsers = FXCollections.observableArrayList();

    public void init(ViewHandler viewHandler, ViewProjectViewModel viewModel, Object obj) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.selectedProject = (Project) obj;

        titleLabel.setText(selectedProject.getName());
        descriptionLabel.setText(selectedProject.getDescription());
        statusLabel.setText(selectedProject.getStatus());
        scrumMasterLabel.setText(selectedProject.getScrum_master().getUsername());
        startDateLabel.setText(selectedProject.getStartDate());
        endDateLabel.setText(selectedProject.getEndDate());
        createdBy.setText(selectedProject.getCreated_by().getUsername());

        // Set table column
        assignedUsernameColumn.setText("Employees");
        assignedUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        for(Employee employee : selectedProject.getEmployees()) {
            if(employee.getRole().getRole_name().equals("developer")) {
                assignedUsers.add(employee);
            }
        }
        assignedUsersTable.setItems(assignedUsers);

        String roleName = viewModel.getLogedEmployee().getRole().getRole_name();
        if (roleName.equals("developer") || roleName.equals("scrum_master"))
        {
            editButton.setVisible(false);
        }
    }

    @FXML
    private void backToProject() {
        viewHandler.openView("ManageProjects");
    }
    @FXML
    private void editProject() {
        viewHandler.openView("EditProject", selectedProject);
    }


}
