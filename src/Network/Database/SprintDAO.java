//package Network.Database;
//
//import Model.Project;
//import Model.Sprint;
//
//import java.sql.*;
//import java.util.ArrayList;
//
//public class SprintDAO extends DAO{
//    private static SprintDAO instance;
//
//    private SprintDAO() throws SQLException {
//        super();
//    }
//
//    public static SprintDAO getInstance() throws SQLException {
//        if (instance == null) {
//            instance = new SprintDAO();
//        }
//        return instance;
//    }
//
//    public Sprint addSprint(Project project, String name, Date startDate, Date endDate) throws SQLException {
//        try(Connection connection = getConnection())
//        {
//            PreparedStatement statement = connection.prepareStatement("INSERT INTO sprint(project_id, name, start_date, end_date) VALUES (?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
//            statement.setInt(1, project.getProject_id());
//            statement.setString(2, name);
//            statement.setDate(3, startDate);
//            statement.setDate(4, endDate);
//            statement.executeUpdate();
//            ResultSet keys = statement.getGeneratedKeys();
//
//            if(keys.next())
//            {
//                return new Sprint(keys.getInt(1), project.getProject_id(), name, startDate, endDate);
//            }
//            else
//            {
//                return null;
//            }
//        }
//    }
//
//    public void removeSprint(Sprint sprint) throws SQLException {
//        try(Connection connection = getConnection())
//        {
//            PreparedStatement statement = connection.prepareStatement("DELETE FROM sprint WHERE sprint_id = ?");
//            statement.setInt(1, sprint.getSprint_id());
//            statement.executeUpdate();
//        }
//    }
//
//    public void updateSprint(Sprint sprint) throws SQLException {
//        try(Connection connection = getConnection())
//        {
//            PreparedStatement statement = connection.prepareStatement("UPDATE sprint SET name = ?, start_date = ?, end_date = ? WHERE sprint_id = ?");
//            statement.setString(1, sprint.getName());
//            statement.setDate(2, sprint.getStart_date());
//            statement.setDate(3, sprint.getEnd_date());
//            statement.setInt(4, sprint.getSprint_id());
//            statement.executeUpdate();
//        }
//    }
//
//    public ArrayList<Sprint> getSprints(Project project) throws SQLException {
//        try(Connection connection = getConnection()) {
//            ArrayList<Sprint> sprints = new ArrayList<>();
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM sprint WHERE project_id = ?");
//            statement.setInt(1, project.getProject_id());
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                sprints.add(new Sprint(resultSet.getInt("sprint_id"), resultSet.getInt("project_id"), resultSet.getString("name"), resultSet.getDate("start_date"), resultSet.getDate("end_date")));
//            }
//            return sprints;
//        }
//    }
//}
