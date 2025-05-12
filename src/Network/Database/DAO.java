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
            System.out.println("failed to add a employee " + username.toUpperCase());
            return null;
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
            System.out.println("failed to edit employee: " + employee.getUsername());
        }
    }

    public void removeEmployee(Employee employee) {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM employee WHERE employee_id = ?");
            statement.setInt(1, employee.getEmployee_id());
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println("failed to remove employee: " + employee.getUsername());
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
            System.out.println("failed to add employee " + employee.getUsername().toUpperCase() + " to project: " + employee.getUsername());
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
            System.out.println("failed to add a PROJECT" + name);
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
            System.out.println("failed to set status to 'finish' for a project: " + project.getName());
        }
    }




    public void removeEmployeeFromProject(Project project, Employee employee){
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("DELETE FROM task_assignment WHERE employee_id = ? AND project_id = ?");
            statement.setInt(1, employee.getEmployee_id());
            statement.setInt(2, project.getProject_id());
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println("failed to remove employee " + employee.getUsername().toUpperCase() + " from project " + project.getName().toUpperCase());
        }

    }

    public Task addTask(Sprint sprint, Project project, String title, String description, int priority) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO task(sprint_id, project_id, title, description, status, priority) VALUES (?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, sprint.getSprint_id());
            statement.setInt(2, project.getProject_id());
            statement.setInt(3, sprint.getProject_id());
            statement.setString(4, title);
            statement.setString(5, description);
            statement.setString(6, "pending");
            statement.setInt(7, priority);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();

            if(keys.next())
            {
                return new Task(keys.getInt(1), sprint.getSprint_id(), project.getProject_id(), title, description, "to-do", priority);
            }
            else
            {
                return null;
            }
        }catch (SQLException e){
            System.out.println("failed to add a task: " + title);
            return null;
        }
    }

    public void assignTask(Employee employee, Task task){
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO task_assignment(employee_id, task_id) VALUES (?, ?)");
            statement.setInt(1, employee.getEmployee_id());
            statement.setInt(2, task.getTask_id());
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println("failed to assign task " + task.getTitle().toUpperCase() + " to employee " + employee.getUsername().toUpperCase());
        }
    }

    public void assignPriority(Employee employee, Task task, int priority){ // employee is kinda useless here
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE task SET priority = ? WHERE task_id = ?");
            statement.setInt(1, priority);
            statement.setInt(2, task.getTask_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("failed to assign priority to a task " + task.getTitle().toUpperCase());
        }
    }

    public void changeTaskStatus(Task task, String status){
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE task SET status = ? WHERE task_id = ?");
            statement.setString(1, status);
            statement.setInt(2, task.getTask_id());
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println("failed to change status of a task " + task.getTitle() );
        }
    }

    public Sprint addSprint(Project project, String name, Date start_date, Date end_date){
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO sprint(project_id, name, start_date, end_date) VALUES (?, ?, ?, ?)");
            statement.setInt(1, project.getProject_id());
            statement.setString(2, name);
            statement.setDate(3, start_date);
            statement.setDate(4, end_date);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if(keys.next())
            {
                return new Sprint(keys.getInt(1), project.getProject_id(), name, start_date, end_date);
            }
            else
            {
                return null;
            }
        }catch (SQLException e){
            System.out.println("failed to add sprint: " + name.toUpperCase());
            return null;
        }
    }
}