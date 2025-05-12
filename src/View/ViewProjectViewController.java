package View;

import ViewModel.AddProjectViewModel;
import ViewModel.ViewProjectViewModel;

public class ViewProjectViewController {
    private ViewHandler viewHandler;
    private ViewProjectViewModel viewModel;


    public void init (ViewHandler viewHandler, ViewProjectViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
    }
}
