package View;

import Model.Project;
import Model.Task;
import ViewModel.AddSprintViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.time.LocalDate;

public class AddSprintViewController {
    @FXML private TextField titleField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;

    @FXML private Button addTaskButton;
    @FXML private Button removeUserButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private ViewHandler viewHandler;
    private AddSprintViewModel viewModel;
    private Project project;

    public void init(ViewHandler viewHandler, AddSprintViewModel viewModel, Object obj) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.project = (Project) obj;

    }

    @FXML
    private void save() {
        String name = titleField.getText();
        LocalDate localStartDate = startDatePicker.getValue();
        LocalDate localEndDate = endDatePicker.getValue();

        Date startDate = null;
        if (localStartDate != null) {
            startDate = Date.valueOf(localStartDate);
        }
        Date endDate = null;
        if (localEndDate != null) {
            endDate = Date.valueOf(localEndDate);
        }

        viewModel.addSprint(project, name, startDate, endDate);
        showAlert("Sprint saved successfully.");
        viewHandler.openView("ManageSprints", project);
    }

    @FXML
    private void cancel() {
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
