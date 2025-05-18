package View;

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

    @FXML private TableView<Task> availableUsersTable;
    @FXML private TableColumn<Task, String> availableUsernameColumn;

    @FXML private TableView<Task> assignedUsersTable;
    @FXML private TableColumn<Task, String> assignedUsernameColumn;

    @FXML private Button addUserButton;
    @FXML private Button removeUserButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private ViewHandler viewHandler;
    private EditSprintViewModel viewModel;
    private Sprint sprint;

    private final ObservableList<Task> availableTasks = FXCollections.observableArrayList();
    private final ObservableList<Task> assignedTasks = FXCollections.observableArrayList();

    public void init(ViewHandler viewHandler, EditSprintViewModel viewModel, Object obj) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.sprint = (Sprint) obj;

        // Set up table columns
       assignedUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        titleField.setText(sprint.getName());
        Date tempStartDate = sprint.getStart_date();
        startDatePicker.setValue(tempStartDate.toLocalDate());
        Date tempEndDate = sprint.getEnd_date();
        endDatePicker.setValue(tempEndDate.toLocalDate());

        assignedTasks.addAll(sprint.getTasks());
        availableUsersTable.setItems(availableTasks);
        assignedUsersTable.setItems(assignedTasks);

        // Action listeners
        addUserButton.setOnAction(e -> assignTask());
        removeUserButton.setOnAction(e -> unassignTask());
        saveButton.setOnAction(e -> saveSprint());
        cancelButton.setOnAction(e -> viewHandler.openView("ManageSprints"));
    }

    private void assignTask() {
        Task selected = availableUsersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            availableTasks.remove(selected);
            assignedTasks.add(selected);
        }
    }

    private void unassignTask() {
        Task selected = assignedUsersTable.getSelectionModel().getSelectedItem();
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
        viewHandler.openView("ManageSprints");
    }
}
