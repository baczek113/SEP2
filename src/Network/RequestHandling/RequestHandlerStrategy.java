package Network.RequestHandling;

import ClientModel.Requests.Request;
import Network.ServerModelManager;

import java.sql.SQLException;

public interface RequestHandlerStrategy {
    public void processRequest(Request request, ServerModelManager modelManager) throws SQLException;
}
