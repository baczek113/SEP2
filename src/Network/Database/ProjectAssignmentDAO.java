//package Network.Database;
//
//import Model.Employee;
//import Model.Project;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class ProjectAssignmentDAO extends DAO{
//    private static ProjectAssignmentDAO instance;
//
//    private ProjectAssignmentDAO() throws SQLException {
//        super();
//    }
//
//    public static ProjectAssignmentDAO getInstance() throws SQLException {
//        if(instance == null){
//            instance = new ProjectAssignmentDAO();
//        }
//        return instance;
//    }
//
//    public void assignEmployee(Project project, Employee employee) throws SQLException {
//        try(Connection connection = getConnection())
//        {
//            PreparedStatement statement = connection.prepareStatement("INSERT INTO project_assignment (user_id, project_id) VALUES (?,?)");
//            statement.setInt(1, employee.getEmployee_id());
//            statement.setInt(2, project.getProject_id());
//            statement.executeUpdate();
//        }
//    }
//
//    public void removeEmployee(Project project, Employee employee) throws SQLException {
//        try(Connection connection = getConnection())
//        {
//            PreparedStatement statement = connection.prepareStatement("DELETE FROM project_assignment WHERE user_id=? AND project_id=?");
//            statement.setInt(1, employee.getEmployee_id());
//            statement.setInt(2, project.getProject_id());
//            statement.executeUpdate();
//        }
//    }
//}
