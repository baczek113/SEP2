package Network;

import ClientModel.ServerInteractions.Request;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.IOException;

public  class ServerConnection implements Runnable{
    private Socket socket;
    private ServerModelManager modelManager;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;

    public ServerConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.modelManager = ServerModelManager.getInstance();
        this.outToClient = new ObjectOutputStream(socket.getOutputStream());
        this.outToClient.flush();
        this.inFromClient = new ObjectInputStream(socket.getInputStream());
    }

    @Override public void run()
    {
      try
      {
        Request loginRequest = (Request) inFromClient.readObject();
        LoginResponse loginResponse = (LoginResponse) modelManager.processRequest(loginRequest);
        int userType = loginResponse.getEmployee().getRole().getRole_id();

        if (userType == 1){
            EmployeeResponse initialResponse = new EmployeeResponse("employee", modelManager.getEmployees());
            while (true){
                Request request = (Request) inFromClient.readObject();
                EmployeeResponse response = (EmployeeResponse) modelManager.processRequest(request);
                sendEmployeeResponse(response);
            }

        }else if (userType == 2 || userType == 3 || userType == 4){
            ProjectResponse initialResponse = new ProjectResponse("project", modelManager.getProjects());
            while (true){
                Request request = (Request) inFromClient.readObject();
                ProjectResponse response = (ProjectResponse) modelManager.processRequest(request);
                sendProjectResponse(response);
            }
        }else {
            outToClient.reset();
            outToClient.writeObject(loginResponse.getEmployee());
        }
      }
      catch (IOException | ClassNotFoundException e)
      {
        throw new RuntimeException(e);
      }
    }

    private void sendEmployeeResponse(EmployeeResponse response) throws IOException
    {
        outToClient.reset();
        outToClient.writeObject(response);
        outToClient.flush();
    }
    private void sendProjectResponse(ProjectResponse response) throws IOException
    {
        outToClient.reset();
        outToClient.writeObject(response);
        outToClient.flush();
    }
}