package View;

import Model.Project;
import Model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ViewModel.ManageBacklogViewModel;

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

    private ObservableList<Task> tasks;

    public void init(ViewHandler viewHandler, ManageBacklogViewModel viewModel, Object obj) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.project = (Project) obj;

        // Bind columns
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("priority"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("status"));

        observableList.addAll(project.getBacklog());
        tableView.setItems(observableList);

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != null) {
                // TODO: Pass selected project to ViewState if needed
                viewHandler.openView("ViewTask");
            }
        });
    }

    @FXML
    private void add() {
        viewHandler.openView("AddTask"); // Or ManageAddTask if that’s the name
    }

    @FXML
    private void edit() {
        Task selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // TODO: Set selected task in ViewState
            viewHandler.openView("EditTask");
        } else {
            showAlert("Please select a task to edit.");
        }
    }

    @FXML
    private void remove() {
        Task selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
//            dummyTasks.remove(selected);
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

}
