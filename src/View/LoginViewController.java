package View;

import Model.Employee;
import ViewModel.LoginViewModel;
import javafx.application.Platform;
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
        if (viewModel.employeeGetLog() != null && viewModel.employeeGetLog().getRole().getRole_name().equals("admin"))
        {
            viewHandler.openView("ManageUser");
        } else if (viewModel.employeeGetLog() != null && (viewModel.employeeGetLog().getRole().getRole_name().equals("product_owner")|| viewModel.employeeGetLog().getRole().getRole_name().equals("scrum_master")|| viewModel.employeeGetLog().getRole().getRole_name().equals("developer")))
        {
            viewHandler.openView("ManageProjects");
        }
        else
        {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Role does not exist");
                alert.showAndWait();
            });
        }

        if(event.getNewValue() == null)
        {
            Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
            });
        }
        else
        {
            System.out.println(((Employee) event.getNewValue()).getRole().getRole_name());
        }
    }



}
