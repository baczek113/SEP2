package View;

import javafx.application.Platform;
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
        Platform.runLater(() -> {
            Scene scene = viewFactory.load(viewName, null);
            stage.setTitle(viewName);
            stage.setScene(scene);
            stage.show();
        });
    }

    public void openView (String viewName, Object obj)
    {
        Platform.runLater(() -> {
            Scene scene = viewFactory.load(viewName, obj);
            stage.setTitle(viewName);
            stage.setScene(scene);
            stage.show();
        });
    }


}
