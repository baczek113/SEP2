package View;

import DataModel.Project;
import ViewModel.ManageProjectsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.beans.PropertyChangeEvent;


public class ManageProjectsViewController
{

    @FXML private Button addProject;
    @FXML private Button editProject;
    @FXML private Button infoProject;
    @FXML private  Button backlog;

    @FXML private TableView<Project> tableView;
    @FXML private TableColumn<Project, String> name;
    @FXML private TableColumn<Project, String> status;
    @FXML private TableColumn<Project, String> startDate;
    @FXML private TableColumn<Project, String> endDate;

    private ViewHandler viewHandler;
    private ManageProjectsViewModel viewModel;


    public void init (ViewHandler viewHandler, ManageProjectsViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        viewModel.addListener(this::updateProjectsHandler);
        updateProjects();

        // Bind table columns to Project properties
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != null) {
                Project selected = tableView.getSelectionModel().getSelectedItem();
                viewHandler.openView("ManageSprints", selected);

            }

        });

        String roleName = viewModel.employeeGetLog().getRole().getRole_name();
        if (roleName.equals("developer") || roleName.equals("scrum_master"))
        {
            addProject.setVisible(false);
            editProject.setVisible(false);
        }




    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void add ()
    {
        viewHandler.openView("AddProject");
    }
    @FXML
    private void edit ()
    {
        Project selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null)
        {
            showAlert("Please select a project to edit.");
        }
        else
        {
            viewHandler.openView("EditProject", selected);
        }

    }
    @FXML
    private void info ()
    {
        Project selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null)
        {
            showAlert("Please select a project to view.");
        }
        else
        {
            viewHandler.openView("ViewProject", selected);
        }
    }
    @FXML
    private void backlog() {
        Project selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            viewHandler.openView("Backlog", selected);
        } else {
            showAlert("Please select a project to view its backlog.");
        }

    }

    @FXML
    private void goBack()
    {
        viewModel.logOut();
        viewHandler.openView("Login");
    }

    private void updateProjects()
    {
        tableView.setItems(viewModel.getProjects());
    }

    private void updateProjectsHandler(PropertyChangeEvent event)
    {
        updateProjects();
    }


}
