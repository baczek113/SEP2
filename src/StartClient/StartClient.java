package StartClient;

import ClientModel.ClientModelManager;
import View.ViewFactory;
import View.ViewHandler;
import ViewModel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class StartClient extends Application {
    @Override
    public void start(Stage stage) {
        ClientModelManager manager = new ClientModelManager("localhost", 2137);
        ViewModelFactory viewModelFactory = new ViewModelFactory(manager);
        ViewFactory viewFactory = new ViewFactory(viewModelFactory);
        ViewHandler viewHandler = new ViewHandler(viewFactory);
        viewFactory.setViewHandler(viewHandler);
        stage.setOnCloseRequest(event -> {
           System.exit(0);
        });
        viewHandler.start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
