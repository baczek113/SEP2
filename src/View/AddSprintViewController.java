package View;

import ViewModel.AddProjectViewModel;
import ViewModel.AddSprintViewModel;

public class AddSprintViewController {
    private ViewHandler viewHandler;
    private AddSprintViewModel viewModel;


    public void init (ViewHandler viewHandler, AddSprintViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
    }

}
