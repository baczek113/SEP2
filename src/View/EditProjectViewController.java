package View;

import DataModel.Employee;
import DataModel.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ViewModel.EditProjectViewModel;

import java.time.LocalDate;

public class EditProjectViewController {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private Label startDateLabel;
    @FXML private Label endDateLabel;
    @FXML private Label statusText;
    @FXML private Button startEndProjectButton;

    @FXML private TableView<Employee> availableUsersTable;
    @FXML private TableColumn<String, String> availableUsernameColumn;

    @FXML private TableView<Employee> assignedUsersTable;
    @FXML private TableColumn<String, String> assignedUsernameColumn;

    @FXML private Button addUserButton;
    @FXML private Button removeUserButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private ViewHandler viewHandler;
    private EditProjectViewModel viewModel;
    private Project selectedProject;
    private String statusChange;

    private final ObservableList<Employee> availableUsers = FXCollections.observableArrayList();
    private final ObservableList<Employee> assignedUsers = FXCollections.observableArrayList();

    public void init(ViewHandler viewHandler, EditProjectViewModel viewModel, Object obj) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.selectedProject = (Project) obj;
        this.statusChange = "none";
        viewModel.setProject(selectedProject);

        this.titleField.setText(selectedProject.getName());
        this.descriptionField.setText(selectedProject.getDescription());

        // Setup table columns
        availableUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        assignedUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        for(Employee employee : viewModel.getEmployees()) {
            if(employee.getRole().getRole_name().equals("developer") && employee.getStatus().equals("active")) {
                if (selectedProject.getEmployees().get(employee.getEmployee_id()) != null) {
                    assignedUsers.add(employee);
                } else {
                    availableUsers.add(employee);
                }
            }
        }

        availableUsersTable.setItems(availableUsers);
        assignedUsersTable.setItems(assignedUsers);

        // Action buttons
        addUserButton.setOnAction(e -> assignUser());
        removeUserButton.setOnAction(e -> unassignUser());
        saveButton.setOnAction(e -> saveProject());
        cancelButton.setOnAction(e -> viewHandler.openView("ManageProjects"));
        startEndProjectButton.setOnAction(e -> startEndProject());


        loadLabels();
    }

    private void assignUser() {
        Employee selected = availableUsersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            availableUsers.remove(selected);
            assignedUsers.add(selected);
        }
    }

    private void unassignUser() {
        Employee selected = assignedUsersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            assignedUsers.remove(selected);
            availableUsers.add(selected);
        }
    }

    private void saveProject() {
        viewModel.saveEmployees(assignedUsers, availableUsers);
        if(statusChange.equals("ongoing"))
        {
            viewModel.startProject();
        }
        else if(statusChange.equals("finished"))
        {
            viewModel.endProject();
        }
        viewModel.saveNameAndDesc(titleField.getText(), descriptionField.getText());
        viewHandler.openView("ManageProjects");
    }

    private void startEndProject() {
        if(selectedProject.getStatus().equals("pending"))
        {
            statusChange = "ongoing";
            statusText.setText(statusChange);
            startDateLabel.setText(LocalDate.now().toString());
        }
        else if(selectedProject.getStatus().equals("ongoing"))
        {
            statusChange = "finished";
            statusText.setText(statusChange);
            endDateLabel.setText(LocalDate.now().toString());
        }
    }

    private void loadLabels() {
        switch(selectedProject.getStatus())
        {
            case "pending":
                startEndProjectButton.setText("Start project");
                break;
            case "ongoing":
                startEndProjectButton.setText("End project");
                break;
            default:
                startEndProjectButton.setVisible(false);
                saveButton.setVisible(false);
                addUserButton.setVisible(false);
                removeUserButton.setVisible(false);
                break;
        }
        statusText.setText(selectedProject.getStatus());
        if(selectedProject.getStartDate() == null)
        {
            startDateLabel.setText("...");
        }
        else
        {
            startDateLabel.setText(selectedProject.getStartDate());
        }
        if(selectedProject.getEndDate() == null)
        {
            endDateLabel.setText("...");
        }
        else
        {
            endDateLabel.setText(selectedProject.getEndDate());
        }
    }
}
