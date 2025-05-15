package View;

import ViewModel.ViewModelFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewHandler
{
    private Stage stage;
    private final ViewModelFactory viewModelFactory;

    public  ViewHandler (ViewModelFactory viewModelFactory)
    {
        this.viewModelFactory = viewModelFactory;
    }

    public void start (Stage stage)
    {
        this.stage=stage;
        openView("Login");
    }

    public void openView (String viewName)
    {
        Scene scene = viewModelFactory.load(viewName);
        stage.setTitle(viewName);
        stage.setScene(scene);
        stage.show();
    }


}
