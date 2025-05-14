package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class TestServer
{
  public static void main(String[] args) throws IOException
  {
    ServerSocket serverSocket = new ServerSocket(2137);
    System.out.println("Server started on port 2137");
    ServerModelManager manager = ServerModelManager.getInstance();
    while (true) {

      Socket clientSocket = serverSocket.accept();

      System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

      ServerConnection newConnection = new ServerConnection(clientSocket);

      new Thread(newConnection).start();

    }

  }

}