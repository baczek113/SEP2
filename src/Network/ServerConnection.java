package Network;

import ClientModel.Requests.Request;
import Network.Response.EmployeeResponse;
import Network.Response.LoginResponse;
import Network.Response.ProjectResponse;
import Network.Response.Response;

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
            LoginResponse loginResponse = (LoginResponse) modelManager.processLogin(loginRequest);
            int userType = loginResponse.getEmployee().getRole().getRole_id();
            outToClient.reset();
            outToClient.writeObject(loginResponse);

            if (userType == 1){
                EmployeeResponse initialResponse = new EmployeeResponse("employee", modelManager.getEmployees());
                sendResponse(initialResponse);
                while (true){
                    Request request = (Request) inFromClient.readObject();
                    modelManager.processRequest(request);
                }
            }else if (userType == 3 || userType == 4){
                ProjectResponse initialResponse = new ProjectResponse("project", modelManager.getProjects(loginResponse.getEmployee().getEmployee_id()));
                sendResponse(initialResponse);
                while (true){
                    Request request = (Request) inFromClient.readObject();
                    modelManager.processRequest(request);
                }
            }else if (userType == 2){
                ProjectResponse initialResponse = new ProjectResponse("project", modelManager.getProjects(loginResponse.getEmployee().getEmployee_id()));
                sendResponse(initialResponse);
                EmployeeResponse initialResponse2 = new EmployeeResponse("employee", modelManager.getEmployees());
                sendResponse(initialResponse2);
                while (true){
                    Request request = (Request) inFromClient.readObject();
                    modelManager.processRequest(request);
                }
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void sendResponse(Response response) throws IOException
    {
        outToClient.reset();
        outToClient.writeObject(response);
        outToClient.flush();
    }
}