package View;

import ViewModel.AddProjectViewModel;
import ViewModel.ManageBacklogViewModel;
import ViewModel.ManageTasksViewModel;

public class ManageBacklogViewController
{
    private ViewHandler viewHandler;
    private ManageBacklogViewModel viewModel;


    public void init (ViewHandler viewHandler, ManageBacklogViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
    }
}
