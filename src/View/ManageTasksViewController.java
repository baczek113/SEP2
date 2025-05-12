package View;

import ViewModel.AddProjectViewModel;
import ViewModel.ManageTasksViewModel;

public class ManageTasksViewController
{
    private ViewHandler viewHandler;
    private ManageTasksViewModel viewModel;


    public void init (ViewHandler viewHandler, ManageTasksViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
    }
}
