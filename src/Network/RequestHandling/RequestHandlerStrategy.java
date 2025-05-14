package Network.RequestHandling;

import ClientModel.Requests.Request;
import Network.Response.Response;
import Network.ServerModelManager;

public interface RequestHandlerStrategy {
    public void processRequest(Request request, ServerModelManager modelManager);
}
