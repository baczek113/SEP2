package Network.Database;

import Network.Project;
import Network.Sprint;

import java.sql.*;

public class SprintDAO extends DAO{
    private static SprintDAO instance;

    private SprintDAO() throws SQLException {
        super();
    }

    public static SprintDAO getInstance() throws SQLException {
        if (instance == null) {
            instance = new SprintDAO();
        }
        return instance;
    }

    public Sprint addSprint(Project project, String name, Date startDate, Date endDate) throws SQLException {
        try(Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO sprint(project_id, name, start_date, end_date) VALUES (?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, project.getProject_id());
            statement.setString(2, name);
            statement.setDate(3, startDate);
            statement.setDate(4, endDate);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();

            if(keys.next())
            {
                return new Sprint(keys.getInt(1), project.getProject_id(), name, startDate, endDate);
            }
            else
            {
                return null;
            }
        }
    }

    public void removeSprint(Sprint sprint) throws SQLException {
        try(Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM sprint WHERE sprint_id = ?");
            statement.setInt(1, sprint.getSprint_id());
            statement.executeUpdate();
        }
    }

    public void updateSprint(Sprint sprint) throws SQLException {
        try(Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("UPDATE sprint SET name = ?, start_date = ?, end_date = ? WHERE sprint_id = ?");
            statement.setString(1, sprint.getName());
            statement.setDate(2, sprint.getStart_date());
            statement.setDate(3, sprint.getEnd_date());
            statement.setInt(4, sprint.getSprint_id());
            statement.executeUpdate();
        }
    }
}
