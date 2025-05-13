package View;

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

    public void init(ViewHandler viewHandler, EditTaskViewModel viewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;

        // Populate priority options
        priorityComboBox.getItems().addAll("Low", "Medium", "High");

        // TODO: Pre-fill fields from selected Task object (via ViewModel or ViewState)
    }

    @FXML
    private void save() {
        String name = titleField.getText();
        String description = titleField1.getText();
        String priority = priorityComboBox.getValue();

        // TODO: Save to model or pass to backend
        System.out.println("Saving task:");
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Priority: " + priority);

        viewHandler.openView("Backlog");
    }

    @FXML
    private void cancel() {
        viewHandler.openView("Backlog");
    }
}
