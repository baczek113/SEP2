package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class   Server
{
  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(2137);
    System.out.println("Server started on port 2137");
    ServerModelManager manager = ServerModelManager.getInstance();
    ConnectionPool connectionPool = new ConnectionPool(manager);
    manager.setConnectionPool(connectionPool);
    while (true) {

      Socket clientSocket = serverSocket.accept();

      System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

      ServerConnection newConnection = new ServerConnection(clientSocket);

      new Thread(newConnection).start();

      connectionPool.addConnection(newConnection);
    }

  }

}