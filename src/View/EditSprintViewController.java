package View;

import DataModel.Project;
import DataModel.Sprint;
import DataModel.Task;
import ViewModel.EditSprintViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.time.LocalDate;

public class EditSprintViewController {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;

    @FXML private TableView<Task> availableTaskTable;
    @FXML private TableColumn<Task, String> availableTaskColumn;

    @FXML private TableView<Task> assignedTaskTable;
    @FXML private TableColumn<Task, String> assignedTaskColumn;

    @FXML private Button addUserButton;
    @FXML private Button removeUserButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private ViewHandler viewHandler;
    private EditSprintViewModel viewModel;
    private Sprint sprint;
    private Project project;

    private final ObservableList<Task> availableTasks = FXCollections.observableArrayList();
    private final ObservableList<Task> assignedTasks = FXCollections.observableArrayList();

    public void init(ViewHandler viewHandler, EditSprintViewModel viewModel, Object obj) {
        availableTasks.clear();
        assignedTasks.clear();
        Object[] receivedData = (Object[]) obj;
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.sprint = (Sprint) receivedData[0];
        this.project = (Project) receivedData[1];
        this.viewModel.setSprint(sprint);

        // Set up table columns
       assignedTaskColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
       availableTaskColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        titleField.setText(sprint.getName());
        Date tempStartDate = sprint.getStart_date();
        startDatePicker.setValue(tempStartDate.toLocalDate());
        Date tempEndDate = sprint.getEnd_date();
        endDatePicker.setValue(tempEndDate.toLocalDate());


        for (Task task : project.getBacklog()){
          if (task.getSprint_id() == 0)
          {
            availableTasks.add(task);
          }
        }
        availableTaskTable.setItems(availableTasks);

        assignedTasks.addAll(sprint.getTasks());
        assignedTaskTable.setItems(assignedTasks);

        // Action listeners
        addUserButton.setOnAction(e -> assignTask());
        removeUserButton.setOnAction(e -> unassignTask());
        saveButton.setOnAction(e -> saveSprint());
        cancelButton.setOnAction(e -> viewHandler.openView("ManageSprints", this.project));
    }

  private void assignTask() {
        Task selected = availableTaskTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            availableTasks.remove(selected);
            assignedTasks.add(selected);
        }
    }

    private void unassignTask() {
        Task selected = assignedTaskTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            availableTasks.add(selected);
            assignedTasks.remove(selected);
        }
    }

    private void saveSprint() {
        String tempTitle = titleField.getText().trim();
        LocalDate localStartDate = startDatePicker.getValue();
        LocalDate localEndDate = endDatePicker.getValue();

        if (tempTitle.isEmpty() || localStartDate == null || localEndDate == null || localStartDate.isAfter(localEndDate)) {
            showAlert("Sprint data is incorrect. Please check the title and dates.");
            return;
        }

        Sprint tempSprint = new Sprint(
                sprint.getSprint_id(),
                sprint.getProject_id(),
                tempTitle,
                Date.valueOf(localStartDate),
                Date.valueOf(localEndDate)
        );

        viewModel.editSprint(tempSprint);
        viewModel.addTasks(assignedTasks, availableTasks);
        viewHandler.openView("ManageSprints", project);
    }
    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
