package View;

import Model.Project;
import Model.Task;
import ViewModel.EditTaskViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditTaskViewController {

    @FXML private TextField titleField;
    @FXML private TextField titleField1; // This is used for description (better rename later)
    @FXML private ComboBox<String> priorityComboBox;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private ViewHandler viewHandler;
    private EditTaskViewModel viewModel;
    private Task selectedTask;

    public void init(ViewHandler viewHandler, EditTaskViewModel viewModel, Object obj) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        selectedTask = (Task) obj;

        // Populate priority options
        priorityComboBox.getItems().addAll("Very Low", "Low", "Medium", "High", "Very High");

        titleField.setText(selectedTask.getName());
        titleField1.setText(selectedTask.getDescription());
        priorityComboBox.getSelectionModel().select(selectedTask.getPriority());
    }

    @FXML
    private void save() {
        String name = titleField.getText();
        String description = titleField1.getText();
        String priority = priorityComboBox.getValue();

        if(!selectedTask.getName().equals(name) || !selectedTask.getDescription().equals(description)) {
            selectedTask.setTitle(name);
            selectedTask.setDescription(description);
            viewModel.saveEdition(selectedTask);
        }
        if(!priority.equals(selectedTask.getPriority())) {
            viewModel.assignPriority(selectedTask, priorityStringToInt(priority));
        }

        viewHandler.openView("Backlog", viewModel.getProject(selectedTask.getProject_id()));
    }

    @FXML
    private void cancel() {
        viewHandler.openView("Backlog", viewModel.getProject(selectedTask.getProject_id()));
    }

    private int priorityStringToInt(String priority) {
        switch (priority) {
            case "Low":
                return 2;
            case "Medium":
                return 3;
            case "High":
                return 4;
            case "Very High":
                return 5;
            default:
                return 1;
        }
    }
}
