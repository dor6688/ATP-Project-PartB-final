package Client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements IClientStrategy{

    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy clientStrategy;

    public Client(InetAddress serverIP, int serverPort, IClientStrategy clientStrategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.clientStrategy = clientStrategy;
    }

    @Override
    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {

    }

    public void communicateWithServer() {
        try (Socket serverSocket = new Socket(serverIP, serverPort)) {
            System.out.println(String.format("Client is connected to server (IP: %s, Port: %s)",serverIP,serverPort));
            clientStrategy.clientStrategy(serverSocket.getInputStream(),serverSocket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
