package Tests;

import DataModel.Employee;
import DataModel.Role;
import DataModel.RoleFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeTest {

    private Role mockRole;
    private RoleFactory mockFactory;

    @BeforeEach
    public void setup() throws SQLException {
        mockRole = new Role(1, "Admin");
        mockFactory = mock(RoleFactory.class);
        when(mockFactory.getRole(1)).thenReturn(mockRole);
        when(mockFactory.getRole(2)).thenReturn(new Role(2, "Developer"));
        RoleFactory.setInstance(mockFactory);
    }

    @Test
    public void testCreateEmployee() throws SQLException {
        Employee emp = new Employee(100, 1, "john_doe");

        assertEquals(100, emp.getEmployee_id());
        assertEquals("john_doe", emp.getUsername());
        assertEquals("active", emp.getStatus());
        assertEquals(mockRole, emp.getRole());
    }

    @Test
    public void testActivateDeactivate() throws SQLException {
        Employee emp = new Employee(101, 1, "jane");
        emp.deativate();
        assertEquals("inactiveactive", emp.getStatus());
        emp.activate();
        assertEquals("active", emp.getStatus());
    }

    @Test
    public void testSetUsername() throws SQLException {
        Employee emp = new Employee(102, 1, "temp");
        emp.setUsername("final");
        assertEquals("final", emp.getUsername());
    }

    @Test
    public void testSetRole() throws SQLException {
        Employee emp = new Employee(103, 1, "user");
        Role newRole = new Role(2, "Developer");
        emp.setRole(newRole);
        assertEquals(newRole, emp.getRole());
    }

    @Test
    public void testNullRoleHandled() throws SQLException {
        RoleFactory nullFactory = mock(RoleFactory.class);
        when(nullFactory.getRole(999)).thenReturn(null);
        RoleFactory.setInstance(nullFactory);

        Employee emp = new Employee(105, 999, "norole");
        assertNull(emp.getRole());
    }


    @Test
    public void testCreateEmployeeWithEmptyUsername() throws SQLException {
        Employee emp = new Employee(106, 1, "");
        assertEquals("", emp.getUsername());
    }

    @Test
    public void testCreateEmployeeWithVeryLongUsername() throws SQLException {
        String longUsername = "a".repeat(300);
        Employee emp = new Employee(107, 1, longUsername);
        assertEquals(longUsername, emp.getUsername());
    }

    @Test
    public void testDifferentRolesAssigned() throws SQLException {
        Employee emp = new Employee(108, 2, "manager_user");
        Role expectedRole = mockFactory.getRole(2);
        assertEquals(expectedRole, emp.getRole());
        assertEquals("Developer", expectedRole.getRole_name());
    }

    @Test
    public void testManyEmployeesCreated() throws SQLException {
        for (int i = 0; i < 50; i++) {
            Employee emp = new Employee(i, 1, "user" + i);
            assertEquals(i, emp.getEmployee_id());
            assertEquals("user" + i, emp.getUsername());
            assertEquals(mockRole, emp.getRole());
        }
    }
    @Test
    public void testInvalidRoleThrowsRuntimeException() {
        RoleFactory faultyFactory = mock(RoleFactory.class);
        doThrow(new RuntimeException("Mocked runtime error")).when(faultyFactory).getRole(anyInt());

        RoleFactory.setInstance(faultyFactory);

        assertThrows(RuntimeException.class, () -> {
            new Employee(200, 3, "user");
        });
    }


}
