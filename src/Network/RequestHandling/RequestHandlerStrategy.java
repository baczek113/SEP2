package Network.RequestHandling;

import ClientModel.ServerInteractions.Request;
import Network.Response.Response;
import Network.ServerModelManager;

public interface RequestHandlerStrategy {
    public Response processRequest(Request request, ServerModelManager modelManager);
}
