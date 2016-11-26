package assignment7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;


public class ServerMain extends Observable{
    public static void main(String[] args) {
        try {
            new ServerMain().setUpNetworking();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpNetworking() throws IOException {
        ServerSocket serverSock = new ServerSocket(5000);
        while (true) {
            Socket clientSocket = serverSock.accept();
            ClientObserver writer = new ClientObserver(clientSocket.getOutputStream());
            Thread t = new Thread(new ClientHandler(clientSocket));
            t.start();
            this.addObserver(writer);
            System.out.println("Got a connection");
        }
    }

    class ClientHandler implements Runnable {
        private BufferedReader reader;

        ClientHandler(Socket clientSocket) {
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) !=null) {
                    System.out.println("Server read: " + message);
                    setChanged();
                    notifyObservers(message);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
