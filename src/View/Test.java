package View;

import View.ViewFactory;
import View.ViewHandler;
import ViewModel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class Test extends Application {
    @Override
    public void start(Stage stage) {
        ViewModelFactory viewModelFactory = new ViewModelFactory();
        ViewFactory viewFactory = new ViewFactory(null, viewModelFactory);
        ViewHandler viewHandler = new ViewHandler(viewFactory);

        viewHandler.start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
