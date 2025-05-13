package Network;

import ClientModel.ServerInteractions.Request;
import Model.Employee;

public class RequestHandler {
    private static RequestHandler instance;
    private static ServerModelManager modelManager;

    private RequestHandler() {
        modelManager = ServerModelManager.getInstance();
    }

    public static RequestHandler getInstance() {
        if (instance == null) {
            instance = new RequestHandler();
        }
        return instance;
    }

    public Response processRequest(Request request) {
        return new LoginResponse("blablabla sigma sigma :D", modelManager.getEmployees().get(1));
    }
}
