package View;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ViewModel.ViewModelFactory;
import java.io.IOException;
import ViewModel.ViewModelFactory;

public class ViewFactory
{
    private final ViewHandler viewHandler;
    private final ViewModelFactory viewModelFactory;

    public ViewFactory (ViewHandler viewHandler, ViewModelFactory viewModelFactory)
    {
        this.viewHandler=viewHandler;
        this.viewModelFactory=viewModelFactory;
    }
    public Scene load (String viewName)
    {
     try{
         FXMLLoader loader = new FXMLLoader();
         Parent root = null;
         switch (viewName) {
             case "Login" -> {
                 loader.setLocation(getClass().getResource("/View/LoginView.fxml"));
                 root = loader.load();
                 LoginViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getLoginViewModel());
             }

             case "ManageProjects" -> {
                 loader.setLocation(getClass().getResource("/View/ManageProjectsView.fxml"));
                 root = loader.load();
                 ManageProjectsViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getManageProjectsViewModel());
             }

             case "AddProject" -> {
                 loader.setLocation(getClass().getResource("/View/AddProjectView.fxml"));
                 root = loader.load();
                 AddProjectViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getAddProjectViewModel());
             }

             case "EditProject" -> {
                 loader.setLocation(getClass().getResource("/View/EditProjectView.fxml"));
                 root = loader.load();
                 EditProjectViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getEditProjectViewModel());
             }

             case "ViewProject" -> {
                 loader.setLocation(getClass().getResource("/View/ViewProjectView.fxml"));
                 root = loader.load();
                 ViewProjectViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getViewProjectViewModel());
             }

             case "ManageSprints" -> {
                 loader.setLocation(getClass().getResource("/View/ManageSprintsView.fxml"));
                 root = loader.load();
                 ManageSprintsViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getManageSprintsViewModel());
             }

             case "AddSprint" -> {
                 loader.setLocation(getClass().getResource("/View/AddSprintView.fxml"));
                 root = loader.load();
                 AddSprintViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getAddSprintViewModel());
             }

             case "EditSprint" -> {
                 loader.setLocation(getClass().getResource("/View/EditSprintView.fxml"));
                 root = loader.load();
                 EditSprintViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getEditSprintViewModel());
             }

             case "ViewSprint" -> {
                 loader.setLocation(getClass().getResource("/View/ViewSprintView.fxml"));
                 root = loader.load();
                 ViewSprintViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getViewSprintViewModel());
             }

             case "ManageTasks" -> {
                 loader.setLocation(getClass().getResource("/View/ManageTasksView.fxml"));
                 root = loader.load();
                 ManageTasksViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getManageTasksViewModel());
             }

             case "AddTask" -> {
                 loader.setLocation(getClass().getResource("/View/AddTaskView.fxml"));
                 root = loader.load();
                 AddTaskViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getAddTaskViewModel());
             }

             case "EditTask" -> {
                 loader.setLocation(getClass().getResource("/View/EditTaskView.fxml"));
                 root = loader.load();
                 EditTaskViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getEditTaskViewModel());
             }

             case "ViewTask" -> {
                 loader.setLocation(getClass().getResource("/View/ViewTaskView.fxml"));
                 root = loader.load();
                 ViewTaskViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getViewTaskViewModel());
             }

             case "ManageUsers" -> {
                 loader.setLocation(getClass().getResource("/View/ManageUsersView.fxml"));
                 root = loader.load();
                 ManageUsersViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getManageUsersViewModel());
             }
             case "Backlog" -> {
                 loader.setLocation(getClass().getResource("/View/ManageBacklog.fxml"));
                 root = loader.load();
                 ManageBacklogViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getManageBacklogViewModel());

             }
         }
         return new Scene(root);
     } catch (IOException e)
     {
         e.printStackTrace();
         return null;
     }
    }

}
