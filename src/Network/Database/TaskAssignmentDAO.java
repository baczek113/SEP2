package Network.Database;

import Network.Employee;
import Network.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskAssignmentDAO extends DAO{
    private static TaskAssignmentDAO instance;

    private TaskAssignmentDAO() throws SQLException {
        super();
    }

    public static TaskAssignmentDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new TaskAssignmentDAO();
        }
        return instance;
    }

    public void assignTask(Employee employee, Task task) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO task_assignment (user_id, task_id) VALUES (?, ?)");
            statement.setInt(1, employee.getEmployee_id());
            statement.setInt(2, task.getTask_id());
            statement.executeUpdate();
        }
    }

    public void unassignTask(Employee employee, Task task) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM task_assignment WHERE user_id = ? AND task_id = ?");
            statement.setInt(1, employee.getEmployee_id());
            statement.setInt(2, task.getTask_id());
            statement.executeUpdate();
        }
    }

    public ArrayList<Employee> getEmployeesTasked(Task task) throws SQLException {
        try(Connection connection = getConnection()) {
            ArrayList<Employee> employees = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement("SELECT employee.user_id, employee.role_id, employee.username, employee.password FROM task_assignment JOIN employee ON employee.user_id = task_assignment.user_id WHERE task_assignment.task_id = ?");
            statement.setInt(1, task.getTask_id());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                employees.add(new Employee(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4)));
            }
            return employees;
        }
    }
}
