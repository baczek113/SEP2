package ClientModel;
import ClientModel.Requests.Request;
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
  private Request loginRequest;
  private boolean running;

  public ClientConnection(String host, int port, ClientModelManager clientModelManager, Request loginRequest) {
    try {
      this.socket = new Socket(host, port);

      this.outToServer = new ObjectOutputStream(socket.getOutputStream());
      this.outToServer.flush();
      this.inFromServer = new ObjectInputStream(socket.getInputStream());
      this.clientModelManager = clientModelManager;
      this.loginRequest = loginRequest;
      this.running = true;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override public void run()
  {
    sendRequest(loginRequest);
      try {
        while (running) {
          Response response = (Response) inFromServer.readObject();

          System.out.println("Login ClientConnection");
          clientModelManager.handleServerResponse(response);
          if(response.getMessage().equals("loginFailure"))
          {
            running = false;
          }
        }
      } catch (IOException | ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
  }

  public void sendRequest(Request request){
    try {
      outToServer.writeObject(request);
      outToServer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setRunning(boolean running) {
    this.running = running;
  }
}