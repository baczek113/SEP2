package View;

import ViewModel.AddProjectViewModel;
import ViewModel.AddTaskViewModel;

public class AddTaskViewController
{
    private ViewHandler viewHandler;
    private AddTaskViewModel viewModel;


    public void init (ViewHandler viewHandler, AddTaskViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
    }
}
