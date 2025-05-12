package View;

import ViewModel.AddProjectViewModel;
import ViewModel.EditTaskViewModel;

public class EditTaskViewController
{
    private ViewHandler viewHandler;
    private EditTaskViewModel viewModel;


    public void init (ViewHandler viewHandler, EditTaskViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
    }
}
