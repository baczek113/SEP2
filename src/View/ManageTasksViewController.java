package View;

import Model.Employee;
import Model.Project;
import Model.Sprint;
import ViewModel.ManageTasksViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Model.Task;

import java.beans.PropertyChangeEvent;

public class ManageTasksViewController {

    @FXML private TableView<Task> todoTable;
    @FXML private TableColumn<Task, String> todoNameColumn;
    @FXML private TableColumn<Task, String> todoAssignedToColumn;
    @FXML private TableColumn<Task, String> todoPriorityColumn;
    @FXML private TabPane taskTabPane;

    @FXML private TableView<Task> doingTable;
    @FXML private TableColumn<Task, String> doingNameColumn;
    @FXML private TableColumn<Task, String> doingAssignedToColumn;
    @FXML private TableColumn<Task, String> doingPriorityColumn;

    @FXML private TableView<Task> doneTable;
    @FXML private TableColumn<Task, String> doneNameColumn;
    @FXML private TableColumn<Task, String> doneAssignedToColumn;
    @FXML private TableColumn<Task, String> donePriorityColumn;

    @FXML private TableView<Task> checkedTable;
    @FXML private TableColumn<Task, String> checkedNameColumn;
    @FXML private TableColumn<Task, String> checkedAssignedToColumn;
    @FXML private TableColumn<Task, String> checkedPriorityColumn;

    @FXML private Button takeTask;
    @FXML private Button changeStatus;
    @FXML private Button unassign;
    @FXML private Button goBack;

    private ViewHandler viewHandler;
    private ManageTasksViewModel viewModel;
    private Sprint sprint;

    private final ObservableList<Task> todoTasks = FXCollections.observableArrayList();
    private final ObservableList<Task> doingTasks = FXCollections.observableArrayList();
    private final ObservableList<Task> doneTasks = FXCollections.observableArrayList();
    private final ObservableList<Task> checkedTasks = FXCollections.observableArrayList();

    public void init(ViewHandler viewHandler, ManageTasksViewModel viewModel, Object obj) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.sprint = (Sprint) obj;
        viewModel.addPropertyChangeListener(this::updateSprint);
        updateSprint();

        if(viewModel.getLoggedEmployeeRole().equals("scrum_master") || viewModel.getProject(sprint.getProject_id()).getStatus().equals("finished"))
        {
            takeTask.setVisible(false);
            changeStatus.setVisible(false);
            unassign.setVisible(false);
        }
        if(viewModel.getLoggedEmployeeRole().equals("product_owner"))
        {
            takeTask.setVisible(false);
            unassign.setVisible(false);
        }

        bindTable(todoTable, todoNameColumn, todoAssignedToColumn, todoPriorityColumn, todoTasks);
        bindTable(doingTable, doingNameColumn, doingAssignedToColumn, doingPriorityColumn, doingTasks);
        bindTable(doneTable, doneNameColumn, doneAssignedToColumn, donePriorityColumn, doneTasks);
        bindTable(checkedTable, checkedNameColumn, checkedAssignedToColumn, checkedPriorityColumn, checkedTasks);

