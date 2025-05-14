package Network.RequestHandling;

import ClientModel.ServerInteractions.Request;
import Network.Response.Response;
import Network.ServerModelManager;

public interface RequestHandlerStrategy {
    public void processRequest(Request request, ServerModelManager modelManager);
}
