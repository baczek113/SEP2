package View;

import ViewModel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.PasswordField;

public class LoginViewController
{
   @FXML private TextField name;
   @FXML private PasswordField name1;
   @FXML private Label message;
   @FXML private Button button;

    private ViewHandler viewHandler;
    private LoginViewModel viewModel;

    public void init (ViewHandler viewHandler, LoginViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;

        message.textProperty().bind(viewModel.messageProperty());
    }

   @FXML public void onLogin()
    {
        String username = name.getText();
        String password = name1.getText();

        viewModel.login(username, password);
    }



}
