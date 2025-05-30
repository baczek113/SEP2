package View;

import DataModel.Project;
import DataModel.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ViewModel.ManageBacklogViewModel;

import java.beans.PropertyChangeEvent;

public class ManageBacklogViewController {

    @FXML private Button addProject;
    @FXML private Button editProject;
    @FXML private Button removeProject;

    @FXML private TableView<Task> tableView;
    @FXML private TableColumn<Task, String> name;
    @FXML private TableColumn<Task, String> startDate; // Actually Priority
    @FXML private TableColumn<Task, String> endDate;   // Actually Status

    private ViewHandler viewHandler;
    private ManageBacklogViewModel viewModel;
    private Project project;

    private final ObservableList<Task> observableList = FXCollections.observableArrayList();

    public void init(ViewHandler viewHandler, ManageBacklogViewModel viewModel, Object obj) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.project = (Project) obj;
        viewModel.addListener(this::updateProject);

        if(project.getCreated_by().getEmployee_id() != viewModel.getLoggedEmployee().getEmployee_id() || project.getStatus().equals("finished"))
        {
            addProject.setVisible(false);
            editProject.setVisible(false);
            removeProject.setVisible(false);
        }

        // Bind columns
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("priority"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("status"));

        observableList.addAll(project.getBacklog());
        tableView.setItems(observableList);

        tableView.setOnMouseClicked(event -> {
            Task selected = tableView.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2 && selected != null) {
                // TODO: Pass selected project to ViewState if needed
                viewHandler.openView("ViewTask", selected);
            }
        });
    }

    @FXML
    private void add() {
        viewHandler.openView("AddTask", project); // Or ManageAddTask if that’s the name
    }

    @FXML
    private void edit() {
        Task selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            viewHandler.openView("EditTask", selected);
        } else {
            showAlert("Please select a task to edit.");
        }
    }

    @FXML
    private void remove() {
        Task selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            viewModel.remove(selected);
        } else {
            showAlert("Please select a task to remove.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }


    @FXML
    private void goBack()
    {
        viewHandler.openView("ManageProjects");
    }

    private void updateProject(PropertyChangeEvent propertyChangeEvent) {
            project = viewModel.getProjects().get(project.getProject_id());
            observableList.clear();
            observableList.addAll(project.getBacklog());
            tableView.setItems(observableList);
    }
}
