//package Network.Database;
//
//import Model.Employee;
//import Model.Sprint;
//import Model.Task;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class TaskDAO extends DAO{
//    private static TaskDAO instance;
//    private TaskDAO() throws SQLException {
//        super();
//    }
//
//    public static TaskDAO getInstance() throws SQLException {
//        if (instance == null) {
//            instance = new TaskDAO();
//        }
//        return instance;
//    }
//
//    public ArrayList<Task> getTasks() throws SQLException {
//        try(Connection connection = getConnection()) {
//            ArrayList<Task> tasks = new ArrayList<>();
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tasks");
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                tasks.add(new Task(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3),
//                        resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6),
//                        resultSet.getString(7), resultSet.getInt(8)));
//            }
//            return tasks;
//        }
//    }
//
//    public ArrayList<Task> getTasks(Sprint sprint) throws SQLException {
//        try(Connection connection = getConnection()) {
//            ArrayList<Task> tasks = new ArrayList<>();
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tasks WHERE sprint_id=?");
//            statement.setInt(1, sprint.getSprint_id());
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                tasks.add(new Task(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3),
//                        resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6),
//                        resultSet.getString(7), resultSet.getInt(8)));
//            }
//            return tasks;
//        }
//    }
//
//    public Task addTask(Sprint sprint, Employee createdBy, String title, String description, int priority) throws SQLException {
//        try(Connection connection = getConnection()) {
//            PreparedStatement statement = connection.prepareStatement("INSERT INTO task(sprint_id, created_by, project_id, title, description, status, priority) VALUES (?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
//            statement.setInt(1, sprint.getSprint_id());
//            statement.setInt(2, createdBy.getEmployee_id());
//            statement.setInt(3, sprint.getProject_id());
//            statement.setString(4, title);
//            statement.setString(5, description);
//            statement.setString(6, "pending");
//            statement.setInt(7, priority);
//            statement.executeUpdate();
//            ResultSet keys = statement.getGeneratedKeys();
//
//            if(keys.next())
//            {
//                return new Task(keys.getInt(1), sprint.getSprint_id(), createdBy.getEmployee_id(), sprint.getProject_id(), title, description, "to-do", priority);
//            }
//            else
//            {
//                return null;
//            }
//        }
//    }
//
//    public void editTask(Task task) throws SQLException {
//        try(Connection connection = getConnection()) {
//            PreparedStatement statement = connection.prepareStatement("UPDATE task SET sprint_id = ?, created_by = ?, project_id = ?, title = ?, description = ?, status = ?, priority = ? WHERE task_id = ?");
//            statement.setInt(1, task.getSprint_id());
//            statement.setInt(2, task.getCreated_by());
//            statement.setInt(3, task.getProject_id());
//            statement.setString(4, task.getTitle());
//            statement.setString(5, task.getDescription());
//            statement.setString(6, task.getStatus());
//            statement.setInt(7, task.getPriority());
//            statement.setInt(8, task.getTask_id());
//            statement.executeUpdate();
//        }
//    }
//
//    public void deleteTask(Task task) throws SQLException {
//        try(Connection connection = getConnection()) {
//            PreparedStatement statement = connection.prepareStatement("DELETE FROM task WHERE task_id = ?");
//            statement.setInt(1, task.getTask_id());
//            statement.executeUpdate();
//        }
//    }
//}
