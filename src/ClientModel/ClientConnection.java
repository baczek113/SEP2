package ClientModel;

import ClientModel.ServerInteractions.LoginRequest;
import ClientModel.ServerInteractions.Request;
import Network.Response.LoginResponse;
import Network.Response.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable{
  private ClientModelManager clientModelManager;
  private Socket socket;
  private ObjectOutputStream outToServer;
  private ObjectInputStream inFromServer;

  public ClientConnection(String host, int port, ClientModelManager clientModelManager) {
    try {
      this.socket = new Socket(host, port);

      this.outToServer = new ObjectOutputStream(socket.getOutputStream());
      this.outToServer.flush();
      this.inFromServer = new ObjectInputStream(socket.getInputStream());
      this.clientModelManager = clientModelManager;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override public void run()
  {
      try {
        Request request = new LoginRequest("login", null, "damianczina", "2137");
        outToServer.writeObject(request);
        while (true) {
          Response response = (Response) inFromServer.readObject();

          clientModelManager.handleServerResponse(response);
          System.out.println(((LoginResponse) response).getEmployee().getRole().getRole_name());

          System.out.println("Client handled server response.");
        }
      } catch (IOException | ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
  }

  public void sendRequest(Request request){
    try {
      outToServer.writeObject(request);
      outToServer.flush();
      System.out.println("works");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}