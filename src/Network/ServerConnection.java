package Network;

import ClientModel.ServerInteractions.Request;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.IOException;

public  class ServerConnection implements Runnable{
    private Socket socket;
    private ServerModelManager model;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;

    public ServerConnection(Socket socket, ServerModelManager model) throws IOException {
        this.socket = socket;
        this.model = model;
        this.outToClient = new ObjectOutputStream(socket.getOutputStream());
        this.outToClient.flush();
        this.inFromClient = new ObjectInputStream(socket.getInputStream());
    }

    @Override public void run()
    {
      try
      {
        Request loginRequest = (Request) inFromClient.readObject();
        LoginResponse loginResponse = model.processRequest(loginRequest);
        int userType = loginResponse.getEmployee().getRole().getRole_id();

        if (userType == 1){
            EmployeeResponse initialResponse = new EmployeeResponse("employee", model.getEmployyes());
            while (true){
                Request request = (Request) inFromClient.readObject();
                EmployeeResponse response = model.processRequest(request);
                sendEmployeeResponse(response);
            }

        }else if (userType == 2 || userType == 3 || userType == 4){
            ProjectResponse initialResponse = new ProjectResponse("project", model.getProjects());
            while (true){
                Request request = (Request) inFromClient.readObject();
                ProjectResponse response = model.processRequest(request);
                sendProjectResponse(response);
            }
        }else {
            String errorMessage = "Login error";
            outToClient.reset();
            outToClient.writeObject(errorMessage);
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