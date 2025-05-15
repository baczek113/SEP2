package ClientModel;

import java.sql.SQLException;

public class Test
{
  public static void main(String[] args) throws SQLException {

    ClientModelManager manager = new ClientModelManager("localhost", 2137);
    manager.login("damianczina", "2137");
  }

}