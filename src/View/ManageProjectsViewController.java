package View;

import ViewModel.ManageProjectsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;


public class ManageProjectsViewController
{

    @FXML private Button addProject;
    @FXML private Button editProject;
    @FXML private Button removeProject;
    @FXML private Button infoProject;
    @FXML private  Button backlog;

    @FXML private TableView<Project> tableView;
    @FXML private TableColumn<Project, String> name;
    @FXML private TableColumn<Project, String> status;
    @FXML private TableColumn<Project, String> startDate;
    @FXML private TableColumn<Project, String> endDate;

    private final ObservableList<Project> dummyProjects = FXCollections.observableArrayList();


    private ViewHandler viewHandler;
    private ManageProjectsViewModel viewModel;


    public void init (ViewHandler viewHandler, ManageProjectsViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;

        // Bind table columns to Project properties
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

// Set data source
        tableView.setItems(dummyProjects); // TODO: Replace with viewModel.getProjects()

// --- Dummy data ---
        Project p1 = new Project("Redesign App", "Active", "2024-05-01", "2024-06-30");
        Project p2 = new Project("Launch Website", "Planning", "2024-06-01", "2024-07-15");
        dummyProjects.addAll(p1, p2);


        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != null) {
                // TODO: Pass selected project to ViewState if needed
                viewHandler.openView("ManageSprints");
            }
        });


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
        viewHandler.openView("AddProject"); // Later: pass a new empty project or use ViewModel logic
    }
    @FXML
    private void edit ()
    {
        Project selceted = tableView.getSelectionModel().getSelectedItem();
        if (selceted == null)
        {
            showAlert("Please select a project to edit.");
        }
        else
        {
            viewHandler.openView("EditProject"); // TODO: store selected project in ViewState or pass via ViewModel
        }

    }
    @FXML
    private void info ()
    {
        Project selceted = tableView.getSelectionModel().getSelectedItem();
        if (selceted == null)
        {
            showAlert("Please select a project to edit.");
        }
        else
        {
            viewHandler.openView("ViewProject"); // TODO: store selected project in ViewState or pass via ViewModel
        }
    }
    @FXML
    private void remove() {
        Project selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            dummyProjects.remove(selected);
        } else {
            showAlert("Please select a project to remove.");
        }
    }
    @FXML
    private void backlog() {
        Project selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // TODO: Pass selected sprint to ViewModel or ViewState
            viewHandler.openView("Backlog");
        } else {
            showAlert("Please select a project to view its backlog.");
        }

    }

    @FXML
    private void goBack()
    {
        viewHandler.openView("Login");
    }




}
