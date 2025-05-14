package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ViewModel.ViewProjectViewModel;

public class ViewProjectViewController {

    @FXML private Label titleLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label statusLabel;
    @FXML private Label scrumMasterLabel;
    @FXML private Label startDateLabel;
    @FXML private Label endDateLabel;

    @FXML private TableView<String> assignedUsersTable;
    @FXML private TableColumn<String, String> assignedUsernameColumn;

    @FXML private Button backButton;
    @FXML private Button editButton;

    private ViewHandler viewHandler;
    private ViewProjectViewModel viewModel;

    private final ObservableList<String> dummyAssignedUsers = FXCollections.observableArrayList();

    public void init(ViewHandler viewHandler, ViewProjectViewModel viewModel) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;

        // Fill dummy data for now — TODO: Replace with viewModel.getSelectedProject()
        titleLabel.setText("Redesign App");
        descriptionLabel.setText("UI redesign for mobile application.");
        statusLabel.setText("Active");
        scrumMasterLabel.setText("Alice");
        startDateLabel.setText("2024-05-01");
        endDateLabel.setText("2024-06-30");

        // Set table column
        assignedUsernameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));
        dummyAssignedUsers.addAll("dev1", "dev2", "dev3");
        assignedUsersTable.setItems(dummyAssignedUsers); // TODO: Replace with viewModel.getAssignedUsers()
    }

    @FXML
    private void backToProject() {
        viewHandler.openView("ManageProjects");
    }
    @FXML
    private void editProject() {
        viewHandler.openView("EditProject");
    }


}
