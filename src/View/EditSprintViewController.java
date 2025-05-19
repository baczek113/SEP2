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

        availableTasks.addAll(project.getBacklog());
        assignedTasks.addAll(sprint.getTasks());
        availableTaskTable.setItems(availableTasks);
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
            assignedTasks.remove(selected);
            availableTasks.add(selected);
        }
    }

    private void saveSprint() {
        System.out.println("Saving sprint:");
        System.out.println("Title: " + titleField.getText());
        System.out.println("Description: " + descriptionField.getText());
        System.out.println("Start: " + startDatePicker.getValue());
        System.out.println("End: " + endDatePicker.getValue());
        System.out.println("Assigned tasks: " + assignedTasks);
        viewHandler.openView("ManageSprints", project);
    }
}
