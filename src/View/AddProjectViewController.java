package View;

import ViewModel.AddProjectViewModel;

public class AddProjectViewController {
    private ViewHandler viewHandler;
    private AddProjectViewModel viewModel;


    public void init (ViewHandler viewHandler, AddProjectViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
    }

}
