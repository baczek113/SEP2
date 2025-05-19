package View;

import Model.Employee;
import Model.Project;
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

    private ViewHandler viewHandler;
    private ViewProjectViewModel viewModel;
    private Project selectedProject;

    private final ObservableList<Employee> assignedUsers = FXCollections.observableArrayList();

    public void init(ViewHandler viewHandler, ViewProjectViewModel viewModel, Object obj) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.selectedProject = (Project) obj;

        // Fill dummy data for now — TODO: Replace with viewModel.getSelectedProject()
        titleLabel.setText(selectedProject.getName());
        descriptionLabel.setText(selectedProject.getDescription());
        statusLabel.setText(selectedProject.getStatus());
        scrumMasterLabel.setText(selectedProject.getScrum_master().getUsername());
        startDateLabel.setText(selectedProject.getStartDate());
        endDateLabel.setText(selectedProject.getEndDate());

        // Set table column
        assignedUsernameColumn.setText("Users");
        assignedUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        assignedUsers.addAll(selectedProject.getEmployees());
        assignedUsersTable.setItems(assignedUsers);
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
