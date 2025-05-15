package ClientModel;

import java.sql.SQLException;

public class Test
{
  public static void main(String[] args) throws SQLException {

    ClientModelManager manager = new ClientModelManager("localhost", 2137);
    manager.login("chuj pierdolony kurwa", "2137");
    manager.login("zabij sie", "2137");
    manager.login("co za gowno", "2137");
    manager.login("sep period = czysciec", "2137");
    manager.login("damianczina", "2137");
  }

}