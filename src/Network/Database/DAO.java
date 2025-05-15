package Network.Database;

import Model.Employee;
import Model.Project;
import Model.Sprint;
import Model.Task;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class DAO {
    private static DAO instance;

    private DAO() throws SQLException
    {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    public Connection getConnection() throws SQLException
    {
        //Substitute for your own database login/password
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=sep_database", "postgres", "gigakoks1");
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
            PreparedStatement statement = connection.prepareStatement("INSERT INTO employee(role_id, username, password, status) VALUES (?,?,?,'active')", PreparedStatement.RETURN_GENERATED_KEYS);
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
            throw new RuntimeException(e);
        }
    }

    public void removeEmployee(Employee employee) {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM employee WHERE employee_id = ?");
            statement.setInt(1, employee.getEmployee_id());
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println("failed to remove employee: " + employee.getUsername());
            throw new RuntimeException(e);
        }
    }

    public void addEmployeeToProject(Project project, Employee employee) {
        try(Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO project_assignment (employee_id, project_id) VALUES (?,?)");
            statement.setInt(1, employee.getEmployee_id());
            statement.setInt(2, project.getProject_id());
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println("failed to add employee " + employee.getUsername().toUpperCase() + " to project: " + employee.getUsername());
            throw new RuntimeException(e);
        }
    }

    public Project addProject(Employee created_by, Employee scrum_master, String name, String description, Date startDate, Date endDate, List<Employee> participants) {
        try(Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO project (created_by, scrum_master, name, description, status, start_date, end_date) VALUES (?, ?, ?, ?, 'pending', ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, created_by.getEmployee_id());
            statement.setInt(2, scrum_master.getEmployee_id());
            statement.setString(3, name);
            statement.setString(4, description);
            statement.setDate(5, startDate);
            statement.setDate(6, endDate);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int project_id = resultSet.getInt(1);
                Project preparedProject = new Project(project_id, created_by, scrum_master, name, description, "pending", null, null);
                if(participants != null) {
                    for (Employee employee : participants) {
                        PreparedStatement assignEmployee = connection.prepareStatement("INSERT INTO project_assignment (employee_id, project_id) VALUES (?, ?)");
                        assignEmployee.setInt(1, employee.getEmployee_id());
                        assignEmployee.setInt(2, project_id);
                        assignEmployee.executeUpdate();

                        preparedProject.addEmployee(employee);
                    }
                }
                connection.commit();
                return preparedProject;
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

    public void editProject(Project project)
    {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE project SET name = ?, description = ? WHERE project_id = ?");
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void startProject(Project project) {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE project SET status = 'ongoing', start_date = NOW() WHERE project_id = ?");
            statement.setInt(1, project.getProject_id());
            statement.executeUpdate();
            project.setStatus("ongoing");
            project.setStart_date(new Date(System.currentTimeMillis()));
        }catch (SQLException e){
            System.out.println("failed to set status to 'ongoing' for a project: " + project.getName());
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    public void editTask(Task task) {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE task SET description = ?, title = ? WHERE task_id = ?");
            statement.setString(1, task.getDescription());
            statement.setString(2, task.getTitle());
            statement.setInt(3, task.getTask_id());
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void removeEmployeeFromProject(Project project, Employee employee){
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("DELETE FROM project_assignment WHERE employee_id = ? AND project_id = ?");
            statement.setInt(1, employee.getEmployee_id());
            statement.setInt(2, project.getProject_id());
            statement.executeUpdate();

            for(Task task : project.getBacklog()) {
                if(task.getAssignedTo().get(employee.getEmployee_id()) != null) {
                    PreparedStatement removeFromTasks = connection.prepareStatement("DELETE FROM task_assignment WHERE task_id = ? AND employee_id = ?");
                    removeFromTasks.setInt(1, task.getTask_id());
                    removeFromTasks.setInt(2, employee.getEmployee_id());
                    removeFromTasks.executeUpdate();
                }
            }
        }catch (SQLException e){
            System.out.println("failed to remove employee " + employee.getUsername().toUpperCase() + " from project " + project.getName().toUpperCase());
            throw new RuntimeException(e);
        }

    }

    public Task addTask(Sprint sprint, Project project, String title, String description, int priority) {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO task(sprint_id, project_id, title, description, status, priority) VALUES (?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            if(sprint != null) {
                statement.setInt(1, sprint.getSprint_id());
            }
            else
            {
                statement.setInt(1, java.sql.Types.INTEGER);
            }
            statement.setInt(2, project.getProject_id());
            statement.setString(3, title);
            statement.setString(4, description);
            statement.setString(5, "to-do");
            statement.setInt(6, priority);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();

            if(keys.next())
            {
                int sprintIdForModel = 0; // Or whatever your Task model uses for "no sprint"
                if (sprint != null) {
                    sprintIdForModel = sprint.getSprint_id();
                }
                return new Task(keys.getInt(1), sprintIdForModel, project.getProject_id(), title, description, "to-do", priority);
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
            throw new RuntimeException(e);
        }
    }

    public void unAssignTask(Employee employee, Task task){
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("DELETE FROM task_assignment WHERE employee_id = ? AND task_id = ?");
            statement.setInt(1, employee.getEmployee_id());
            statement.setInt(2, task.getTask_id());
            statement.executeUpdate();
        }catch (SQLException e){
            System.out.println("failed to unassign task " + task.getTitle().toUpperCase() + " to employee " + employee.getUsername().toUpperCase());
            throw new RuntimeException(e);
        }
    }

    public void assignPriority(Task task, int priority){
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE task SET priority = ? WHERE task_id = ?");
            statement.setInt(1, priority);
            statement.setInt(2, task.getTask_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("failed to assign priority to a task " + task.getTitle().toUpperCase());
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    public Sprint addSprint(Project project, String name, Date start_date, Date end_date){
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO sprint(project_id, name, start_date, end_date) VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
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

    public HashMap<Integer, String> getAllRoles()
    {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM role");
            ResultSet results = statement.executeQuery();
            HashMap<Integer, String> roles = new HashMap<Integer, String>();

            while(results.next()) {
                roles.put(results.getInt("role_id"), results.getString("role_name"));
            }
            return roles;
        } catch (SQLException e) {
            System.out.println("Failed to retrieve roles");
            throw new RuntimeException(e);
        }
    }

    public Employee login(String username, String password)
    {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT employee_id, role_id, status FROM employee WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet results = statement.executeQuery();

            if(results.next()) {
                Employee employee = new Employee(results.getInt("employee_id"), results.getInt("role_id"), username);
                if(results.getString("status").equals("inactive"))
                {
                    employee.deativate();
                }
                return employee;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Login failed");
            return null;
        }
    }

    public void deactivateEmployee(Employee employee)
    {
        try(Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("UPDATE employee SET status = 'inactive' WHERE employee_id = ?");
            statement.setInt(1, employee.getEmployee_id());
            statement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Failed to deactivate employee");
            throw new RuntimeException(e);
        }
    }

    public void activateEmployee(Employee employee)
    {
        try(Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("UPDATE employee SET status = 'active' WHERE employee_id = ?");
            statement.setInt(1, employee.getEmployee_id());
            statement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Failed to activate employee");
            throw new RuntimeException(e);
        }
    }

    public void addTaskToSprint(Task task, Sprint sprint)
    {
        try(Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("UPDATE task SET sprint_id = ? WHERE task_id = ?");
            statement.setInt(1, sprint.getSprint_id());
            statement.setInt(2, task.getTask_id());
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void removeTaskFromSprint(Task task, Sprint sprint)
    {
        try(Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("UPDATE task SET sprint_id = 0 WHERE task_id = ?");
            statement.setInt(1, task.getTask_id());
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void editSprint(Sprint sprint)
    {
        try(Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("UPDATE sprint SET name = ?, start_date = ?, end_date = ? WHERE sprint_id = ?");
            statement.setString(1, sprint.getName());
            statement.setDate(2, sprint.getStart_date());
            statement.setDate(3, sprint.getEnd_date());
            statement.setInt(4, sprint.getSprint_id());
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}