        enableDoubleClickOpen(todoTable);
        enableDoubleClickOpen(doingTable);
        enableDoubleClickOpen(doneTable);
        enableDoubleClickOpen(checkedTable);

    }

        private void enableDoubleClickOpen(TableView<Task> table) {
            table.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && table.getSelectionModel().getSelectedItem() != null) {
                    Task selected = table.getSelectionModel().getSelectedItem();
                    viewHandler.openView("ViewTask", selected);
                }
            });
        }


        private void updateSprint(PropertyChangeEvent propertyChangeEvent) {
        updateSprint();
    }

    private void updateSprint()
    {
        boolean sprintExists = false;
        for(Sprint newSprint : viewModel.getProject(sprint.getProject_id()).getSprints())
        {
            if(newSprint.getSprint_id() == sprint.getSprint_id())
            {
                this.sprint = newSprint;
                sprintExists = true;
            }
        }
        if(!sprintExists)
        {
            showAlert("Sprint has been removed");
            viewHandler.openView("ManageSprints", viewModel.getProject(sprint.getProject_id()));
        }
        todoTasks.clear();
        doingTasks.clear();
        doneTasks.clear();
        checkedTasks.clear();
        for(Task task : sprint.getTasks()) {
            switch(task.getStatus()) {
                case "to-do":
                    todoTasks.add(task);
                    break;
                case "doing":
                    doingTasks.add(task);
                    break;
                case "done":
                    doneTasks.add(task);
                    break;
                case "done-and-approved":
                    checkedTasks.add(task);
                    break;
            }
        }
    }

    private void bindTable(TableView<Task> table, TableColumn<Task, String> name,
                           TableColumn<Task, String> assignedTo, TableColumn<Task, String> priority,
                           ObservableList<Task> data) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        assignedTo.setCellValueFactory(cellData -> {
            String employeesListed = "";
            for(Employee employee : cellData.getValue().getAssignedTo()) {
                employeesListed += employee.getUsername() + ", ";
            }
            if(!employeesListed.equals("")) {
                employeesListed = employeesListed.substring(0, employeesListed.length() - 2);
                return new SimpleStringProperty(employeesListed);
            }
            else
            {
                return new SimpleStringProperty("No employees assigned");
            }
        });
        priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        table.setItems(data);
    }

    @FXML
    private void goBack() {
        viewHandler.openView("ManageSprints", viewModel.getProject(sprint.getProject_id()));
    }

    @FXML
    private void takeTask() {
        Task selected;
        switch(taskTabPane.getSelectionModel().getSelectedIndex()) {
            case 0:
                selected = todoTable.getSelectionModel().getSelectedItem();
                if(selected != null) {
                    viewModel.takeTask(selected);
                    break;
                }
                showAlert("Select a task");
                break;
            case 1:
                selected = doingTable.getSelectionModel().getSelectedItem();
                if(selected != null) {
                    viewModel.takeTask(selected);
                    break;
                }
                showAlert("Select a task");
                break;
            default:
                showAlert("Cannot assign done tasks");
                break;
        }
    }

    @FXML
    private void unassign() {
        Task selected;
        switch(taskTabPane.getSelectionModel().getSelectedIndex()) {
            case 0:
                selected = todoTable.getSelectionModel().getSelectedItem();
                if(selected != null) {
                    viewModel.unassignTask(selected);
                    break;
                }
                showAlert("Select a task");
                break;
            case 1:
                selected = doingTable.getSelectionModel().getSelectedItem();
                if(selected != null) {
                    viewModel.unassignTask(selected);
                    break;
                }
                showAlert("Select a task");
                break;
            default:
                showAlert("Cannot assign done tasks");
                break;
        }
    }

    @FXML
    private void changeStatus() {
        Task selected;
        if(viewModel.getLoggedEmployeeRole().equals("developer")) {
                switch (taskTabPane.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        selected = todoTable.getSelectionModel().getSelectedItem();
                            if (selected != null) {
                                if (selected.getAssignedTo().get(viewModel.getLoggedEmployeeId()) != null) {
                                    viewModel.changeTaskStatus(selected, "doing");
                                }
                                else
                                {
                                    showAlert("You are not assigned to this task");
                                }
                            }
                            else {
                                showAlert("Select a task");
                            }
                        break;
                    case 1:
                        selected = doingTable.getSelectionModel().getSelectedItem();
                        if (selected != null) {
                            if (selected.getAssignedTo().get(viewModel.getLoggedEmployeeId()) != null) {
                                viewModel.changeTaskStatus(selected, "done");
                            }
                            else
                            {
                                showAlert("You are not assigned to this task");
                            }
                        }
                        else {
                            showAlert("Select a task");
                        }
                        break;
                    default:
                        showAlert("A developer can not approve tasks");
                        break;
                }
        }
        else if(viewModel.getLoggedEmployeeRole().equals("product_owner")) {
            if(taskTabPane.getSelectionModel().getSelectedIndex() == 2) {
                selected = doneTable.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    viewModel.changeTaskStatus(selected, "done-and-approved");
                }
                else {
                    showAlert("Select a task");
                }
            }
            else
            {
                showAlert("As a product owner you can only change status from done to done and approved");
            }
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
