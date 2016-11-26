package assignment7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static javafx.scene.layout.AnchorPane.setLeftAnchor;
import static javafx.scene.layout.AnchorPane.setTopAnchor;


public class ClientMain extends Application{
    public BufferedReader reader;
    public PrintWriter writer;
    Label text;

    public static void main(String[] args) {
        try {
            ClientMain.launch(ClientMain.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        login(primaryStage);
        /*Stage newStage = new Stage();
        AnchorPane chat = new AnchorPane();
        newStage.setTitle("Chat");
        text = new Label("Hey there");
        chat.getChildren().add(text);
        newStage.setScene(new Scene(chat, 550, 380));
        newStage.show();
        setUpNetworking();*/
    }

    public void login(Stage primaryStage) {
        AnchorPane loginScreen = new AnchorPane();
        primaryStage.setTitle("Login");
        Label heading = new Label("LOGIN");
        loginScreen.getChildren().add(heading);
        setLeftAnchor(heading, 130.0);
        setTopAnchor(heading, 10.0);

        Label username = new Label("Username: ");
        TextField getUsername = new TextField();
        HBox usernameInfo = new HBox();
        usernameInfo.getChildren().addAll(username, getUsername);
        usernameInfo.setSpacing(5.0);
        setLeftAnchor(usernameInfo, 37.0);
        setTopAnchor(usernameInfo, 50.0);
        loginScreen.getChildren().add(usernameInfo);

        Label password = new Label("Password: ");
        TextField getPassword = new TextField();
        HBox passwordInfo = new HBox();
        passwordInfo.getChildren().addAll(password, getPassword);
        passwordInfo.setSpacing(5.0);
        setLeftAnchor(passwordInfo, 41.0);
        setTopAnchor(passwordInfo, 100.0);
        loginScreen.getChildren().add(passwordInfo);

        primaryStage.setScene(new Scene(loginScreen, 305, 185));
        primaryStage.show();
    }

    public void setUpNetworking() throws Exception{
        Socket sock = new Socket("localhost", 5000); //to talk to my own computer
        InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
        reader = new BufferedReader(streamReader);
        writer = new PrintWriter(sock.getOutputStream());
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
        text.setText("Established connection");
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
