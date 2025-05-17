package View;

import Model.Employee;
import ViewModel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.PasswordField;

import java.beans.PropertyChangeEvent;


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
        viewModel.addListener("login", this::handleLoginResponse);
    }

   @FXML public void onLogin()
    {
        String username = name.getText();
        String password = name1.getText();

        viewModel.login(username, password);
    }

    public void handleLoginResponse(PropertyChangeEvent event)
    {
        if(event.getNewValue() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
        }
        else
        {
            System.out.println(((Employee) event.getNewValue()).getRole().getRole_name());
        }
    }



}
