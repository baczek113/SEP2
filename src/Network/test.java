package Network;

import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException {
        UserDAO.getInstance().addUser(1, "baczek113", "gigakoks1");
    }
}
