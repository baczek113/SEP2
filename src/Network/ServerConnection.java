//package Network;
//
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.Socket;
//import java.io.IOException;
//
//public class ServerConnection implements Runnable {
//    private Socket socket;
//    private ObjectInputStream inFromClient;
//    private ObjectOutputStream outToClient;
//    private ServerModelManager model;
//    private String clientIpAddress;
//
//    public ServerConnection(Socket socket, ServerModelManager model) throws IOException {
//        this.socket = socket;
//        this.model = model;
//        this.clientIpAddress = socket.getInetAddress().getHostAddress();
//
//        this.outToClient = new ObjectOutputStream(socket.getOutputStream());
//        this.outToClient.flush();
//        this.inFromClient = new ObjectInputStream(socket.getInputStream());
//    }
//
//    @Override
//    public void run() {
//        try {
//            Response initialResponse = new Response();
//            initialResponse.setVinyls(model.getVinyls());
//            initialResponse.setMessage("Initial list loaded.");
//            sendResponse(initialResponse);
//
//            while (true) {
//                Request request = (Request) inFromClient.readObject();
//
//                Response response = model.processRequest(request);
//                sendResponse(response);
//            }
//        } catch (java.io.EOFException e) {
//        } catch (java.net.SocketException e) {
//        } catch (IOException | ClassNotFoundException | ClassCastException e) {
//        } finally {
//            try {
//                if (socket != null && !socket.isClosed()) {
//                    socket.close();
//                }
//            } catch (IOException ex) {
//            }
//        }
//    }
//
//    public void sendResponse(Response response) throws IOException {
//        String responseLog = String.format("Sending response: Message='%s', VinylCount=%d",
//                response.getMessage(),
//                response.getVinyls().size());
//
//        outToClient.reset();
//        outToClient.writeObject(response);
//        outToClient.flush();
//    }
//}