package View;

import Model.Project;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ViewModel.EditProjectViewModel;

import java.beans.PropertyChangeEvent;

public class EditProjectViewController {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private Label startDateLabel;
    @FXML private Label endDateLabel;
    @FXML private Label statusText;
    @FXML private Button startEndProjectButton;

    @FXML private TableView<String> availableUsersTable;
    @FXML private TableColumn<String, String> availableUsernameColumn;

    @FXML private TableView<String> assignedUsersTable;
    @FXML private TableColumn<String, String> assignedUsernameColumn;

    @FXML private Button addUserButton;
    @FXML private Button removeUserButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private ViewHandler viewHandler;
    private EditProjectViewModel viewModel;
    private Project selectedProject;

    private final ObservableList<String> availableUsers = FXCollections.observableArrayList();
    private final ObservableList<String> assignedUsers = FXCollections.observableArrayList();

    public void init(ViewHandler viewHandler, EditProjectViewModel viewModel, Object obj) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.selectedProject = (Project) obj;

        this.titleField.setText(selectedProject.getName());
        this.descriptionField.setText(selectedProject.getDescription());

        // Setup table columns
        availableUsernameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));
        assignedUsernameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));

        // Dummy data (replace with viewModel later)
        availableUsers.addAll("dev1", "dev2", "dev3");
        assignedUsers.addAll("dev4");

        availableUsersTable.setItems(availableUsers);
        assignedUsersTable.setItems(assignedUsers);

        viewModel.addListener(this::projectUpdated);

        // Action buttons
        addUserButton.setOnAction(e -> assignUser());
        removeUserButton.setOnAction(e -> unassignUser());
        saveButton.setOnAction(e -> saveProject());
        cancelButton.setOnAction(e -> viewHandler.openView("ManageProjects"));
        startEndProjectButton.setOnAction(e -> startEndProject());

        loadLabels();
    }

    private void assignUser() {
        String selected = availableUsersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            availableUsers.remove(selected);
            assignedUsers.add(selected);
        }
    }

    private void unassignUser() {
        String selected = assignedUsersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            assignedUsers.remove(selected);
            availableUsers.add(selected);
        }
    }

    private void saveProject() {
        // TODO: Replace with model logic
        System.out.println("Saving project with:");
        System.out.println("Title: " + titleField.getText());
        System.out.println("Description: " + descriptionField.getText());
        System.out.println("Assigned Users: " + assignedUsers);
        viewHandler.openView("ManageProjects");
    }

    private void startEndProject() {
        if(selectedProject.getStatus().equals("pending"))
        {
            viewModel.startProject(selectedProject);
        }
        else if(selectedProject.getStatus().equals("ongoing"))
        {
            viewModel.endProject(selectedProject);
        }
    }

    private void projectUpdated(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() -> {
            this.projectUpdated();
        });
    }

    private void projectUpdated()
    {
        selectedProject = viewModel.getProject(selectedProject.getProject_id());
        loadLabels();
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
