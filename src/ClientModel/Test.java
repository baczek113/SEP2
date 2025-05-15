package ClientModel;

import java.sql.SQLException;

public class Test
{
  public static void main(String[] args) throws SQLException {

    ClientModelManager manager = new ClientModelManager();

    ClientConnection client = new ClientConnection("localhost", 2137, manager);

    manager.setConnection(client);

    new Thread(client).start();
  }

}