package View;

import DataModel.Project;
import DataModel.Sprint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import ViewModel.ManageSprintsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.beans.PropertyChangeEvent;

public class ManageSprintsViewController {
    private ViewHandler viewHandler;
    private ManageSprintsViewModel viewModel;

    @FXML private Button addSprint;
    @FXML private Button editSprint;
    @FXML private Button removeSprint;
    @FXML private Button backlog;

    @FXML private TableView<Sprint> tableView;
    @FXML private TableColumn<Sprint, String> name;
    @FXML private TableColumn<Sprint, String> startDate;
    @FXML private TableColumn<Sprint, String> endDate;

    private ObservableList<Sprint> sprints = FXCollections.observableArrayList();
    Project project;

    public void init (ViewHandler viewHandler, ManageSprintsViewModel viewModel, Object obj)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.project = (Project) obj;
        this.viewModel.addListener(this::updateProjects);
        if (viewModel.getLoggedEmployee().getRole().getRole_id() != 3 || project.getStatus().equals("finished")){
            addSprint.setVisible(false);
            editSprint.setVisible(false);
            removeSprint.setVisible(false);
        }
        updateProjects();

        sprints.setAll(project.getSprints());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("end_date"));

        tableView.setItems(sprints);

        tableView.setOnMouseClicked(event -> {
            Sprint selected = tableView.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != null) {
                viewHandler.openView("ManageTasks", selected);
            }
        });


    }

    private void updateProjects(PropertyChangeEvent propertyChangeEvent)
    {
        updateProjects();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void add() {
        viewHandler.openView("AddSprint", project);
    }

    @FXML
    private void edit() {
        Sprint selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Object[] dataToPass = {selected, this.project};
            viewHandler.openView("EditSprint", dataToPass);
        } else {
            showAlert("Please select a sprint to edit.");
        }
    }
    @FXML
    private void remove() {
        Sprint selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            viewModel.remove(selected);
        } else {
            showAlert("Please select a sprint to remove.");
        }
    }
    @FXML
    private void goBack()
    {
        viewHandler.openView("ManageProjects");
    }

    private void updateProjects()
    {
        project = viewModel.getProject(project.getProject_id());
        sprints.setAll(project.getSprints());
        tableView.setItems(sprints);
    }

}
