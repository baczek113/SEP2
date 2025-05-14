package ClientModel;
import ClientModel.ServerInteractions.LoginRequest;
import ClientModel.ServerInteractions.Request;
public class Test
{
  public static void main(String[] args)
  {

    ClientModelManager manager = new ClientModelManager();

    ClientConnection client = new ClientConnection("localhost", 2137, manager);

    manager.setConnection(client);

    new Thread(client).start();
  }

}