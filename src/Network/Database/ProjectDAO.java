package Network.Database;

import Model.Employee;
import Model.Project;

import java.sql.*;
import java.util.ArrayList;

public class ProjectDAO extends DAO{
    private static ProjectDAO instance;
    private ProjectDAO() throws SQLException {
        super();
    }

    public static ProjectDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProjectDAO();
        }
        return instance;
    }

    public ArrayList<Project> getProjects(Employee created_by) throws SQLException {
        try(Connection connection = getConnection()) {
            ArrayList<Project> projects = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM project WHERE created_by = ?");
            statement.setInt(1, created_by.getEmployee_id());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                projects.add(new Project(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getDate(6), resultSet.getDate(7)));
            }
            return projects;
        }
    }

    public Project addProject(Employee created_by, String name, String description) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO project (created_by, name, description, status, start_date, end_date) VALUES (?, ?, ?, 'pending', NULL, NULL)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, created_by.getEmployee_id());
            statement.setString(2, name);
            statement.setString(3, description);

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return new Project(resultSet.getInt(1), created_by.getEmployee_id(), name, description, "pending", null, null);
            }
            else
            {
                return null;
            }
        }
    }

    public void startProject(Project project) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE project SET status = 'ongoing', start_date = NOW() WHERE project_id = ?");
            statement.setInt(1, project.getProject_id());
            statement.executeUpdate();
            project.setStatus("ongoing");
            project.setStart_date(new Date(System.currentTimeMillis()));
        }
    }

    public void endProject(Project project) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE project SET status = 'finished', end_date = NOW() WHERE project_id = ?");
            statement.setInt(1, project.getProject_id());
            statement.executeUpdate();
            project.setStatus("finished");
            project.setEnd_date(new Date(System.currentTimeMillis()));
        }
    }
}
