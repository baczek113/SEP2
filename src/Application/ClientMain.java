package Application;

import ClientModel.ClientConnection;
import ClientModel.ClientModelManager;
import ClientModel.Requests.LoginRequest;
import View.ViewHandler;
import ViewModel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientMain extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        ClientModelManager model = new ClientModelManager("localhost", 2910);
        
        LoginRequest loginRequest = new LoginRequest("login", null, "", "");
      
        ClientConnection client = new ClientConnection("localhost", 2910, model, loginRequest);
        model.setClientConnection(client);
        
        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);
        
        viewHandler.start(primaryStage);
        
        Thread clientThread = new Thread(client);
        clientThread.setDaemon(true);
        clientThread.start();
    }
} 