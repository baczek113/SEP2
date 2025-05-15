package ClientModel;

import ClientModel.Requests.LoginRequest;
import ClientModel.Requests.ProjectRequest;
import ClientModel.Requests.Request;
import Network.Response.EmployeeResponse;
import Network.Response.LoginResponse;
import Network.Response.ProjectResponse;
import Network.Response.Response;
import Model.Project;

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
        sendRequest(request);
        while (true) {
          Response response = (Response) inFromServer.readObject();
          clientModelManager.handleServerResponse(response);
          if (response instanceof LoginResponse) {
            System.out.println("Role from LoginResponse: " + ((LoginResponse) response).getEmployee().getRole().getRole_name());
          } else if (response instanceof ProjectResponse){
            System.out.println("Received project response from server." + ((ProjectResponse) response).getProjects());
          } else {
            System.out.println("Received employee response" + ((EmployeeResponse) response).getEmployees());
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
}