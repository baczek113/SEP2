package View;

import ViewModel.AddProjectViewModel;
import ViewModel.EditProjectViewModel;

public class EditProjectViewController {
    private ViewHandler viewHandler;
    private EditProjectViewModel viewModel;


    public void init (ViewHandler viewHandler, EditProjectViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
    }
}
