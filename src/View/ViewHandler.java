package View;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewHandler
{
    private Stage stage;
    private final ViewFactory viewFactory;

    public  ViewHandler (ViewFactory viewFactory)
    {
        this.viewFactory=viewFactory;
    }

    public void start (Stage stage)
    {
        this.stage=stage;
        openView("Login");
    }

    public void openView (String viewName)
    {
        Scene scene = viewFactory.load(viewName);
        stage.setTitle(viewName);
        stage.setScene(scene);
        stage.show();
    }


}
