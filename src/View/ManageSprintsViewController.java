//package View;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.control.Alert;
//import javafx.scene.control.cell.PropertyValueFactory;
//import ViewModel.AddProjectViewModel;
//import ViewModel.ManageProjectsViewModel;
//import ViewModel.ManageSprintsViewModel;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//
//public class ManageSprintsViewController {
//    private ViewHandler viewHandler;
//    private ManageSprintsViewModel viewModel;
//
//    @FXML private Button addSprint;
//    @FXML private Button editSprint;
//    @FXML private Button removeSprint;
//    @FXML private Button backlog;
//
//    @FXML private TableView<Sprint> tableView;
//    @FXML private TableColumn<Sprint, String> name;
//    @FXML private TableColumn<Sprint, String> startDate;
//    @FXML private TableColumn<Sprint, String> endDate;
//
//    private final ObservableList<Sprint> dummySprints = FXCollections.observableArrayList();
//
//
//
//    public void init (ViewHandler viewHandler, ManageSprintsViewModel viewModel)
//    {
//        this.viewHandler = viewHandler;
//        this.viewModel = viewModel;
//
//        // Bind columns to Sprint properties
//        name.setCellValueFactory(new PropertyValueFactory<>("name"));
//        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
//        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
//
//// Set table data
//        tableView.setItems(dummySprints); // TODO: Replace with viewModel.getSprints()
//
//// --- Dummy data ---
//        Sprint s1 = new Sprint("Sprint 1", "2024-05-01", "2024-05-15");
//        Sprint s2 = new Sprint("Sprint 2", "2024-05-16", "2024-05-31");
//
//        dummySprints.addAll(s1, s2);
//
//        tableView.setOnMouseClicked(event -> {
//            if (event.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != null) {
//                // TODO: Pass selected project to ViewState if needed
//                viewHandler.openView("ManageTasks");
//            }
//        });
//
//
//    }
//
//    private void showAlert(String message) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Info");
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//
//    @FXML
//    private void add() {
//        viewHandler.openView("AddSprint"); // TODO: Pass selected project context if needed
//    }
//
//    @FXML
//    private void edit() {
//        Sprint selected = tableView.getSelectionModel().getSelectedItem();
//        if (selected != null) {
//            // TODO: store selected sprint in ViewModel or ViewState
//            viewHandler.openView("EditSprint");
//        } else {
//            showAlert("Please select a sprint to edit.");
//        }
//    }
//    @FXML
//    private void remove() {
//        Sprint selected = tableView.getSelectionModel().getSelectedItem();
//        if (selected != null) {
//            dummySprints.remove(selected); // TODO: viewModel.removeSprint(selected)
//        } else {
//            showAlert("Please select a sprint to remove.");
//        }
//    }
//    @FXML
//    private void goBack()
//    {
//        viewHandler.openView("ManageProjects");
//    }
//
//}
