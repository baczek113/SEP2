package View;

import ViewModel.AddProjectViewModel;
import ViewModel.EditProjectViewModel;
import ViewModel.EditSprintViewModel;

public class EditSprintViewController
{
    private ViewHandler viewHandler;
    private EditSprintViewModel viewModel;


    public void init (ViewHandler viewHandler, EditSprintViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
    }
}
