//package Network.Database;
//
//import Model.Employee;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class EmployeeDAO extends DAO{
//    private static EmployeeDAO instance;
//
//    private EmployeeDAO() throws SQLException {
//        super();
//    }
//
//    public static EmployeeDAO getInstance() throws SQLException {
//        if (instance == null) {
//            instance = new EmployeeDAO();
//        }
//        return instance;
//    }
//
//    public Employee addEmployee(int role_id, String username, String password) throws SQLException {
//        try(Connection connection = getConnection()) {
//            PreparedStatement statement = connection.prepareStatement("INSERT INTO employee(role_id, username, password) VALUES (?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
//            statement.setInt(1, role_id);
//            statement.setString(2, username);
//            statement.setString(3, password);
//            statement.executeUpdate();
//            ResultSet keys = statement.getGeneratedKeys();
//
//            if(keys.next()) {
//                return new Employee(keys.getInt(1), role_id, username, password);
//            }
//            else {
//                return null;
//            }
//        }
//    }
//
//    public ArrayList<Employee> getAllEmployees() throws SQLException {
//        try(Connection connection = getConnection()) {
//            ArrayList<Employee> employees = new ArrayList<>();
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee");
//
//            ResultSet resultSet = statement.executeQuery();
//
//            while(resultSet.next()) {
//                employees.add(new Employee(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4)));
//            }
//            return employees;
//        }
//    }
//
//    public void editEmployee(Employee employee) throws SQLException {
//        try(Connection connection = getConnection()) {
//            PreparedStatement statement = connection.prepareStatement("UPDATE employee SET role_id = ?, username = ?, password = ? WHERE employee_id = ?");
//            statement.setInt(1, employee.getRole().getRole_id());
//            statement.setString(2, employee.getUsername());
//            statement.setString(3, employee.getPassword());
//            statement.setInt(4, employee.getEmployee_id());
//            statement.executeUpdate();
//        }
//    }
//
//    public void removeEmployee(Employee employee) throws SQLException {
//        try(Connection connection = getConnection()) {
//            PreparedStatement statement = connection.prepareStatement("DELETE FROM employee WHERE employee_id = ?");
//            statement.setInt(1, employee.getEmployee_id());
//            statement.executeUpdate();
//        }
//    }
//
//    public Employee getEmployee(String username, String password) throws SQLException {
//        try(Connection connection = getConnection()) {
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee WHERE username = ? AND password = ?");
//            statement.setString(1, username);
//            statement.setString(2, password);
//            ResultSet resultSet = statement.executeQuery();
//            if(resultSet.next()) {
//                return new Employee(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4));
//            }
//            else {
//                return null;
//            }
//        }
//    }
//
//
//}
