package View;


import ViewModel.AddProjectViewModel;
import ViewModel.ViewSprintViewModel;
import javafx.application.Application;
import javafx.stage.Stage;

public class ViewSprintViewController {
    private ViewHandler viewHandler;
    private ViewSprintViewModel viewModel;


    public void init (ViewHandler viewHandler, ViewSprintViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
    }

}
