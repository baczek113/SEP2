package View;

import Model.Project;
import Model.Sprint;
import Model.Task;
import ViewModel.EditSprintViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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
        Object[] receivedData = (Object[]) obj;
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.sprint = (Sprint) receivedData[0];
        this.project = (Project) receivedData[1];

        // Set up table columns
       assignedTaskColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
       availableTaskColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        titleField.setText(sprint.getName());
        Date tempStartDate = sprint.getStart_date();
        startDatePicker.setValue(tempStartDate.toLocalDate());
        Date tempEndDate = sprint.getEnd_date();
        endDatePicker.setValue(tempEndDate.toLocalDate());


//        availableTasks.addAll(project.getBacklog());
        assignedTasks.addAll(sprint.getTasks());
        for (Task task : project.getBacklog()){
          if (task.getSprint_id() != 0 && task.getSprint_id() != sprint.getSprint_id())
          {
            availableTasks.add(task);
          }
        }
        availableTaskTable.setItems(availableTasks);
        assignedTaskTable.setItems(assignedTasks);

        // Action listeners
        addUserButton.setOnAction(e -> assignTask());
        removeUserButton.setOnAction(e -> unassignTask());
        saveButton.setOnAction(e -> saveSprint());
        cancelButton.setOnAction(e -> viewHandler.openView("ManageSprints", this.project));
    }

  private void updateProjects(PropertyChangeEvent propertyChangeEvent)
  {
    updateProjects();
  }

  private void assignTask() {
        Task selected = availableTaskTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            viewModel.addTaskToSprint("addTaskToSprint", sprint, selected);
            this.viewModel.addListener(this::updateProjects);
        }
    }

    private void unassignTask() {
        Task selected = assignedTaskTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            viewModel.removeTaskFromSprint(selected, sprint);
            this.viewModel.addListener(this::updateProjects);
        }
    }

    private void saveSprint() {
        String tempTitle = titleField.getText();
        LocalDate localStartDate = startDatePicker.getValue();
        LocalDate localEndDate = endDatePicker.getValue();
        Date.valueOf(localStartDate);
        Date.valueOf(localEndDate);
        Sprint tempSprint = new Sprint(sprint.getSprint_id(), sprint.getProject_id(), tempTitle, Date.valueOf(localStartDate), Date.valueOf(localEndDate));
        viewModel.editSprint(tempSprint);
        viewHandler.openView("ManageSprints", project);
    }

    private void updateProjects()
    {
      project = viewModel.getProject(project.getProject_id());
      availableTasks.clear();
      for(Task task : project.getBacklog())
      {
        if (task.getSprint_id() != 0
            && task.getSprint_id() != sprint.getSprint_id())
        {
          availableTasks.add(task);
        }
      }
      sprint = viewModel.getProject(project.getProject_id()).getSprints().get(sprint.getSprint_id());
      assignedTasks.setAll(sprint.getTasks());
      availableTaskTable.setItems(availableTasks);
      assignedTaskTable.setItems(assignedTasks);
    }
}
