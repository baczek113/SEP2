package Network;

import ClientModel.Requests.Request;
import Model.Employee;
import Network.Response.EmployeeResponse;
import Network.Response.LoginResponse;
import Network.Response.ProjectResponse;
import Network.Response.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.IOException;
import java.sql.SQLException;

public  class ServerConnection implements Runnable{
    private Socket socket;
    private Employee employee;
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
            int userType = -1;
            if(!loginResponse.getMessage().equals("loginFailure")) {
                this.employee = loginResponse.getEmployee();
                userType = loginResponse.getEmployee().getRole().getRole_id();
            }
            else
            {
                modelManager.getConnectionPool().removeConnection(this);
            }
            outToClient.reset();
            outToClient.writeObject(loginResponse);

            if (userType == 1){
                EmployeeResponse initialResponse = new EmployeeResponse(modelManager.getEmployees());
                sendResponse(initialResponse);
                while (true){
                    Request request = (Request) inFromClient.readObject();
                    modelManager.processRequest(request);
                }
            }else if (userType == 3 || userType == 4){
                ProjectResponse initialResponse = new ProjectResponse(modelManager.getProjectsForEmployee(loginResponse.getEmployee().getEmployee_id()));
                sendResponse(initialResponse);
                while (true){
                    Request request = (Request) inFromClient.readObject();
                    modelManager.processRequest(request);
                }
            }else if (userType == 2){
                ProjectResponse initialResponse = new ProjectResponse(modelManager.getProjectsForEmployee(loginResponse.getEmployee().getEmployee_id()));
                sendResponse(initialResponse);
                EmployeeResponse initialResponse2 = new EmployeeResponse(modelManager.getEmployees());
                sendResponse(initialResponse2);
                while (true){
                    Request request = (Request) inFromClient.readObject();
                    modelManager.processRequest(request);
                }
            }
        }
        catch (IOException | ClassNotFoundException | SQLException e)
        {
            modelManager.getConnectionPool().removeConnection(this);
        }
    }

    public Employee getEmployee() {
        return employee;
    }

    public void sendResponse(Response response) throws IOException
    {
        outToClient.reset();
        outToClient.writeObject(response);
        outToClient.flush();
    }
}