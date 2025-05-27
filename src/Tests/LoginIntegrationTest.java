package Tests;

import ClientModel.ClientModelManager;
import DataModel.Employee;
import ViewModel.LoginViewModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginIntegrationTest {

    @Test
    public void testSuccessfulLogin() throws InterruptedException {
        ClientModelManager model = new ClientModelManager("localhost", 2137);
        LoginViewModel viewModel = new LoginViewModel(model);

        viewModel.login("admin", "admin");

        Thread.sleep(1000);
        assertNotNull(model.getLoggedEmployee());
        assertEquals("admin", model.getLoggedEmployee().getUsername());
    }
    @Test
    public void testFailedLogin() throws InterruptedException {
        ClientModelManager model = new ClientModelManager("localhost", 2137);
        LoginViewModel viewModel = new LoginViewModel(model);

        viewModel.login("wrong_user", "wrong_pass");

        Thread.sleep(1000);

        assertNull(model.getLoggedEmployee());
    }
}
