package assignment7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMain extends Application{
    public BufferedReader reader;
    public PrintWriter writer;
    Label text;

    public static void main(String[] args) {
        try {
            ClientMain.launch(ClientMain.class);
            new ClientMain().setUpNetworking();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpNetworking() throws Exception{
        Socket sock = new Socket("localhost", 5000); //this is my local IP address
        InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
        reader = new BufferedReader(streamReader);
        writer = new PrintWriter(sock.getOutputStream());
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
        text.setText("Established connection");

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane chat = new AnchorPane();
        primaryStage.setTitle("Chat");
        text = new Label("Hey there");
        chat.getChildren().add(text);
        primaryStage.setScene(new Scene(chat, 550, 380));
        primaryStage.show();
        setUpNetworking();
    }



    class IncomingReader implements Runnable {
        String message;
        @Override
        public void run() {
            try {
                while ((message=reader.readLine())!=null) {
                    text.setText(message);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
