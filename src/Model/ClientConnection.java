package Model;

import Network.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable {
  private ClientModelManager clientModelManager;
  private Socket socket;
  private ObjectOutputStream outToServer;
  private ObjectInputStream inFromServer;

  public ClientConnection(String host, int port, ClientModelManager clientModelManager) {
    try {
      this.socket = new Socket(host, port);
      this.outToServer = new ObjectOutputStream(socket.getOutputStream());
      this.outToServer.flush();
      this.inFromServer = new ObjectInputStream(socket.getInputStream());
      this.clientModelManager = clientModelManager;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    try {
      while (true) {
        Response response = (Response) inFromServer.readObject();
        clientModelManager.handleServerResponse(response);
        System.out.println("clientconnection");
        System.out.println("Client handled server response.");
      }
    } catch (IOException | ClassNotFoundException e) {
      try {
        if (inFromServer != null) inFromServer.close();
        if (outToServer != null) outToServer.close();
        if (socket != null) socket.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      throw new RuntimeException(e);
    }
  }

  public void sendRequest(Request request) {
    try {
      outToServer.writeObject(request);
      outToServer.flush();
      System.out.println("works");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}