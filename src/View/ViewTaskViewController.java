package View;

import DataModel.Employee;
import DataModel.Task;
import ViewModel.ViewTaskViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewTaskViewController {

    @FXML private Label nameLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label priorityLabel;

    @FXML private TableView<Employee> assignedUsersTable;
    @FXML private TableColumn<Employee, String> usernameColumn;
    private final ObservableList<Employee> assignedUsers = FXCollections.observableArrayList();

    @FXML private Button editButton;
    @FXML private Button cancelButton;

    private ViewHandler viewHandler;
    private ViewTaskViewModel viewModel;
    private Task selectedTask;

    public void init(ViewHandler viewHandler, ViewTaskViewModel viewModel, Object obj) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.selectedTask = (Task) obj;

        if (viewModel.getProject(selectedTask.getProject_id()).getStatus().equals("finished")){
            editButton.setVisible(false);
        }

        // TODO: Replace with values from the actual viewModel or model
        nameLabel.setText(selectedTask.getName());
        descriptionLabel.setText(selectedTask.getDescription());
        priorityLabel.setText(selectedTask.getPriority());

        // Setup table
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        assignedUsers.addAll(selectedTask.getAssignedTo());
        assignedUsersTable.setItems(assignedUsers);

        if (viewModel.getLoggedEmployee().getRole().getRole_name().equals("developer")|| viewModel.getLoggedEmployee().getRole().getRole_name().equals("scrum_master"))
        {
            editButton.setVisible(false);
        }
    }

    @FXML
    private void edit() {
        viewHandler.openView("EditTask", selectedTask); // Navigate to EditTask view
    }

    @FXML
    private void cancel() {
        viewHandler.openView("Backlog", viewModel.getProject(selectedTask.getProject_id())); // Navigate back to task list
    }
}
