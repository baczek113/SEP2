package Network;

import ClientModel.ServerInteractions.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AdminConnection extends ServerConnection implements Runnable
{
  private ObjectInputStream inFromClient;
  private ObjectOutputStream outToClient;
  private ServerModelManager model;
  public AdminConnection(Socket socket, ServerModelManager model) throws IOException
  {
    super(socket, model);
    this.outToClient = new ObjectOutputStream(socket.getOutputStream());
    this.outToClient.flush();
    this.inFromClient = new ObjectInputStream(socket.getInputStream());
  }

  @Override public void run()
  {
    EmployeeResponse initialResponse = new EmployeeResponse("Initial", model.getEmployyes());

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

  public void sendResponse(EmployeeResponse response) throws IOException
  {
    outToClient.reset();
    outToClient.writeObject(response);
    outToClient.flush();
  }
}
