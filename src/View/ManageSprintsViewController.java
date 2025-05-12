package View;

import ViewModel.AddProjectViewModel;
import ViewModel.ManageProjectsViewModel;
import ViewModel.ManageSprintsViewModel;

public class ManageSprintsViewController {
    private ViewHandler viewHandler;
    private ManageSprintsViewModel viewModel;


    public void init (ViewHandler viewHandler, ManageSprintsViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
    }
}
