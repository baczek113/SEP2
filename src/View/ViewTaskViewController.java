package View;

import ViewModel.AddProjectViewModel;
import ViewModel.ViewTaskViewModel;

public class ViewTaskViewController
{
    private ViewHandler viewHandler;
    private ViewTaskViewModel viewModel;


    public void init (ViewHandler viewHandler, ViewTaskViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
    }
}
