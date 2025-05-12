package View;

import ViewModel.ManageProjectsViewModel;

public class ManageProjectsViewController
{
    private ViewHandler viewHandler;
    private ManageProjectsViewModel viewModel;


    public void init (ViewHandler viewHandler, ManageProjectsViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
    }
}
