package Network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.IOException;

public abstract class ServerConnection {
    private Socket socket;
    private ServerModelManager model;

    public ServerConnection(Socket socket, ServerModelManager model) throws IOException {
        this.socket = socket;
        this.model = model;
    }

    public void sendResponse(Response response) throws IOException {

    }
}