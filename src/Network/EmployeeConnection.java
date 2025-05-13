package Network;

import ClientModel.ServerInteractions.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class EmployeeConnection extends ServerConnection implements Runnable
{
  private ObjectInputStream inFromClient;
  private ObjectOutputStream outToClient;
  private ServerModelManager model;

  public EmployeeConnection(Socket socket, ServerModelManager model) throws IOException
  {
    super(socket, model);
    this.outToClient = new ObjectOutputStream(socket.getOutputStream());
    this.outToClient.flush();
    this.inFromClient = new ObjectInputStream(socket.getInputStream());
  }

  @Override public void run()
  {
    ProjectResponse initialResponse = new ProjectResponse("Initial", model.getProjects());

    while (true){
      try
      {
        Request request = (Request) inFromClient.readObject();
        EmployeeResponse response = model.processRequest(request);
        sendResponse(response);
      }
      catch (IOException | ClassNotFoundException e)
      {
        throw new RuntimeException(e);
      }
    }

  }

  public void sendResponse(ProjectResponse response) throws IOException
  {
    outToClient.reset();
    outToClient.writeObject(response);
    outToClient.flush();
  }
}
