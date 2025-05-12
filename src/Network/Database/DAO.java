package Network.Database;

import Model.Employee;
import Model.Project;
import Model.Sprint;
import Model.Task;

import java.sql.*;

public class DAO {
    private static DAO instance;

    private DAO() throws SQLException
    {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    private Connection getConnection() throws SQLException
    {
        //Substitute for your own database login/password
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=sep_database", "postgres", "");
    }

    public static DAO getInstance() throws SQLException
    {
        if(instance == null){
            instance = new DAO();
        }
        return instance;
    }

    public Employee addEmployee(int role_id, String username, String password) {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO employee(role_id, username, password) VALUES (?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, role_id);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();

            if(keys.next()) {
                return new Employee(keys.getInt(1), role_id, username);
            }
            else {
                return null;
            }
        }catch (SQLException e){
            return null;
        }
    }

    public Project addProject(Employee created_by, String name, String description) {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO project (created_by, name, description, status, start_date, end_date) VALUES (?, ?, ?, 'pending', NULL, NULL)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, created_by.getEmployee_id());
            statement.setString(2, name);
            statement.setString(3, description);

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return new Project(resultSet.getInt(1), created_by, name, description, "pending", null, null);
            }
            else
            {
                return null;
            }
        }catch (SQLException e){
            return null;
        }
    }

    public void endProject(Project project) {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE project SET status = 'finished', end_date = NOW() WHERE project_id = ?");
            statement.setInt(1, project.getProject_id());
            statement.executeUpdate();
            project.setStatus("finished");
            project.setEnd_date(new Date(System.currentTimeMillis()));
        }catch (SQLException e){
            System.out.println("");
        }
    }

    public void editEmployee(Employee employee){
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE employee SET role_id = ?, username = ? WHERE employee_id = ?");
            statement.setInt(1, employee.getRole().getRole_id());
            statement.setString(2, employee.getUsername());
            statement.setInt(3, employee.getEmployee_id());
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println("Cos sie zesralo");
        }
    }

    public void removeEmployee(Employee employee) {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM employee WHERE employee_id = ?");
            statement.setInt(1, employee.getEmployee_id());
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println("Coś się zesrało");
        }
    }

    public void addEmployeeToProject(Project project, Employee employee) {
        try(Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO project_assignment (user_id, project_id) VALUES (?,?)");
            statement.setInt(1, employee.getEmployee_id());
            statement.setInt(2, project.getProject_id());
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println("Failed to ");
        }
    }

    public void removeEmployeeFromProject(Project project, Employee employee){
        // DELETE FROM task_assignment WHERE employee_id = 2;
    }

    public Task addTask(Sprint sprint, Employee createdBy, String title, String description, int priority) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO task(sprint_id, created_by, project_id, title, description, status, priority) VALUES (?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, sprint.getSprint_id());
            statement.setInt(2, createdBy.getEmployee_id());
            statement.setInt(3, sprint.getProject_id());
            statement.setString(4, title);
            statement.setString(5, description);
            statement.setString(6, "pending");
            statement.setInt(7, priority);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();

            if(keys.next())
            {
                return new Task(keys.getInt(1), sprint.getSprint_id(), createdBy.getEmployee_id(), sprint.getProject_id(), title, description, "to-do", priority);
            }
            else
            {
                return null;
            }
        }
    }

    public void assignTask(Employee employee, Task task){

    }














}
