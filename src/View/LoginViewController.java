package View;

import ViewModel.LoginViewModel;
import javafx.fxml.FXML;

import java.awt.*;

public class LoginViewController
{
    @FXML private TextField name;
    @FXML private TextField name1;
    @FXML private Label message;
    @FXML private Button button;

    private ViewHandler viewHandler;
    private LoginViewModel viewModel;

    public void init (ViewHandler viewHandler, LoginViewModel viewModel)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
    }

    private void onLogin ()
    {
        String username = name.getText();
        String password = name1.getText();

        if (username.equals("admin")&&password.equals("admin"))
        {
            viewHandler.openView("ManageUsers");
        } else if (username.equals("user")&&password.equals("user")) {
            viewHandler.openView("ManageProjects");
        }
        else
        {
            message.setText("Invalid username or password.");
        }

    }



}
