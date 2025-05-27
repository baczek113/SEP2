package Tests;

import ClientModel.ClientModelManager;
import DataModel.Employee;
import ViewModel.ManageUsersViewModel;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ManageUsersViewModelTest {

    private ClientModelManager mockModel;
    private ManageUsersViewModel viewModel;
    private Employee emp1;
    private Employee emp2;

    @BeforeEach
    void setUp() throws SQLException {
        mockModel = mock(ClientModelManager.class);
        emp1 = new Employee(1, 1, "Alice");
        emp2 = new Employee(2, 2, "Bob");

        when(mockModel.getEmployees()).thenReturn(Arrays.asList(emp1, emp2));
        viewModel = new ManageUsersViewModel(mockModel);
    }

    @Test
    void testGetEmployeesReturnsObservableList() {
        ObservableList<Employee> list = viewModel.getEmployees();
        assertEquals(2, list.size());
        assertTrue(list.contains(emp1));
        assertTrue(list.contains(emp2));
    }

    @Test
    void testAddEmployeeDelegatesToModel() {
        viewModel.addEmployee("newUser", "password", 1);
        verify(mockModel).createEmployee("newUser", "password", 1);
    }

    @Test
    void testDeactivateEmployeeDelegatesToModel() {
        viewModel.deactivateEmployee(emp1);
        verify(mockModel).deactivateEmployee(emp1);
    }

    @Test
    void testActivateEmployeeDelegatesToModel() {
        viewModel.activateEmployee(emp2);
        verify(mockModel).activateEmployee(emp2);
    }

    @Test
    void testUpdateEmployeeCallsModelEdit() throws SQLException {
        viewModel.updateEmployee(emp1, "UpdatedAlice", 2);
        verify(mockModel).editEmployee(any(Employee.class));
    }

    @Test
    void testValidateInputBlankUsername() {
        String error = viewModel.validateInput("   ", "pass", "1", false, null);
        assertEquals("Please fill in all fields.", error);
    }

    @Test
    void testValidateInputBlankPasswordWhenNotEditing() {
        String error = viewModel.validateInput("NewUser", "", "1", false, null);
        assertEquals("Please enter a password.", error);
    }

    @Test
    void testValidateInputNullRole() {
        String error = viewModel.validateInput("NewUser", "pass", null, false, null);
        assertEquals("Please fill in all fields.", error);
    }

    @Test
    void testValidateInputUsernameExistsInCreateMode() {
        String error = viewModel.validateInput("Alice", "pass", "1", false, null);
        assertEquals("Username already exists.", error);
    }

    @Test
    void testValidateInputUsernameExistsButSameUserInEditMode() {
        String error = viewModel.validateInput("Alice", "pass", "1", true, emp1);
        assertNull(error);  // allowed if editing self
    }

    @Test
    void testValidateInputUsernameExistsForDifferentUserInEditMode() {
        String error = viewModel.validateInput("Bob", "pass", "1", true, emp1);
        assertEquals("Username already exists.", error);
    }

    @Test
    void testValidateInputValidNewUser() {
        String error = viewModel.validateInput("NewGuy", "123", "1", false, null);
        assertNull(error);
    }

    @Test
    void testLogOutDelegatesToModel() {
        viewModel.logOut();
        verify(mockModel).logOut();
    }
}

