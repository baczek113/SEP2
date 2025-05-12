package View;

import ViewModel.AddProjectViewModel;
import ViewModel.ManageUsersViewModel;

public class ManageUsersViewController {
    private ViewHandler viewHandler;
    private ManageUsersViewModel viewModel;


    public void init (ViewHandler viewHandler, ManageUsersViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
    }
}
