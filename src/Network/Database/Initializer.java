package Network.Database;

import Model.Employee;
import Model.Project;
import Model.Sprint;
import Model.Task;
import Network.Utils.EmployeeList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Initializer extends DAO{
    private static Initializer instance;

    private Initializer() throws SQLException {
        super();
    }

    public static Initializer getInstance() throws SQLException {
        if (instance == null) {
            instance = new Initializer();
        }
        return instance;
    }

    public ArrayList<Project> getProjects(EmployeeList employees) throws SQLException {
        try(Connection connection = getConnection()) {

            //Selects all projects
            ArrayList<Project> projects = new ArrayList<>();
            PreparedStatement projectStatement = connection.prepareStatement("SELECT * FROM project");
            ResultSet projectResults = projectStatement.executeQuery();
            while (projectResults.next()) {

                //Creates a project that will be prepared to get added to the returned ArrayList<>
                Project preparedProject = new Project(projectResults.getInt("project_id"),
                                employees.getEmployeeById(projectResults.getInt("created_by")),
                                projectResults.getString("name"),
                                projectResults.getString("description"),
                                projectResults.getString("status"),
                                projectResults.getDate("start_date"),
                                projectResults.getDate("end_date"));

                //Selects sprints belonging to the project
                PreparedStatement sprintStatement = connection.prepareStatement("SELECT * FROM sprint WHERE project_id = ?");
                sprintStatement.setInt(1, preparedProject.getProject_id());
                ResultSet sprintResults = sprintStatement.executeQuery();
                while (sprintResults.next()) {

                    //Sprint is initially prepared
                    Sprint preparedSprint = new Sprint(sprintResults.getInt("sprint_id"),
                            preparedProject,
                            sprintResults.getString("name"),
                            sprintResults.getDate("start_date"),
                            sprintResults.getDate("end_date"));

                    //Selects the tasks that are in the sprint
                    PreparedStatement sprintTasks = connection.prepareStatement("SELECT * FROM task WHERE sprint_id = ?");
                    sprintTasks.setInt(1, preparedSprint.getSprint_id());
                    ResultSet sprintTasksResults = sprintTasks.executeQuery();

                    while (sprintTasksResults.next())
                    {
                        //Task is initially prepared
                        Task preparedTask = new Task(sprintTasksResults.getInt("task_id"),
                            preparedSprint,
                            employees.getEmployeeById(sprintTasksResults.getInt("created_by")),
                            sprintTasksResults.getString("title"),
                            sprintTasksResults.getString("description"),
                            sprintTasksResults.getString("status"),
                            sprintTasksResults.getInt("priority"));

                        //Selects the employees assigned to the task
                        PreparedStatement taskAssigneesStatement = connection.prepareStatement("SELECT employee_id FROM task_assignment WHERE task_id = ?");
                        taskAssigneesStatement.setInt(1, preparedTask.getTask_id());
                        ResultSet taskAssigneesResults = taskAssigneesStatement.executeQuery();

                        //Adds the employees assigned to the task to it
                        while (taskAssigneesResults.next()) {
                            preparedTask.assignTo(employees.getEmployeeById(taskAssigneesResults.getInt("employee_id")));
                        }

                        //The task is ready so it is finally added to the sprint
                        preparedSprint.addTask(preparedTask);
                    }

                    //The sprint is ready so it is finally added to the project
                    preparedProject.addSprint(preparedSprint);
                }

                //Selects the employees assigned to the project
                PreparedStatement projectAssignees = connection.prepareStatement("SELECT employee_id FROM project_assignment WHERE project_id = ?");
                projectAssignees.setInt(1, projectResults.getInt("project_id"));
                ResultSet projectAssigneesResults = projectAssignees.executeQuery();

                //Adds the employees to the project
                while (projectAssigneesResults.next()) {
                    preparedProject.addEmployee(employees.getEmployeeById(projectAssigneesResults.getInt("employee_id")));
                }
                //Adds the complete project to the list
                projects.add(preparedProject);
            }
            return projects;
        }
    }

    public EmployeeList getEmployees() throws SQLException {
        try(Connection connection = getConnection()) {
            EmployeeList employees = new EmployeeList();
            PreparedStatement statement = connection.prepareStatement("SELECT employee_id, role_id, username FROM employee");
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                employees.add(new Employee(results.getInt("employee_id"), results.getInt("role_id"), results.getString("username")));
            }
            return employees;
        }
    }
}
