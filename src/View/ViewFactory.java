package View;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ViewModel.ViewModelFactory;
import java.io.IOException;
import ViewModel.ViewModelFactory;

public class ViewFactory
{
    private ViewHandler viewHandler;
    private final ViewModelFactory viewModelFactory;

    public ViewFactory (ViewModelFactory viewModelFactory)
    {
        this.viewModelFactory=viewModelFactory;
    }
    public void setViewHandler(ViewHandler viewHandler) {
        this.viewHandler = viewHandler;
    }
    public Scene load (String viewName, Object obj)
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
                 controller.init(viewHandler, viewModelFactory.getEditProjectViewModel(), obj);
             }

             case "ViewProject" -> {
                 loader.setLocation(getClass().getResource("/View/ViewProjectView.fxml"));
                 root = loader.load();
                 ViewProjectViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getViewProjectViewModel(), obj);
             }

             case "ManageSprints" -> {
                 loader.setLocation(getClass().getResource("/View/ManageSprintsView.fxml"));
                 root = loader.load();
                 ManageSprintsViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getManageSprintsViewModel(), obj);
             }

             case "AddSprint" -> {
                 loader.setLocation(getClass().getResource("/View/AddSprintView.fxml"));
                 root = loader.load();
                 AddSprintViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getAddSprintViewModel(), obj);
             }

             case "EditSprint" -> {
                 loader.setLocation(getClass().getResource("/View/EditSprintView.fxml"));
                 root = loader.load();
                 EditSprintViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getEditSprintViewModel(), obj);
             }


             case "ManageTasks" -> {
                 loader.setLocation(getClass().getResource("/View/ManageTasksView.fxml"));
                 root = loader.load();
                 ManageTasksViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getManageTasksViewModel(), obj);
             }

             case "AddTask" -> {
                 loader.setLocation(getClass().getResource("/View/AddTaskView.fxml"));
                 root = loader.load();
                 AddTaskViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getAddTaskViewModel(), obj);
             }

             case "EditTask" -> {
                 loader.setLocation(getClass().getResource("/View/EditTaskView.fxml"));
                 root = loader.load();
                 EditTaskViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getEditTaskViewModel(), obj);
             }

             case "ViewTask" -> {
                 loader.setLocation(getClass().getResource("/View/ViewTaskView.fxml"));
                 root = loader.load();
                 ViewTaskViewController controller = loader.getController();
                 controller.init(viewHandler, viewModelFactory.getViewTaskViewModel(), obj);
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
                 controller.init(viewHandler, viewModelFactory.getManageBacklogViewModel(), obj);

             }
         }
         if(root == null)
         {
             System.out.println("View cannot be created, because it does not exist");
             return null;
         }
         return new Scene(root);
     } catch (IOException e)
     {
         e.printStackTrace();
         return null;
     }
    }

}
