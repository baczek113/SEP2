package ViewModel;

public class ViewModelFactory {
    private LoginViewModel loginViewModel;

    private AddProjectViewModel addProjectViewModel;
    private EditProjectViewModel editProjectViewModel;
    private ViewProjectViewModel viewProjectViewModel;
    private ManageProjectsViewModel manageProjectsViewModel;

    private AddSprintViewModel addSprintViewModel;
    private EditSprintViewModel editSprintViewModel;
    private ViewSprintViewModel viewSprintViewModel;
    private ManageSprintsViewModel manageSprintsViewModel;

    private AddTaskViewModel addTaskViewModel;
    private EditTaskViewModel editTaskViewModel;
    private ViewTaskViewModel viewTaskViewModel;
    private ManageTasksViewModel manageTasksViewModel;

    private ManageUsersViewModel manageUsersViewModel;
    private ManageBacklogViewModel manageBacklogViewModel;

    public LoginViewModel getLoginViewModel() {
        if (loginViewModel == null) loginViewModel = new LoginViewModel();
        return loginViewModel;
    }

    public AddProjectViewModel getAddProjectViewModel() {
        if (addProjectViewModel == null) addProjectViewModel = new AddProjectViewModel();
        return addProjectViewModel;
    }

    public EditProjectViewModel getEditProjectViewModel() {
        if (editProjectViewModel == null) editProjectViewModel = new EditProjectViewModel();
        return editProjectViewModel;
    }

    public ViewProjectViewModel getViewProjectViewModel() {
        if (viewProjectViewModel == null) viewProjectViewModel = new ViewProjectViewModel();
        return viewProjectViewModel;
    }

    public ManageProjectsViewModel getManageProjectsViewModel() {
        if (manageProjectsViewModel == null) manageProjectsViewModel = new ManageProjectsViewModel();
        return manageProjectsViewModel;
    }

    public AddSprintViewModel getAddSprintViewModel() {
        if (addSprintViewModel == null) addSprintViewModel = new AddSprintViewModel();
        return addSprintViewModel;
    }

    public EditSprintViewModel getEditSprintViewModel() {
        if (editSprintViewModel == null) editSprintViewModel = new EditSprintViewModel();
        return editSprintViewModel;
    }

    public ViewSprintViewModel getViewSprintViewModel() {
        if (viewSprintViewModel == null) viewSprintViewModel = new ViewSprintViewModel();
        return viewSprintViewModel;
    }

    public ManageSprintsViewModel getManageSprintsViewModel() {
        if (manageSprintsViewModel == null) manageSprintsViewModel = new ManageSprintsViewModel();
        return manageSprintsViewModel;
    }

    public AddTaskViewModel getAddTaskViewModel() {
        if (addTaskViewModel == null) addTaskViewModel = new AddTaskViewModel();
        return addTaskViewModel;
    }

    public EditTaskViewModel getEditTaskViewModel() {
        if (editTaskViewModel == null) editTaskViewModel = new EditTaskViewModel();
        return editTaskViewModel;
    }

    public ViewTaskViewModel getViewTaskViewModel() {
        if (viewTaskViewModel == null) viewTaskViewModel = new ViewTaskViewModel();
        return viewTaskViewModel;
    }

    public ManageTasksViewModel getManageTasksViewModel() {
        if (manageTasksViewModel == null) manageTasksViewModel = new ManageTasksViewModel();
        return manageTasksViewModel;
    }

    public ManageUsersViewModel getManageUsersViewModel() {
        if (manageUsersViewModel == null) manageUsersViewModel = new ManageUsersViewModel();
        return manageUsersViewModel;
    }

    public ManageBacklogViewModel getManageBacklogViewModel() {
        if (manageBacklogViewModel == null) manageBacklogViewModel = new ManageBacklogViewModel();
        return manageBacklogViewModel;
    }
}
