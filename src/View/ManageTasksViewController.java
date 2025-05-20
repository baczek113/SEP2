package View;

import Model.Project;
import ViewModel.ManageTasksViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Model.Task;

public class ManageTasksViewController {

    @FXML private TableView<Task> todoTable;
    @FXML private TableColumn<Task, String> todoNameColumn;
    @FXML private TableColumn<Task, String> todoCreatedByColumn;
    @FXML private TableColumn<Task, String> todoPriorityColumn;

    @FXML private TableView<Task> doingTable;
    @FXML private TableColumn<Task, String> doingNameColumn;
    @FXML private TableColumn<Task, String> doingCreatedByColumn;
    @FXML private TableColumn<Task, String> doingPriorityColumn;

    @FXML private TableView<Task> doneTable;
    @FXML private TableColumn<Task, String> doneNameColumn;
    @FXML private TableColumn<Task, String> doneCreatedByColumn;
    @FXML private TableColumn<Task, String> donePriorityColumn;

    @FXML private TableView<Task> checkedTable;
    @FXML private TableColumn<Task, String> checkedNameColumn;
    @FXML private TableColumn<Task, String> checkedCreatedByColumn;
    @FXML private TableColumn<Task, String> checkedPriorityColumn;

    @FXML private Button takeTask;
    @FXML private Button changeStatus;
    @FXML private Button unassign;
    @FXML private Button goBack;

    private ViewHandler viewHandler;
    private ManageTasksViewModel viewModel;
    private Project project;

    private final ObservableList<Task> todoTasks = FXCollections.observableArrayList();
    private final ObservableList<Task> doingTasks = FXCollections.observableArrayList();
    private final ObservableList<Task> doneTasks = FXCollections.observableArrayList();
    private final ObservableList<Task> checkedTasks = FXCollections.observableArrayList();

    public void init(ViewHandler viewHandler, ManageTasksViewModel viewModel ,Object obj) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.project = (Project) obj;
        bindTable(todoTable, todoNameColumn, todoCreatedByColumn, todoPriorityColumn, todoTasks);
        bindTable(doingTable, doingNameColumn, doingCreatedByColumn, doingPriorityColumn, doingTasks);
        bindTable(doneTable, doneNameColumn, doneCreatedByColumn, donePriorityColumn, doneTasks);
        bindTable(checkedTable, checkedNameColumn, checkedCreatedByColumn, checkedPriorityColumn, checkedTasks);
    }

    private void bindTable(TableView<Task> table, TableColumn<Task, String> name,
                           TableColumn<Task, String> createdBy, TableColumn<Task, String> priority,
                           ObservableList<Task> data) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        createdBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        table.setItems(data);
    }

    @FXML
    private void goBack() {
        viewHandler.openView("ManageSprints", project);
    }

    @FXML
    private void takeTask() {
        showAlert("Take task logic not implemented yet."); // TODO
    }

    @FXML
    private void unassign() {
        showAlert("Unassign logic not implemented yet."); // TODO
    }

    @FXML
    private void changeStatus() {
        showAlert("Change status logic not implemented yet."); // TODO
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
