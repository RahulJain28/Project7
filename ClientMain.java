package assignment7;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;

import static javafx.scene.layout.AnchorPane.setLeftAnchor;
import static javafx.scene.layout.AnchorPane.setTopAnchor;


public class ClientMain extends Application{
    public BufferedReader reader;
    public PrintWriter writer;
    Label text;

    public static void main(String[] args) {
        try {
            Map.addOrChange("default", "default"); //for testing purposes
            Map.addOrChange("a", "default"); //for testing purposes
            Map.addOrChange("b", "default"); //for testing purposes
            Map.addOrChange("c", "default"); //for testing purposes
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
        Scene scene = new Scene(loginScreen, 335, 220);
        primaryStage.setTitle("Login");
        Label heading = new Label("LOGIN");
        loginScreen.getChildren().add(heading);
        setLeftAnchor(heading, 130.0);
        setTopAnchor(heading, 10.0);

        Button quit = new Button("Quit");
        loginScreen.getChildren().add(quit);
        setLeftAnchor(quit, 280.0);
        setTopAnchor(quit, 10.0);
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        Label username = new Label("Username: ");
        TextField getUsername = new TextField();
        HBox usernameInfo = new HBox();
        usernameInfo.getChildren().addAll(username, getUsername);
        usernameInfo.setSpacing(5.0);
        setLeftAnchor(usernameInfo, 37.0);
        setTopAnchor(usernameInfo, 50.0);
        loginScreen.getChildren().add(usernameInfo);

        Label password = new Label("Password: ");
        PasswordField getPassword = new PasswordField();
        getPassword.setPromptText("Enter your password");
        HBox passwordInfo = new HBox();
        passwordInfo.getChildren().addAll(password, getPassword);
        passwordInfo.setSpacing(5.0);
        setLeftAnchor(passwordInfo, 41.0);
        setTopAnchor(passwordInfo, 100.0);
        loginScreen.getChildren().add(passwordInfo);

        Text forgot = new Text("I forgot my password");
        forgot.setFont(Font.font(11));
        setLeftAnchor(forgot, 144.0);
        setTopAnchor(forgot, 127.0);
        loginScreen.getChildren().add(forgot);

        forgot.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                forgot.setUnderline(true);
                scene.setCursor(Cursor.HAND);
            }
        });

        forgot.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                forgot.setUnderline(false);
                scene.setCursor(Cursor.DEFAULT);
            }
        });

        /**
         * This handles the entire changing password aspect of the program
         */
        forgot.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.close();
                Stage stage = new Stage();
                stage.setTitle("Forgot password");
                AnchorPane forgotPassword = new AnchorPane();
                Scene scene = new Scene(forgotPassword, 350, 250);
                Label title = new Label("Password Change");
                forgotPassword.getChildren().add(title);
                setLeftAnchor(title, 110.0);
                setTopAnchor(title, 10.0);

                Label username = new Label("Enter Username: ");
                TextField getUsername = new TextField();
                HBox usernameInfo = new HBox();
                usernameInfo.getChildren().addAll(username, getUsername);
                usernameInfo.setSpacing(5.0);
                setLeftAnchor(usernameInfo, 37.0);
                setTopAnchor(usernameInfo, 50.0);
                forgotPassword.getChildren().add(usernameInfo);

                Label password = new Label("Enter new password: ");
                PasswordField getPassword = new PasswordField();
                getPassword.setPromptText("Enter your password");
                HBox passwordInfo = new HBox();
                passwordInfo.getChildren().addAll(password, getPassword);
                passwordInfo.setSpacing(5.0);
                setLeftAnchor(passwordInfo, 15.0);
                setTopAnchor(passwordInfo, 100.0);
                forgotPassword.getChildren().add(passwordInfo);

                Button confirm = new Button("Confirm");
                forgotPassword.getChildren().add(confirm);
                setLeftAnchor(confirm, 130.0);
                setTopAnchor(confirm, 150.0);
                Label confirmed = new Label("Password change confirmed");
                confirmed.setVisible(false);
                confirmed.setTextFill(Color.GREEN);
                setLeftAnchor(confirmed, 192.0);
                setTopAnchor(confirmed, 154.0);
                forgotPassword.getChildren().add(confirmed);
                confirm.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String name = getUsername.getText();
                        String newPass = getPassword.getText();
                        Map.addOrChange(name, newPass);
                        confirmed.setVisible(true);
                    }
                });

                Button back = new Button("Back to Login page");
                forgotPassword.getChildren().add(back);
                setLeftAnchor(back, 100.0);
                setTopAnchor(back, 190.0);
                back.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        stage.close();
                        primaryStage.show();

                    }
                });

                stage.setScene(scene);
                stage.show();
            }
        });

        Button login = new Button("Log In");
        Button signUp = new Button("Sign Up");
        HBox buttons = new HBox();
        buttons.getChildren().addAll(login, signUp);
        buttons.setSpacing(20.0);
        setLeftAnchor(buttons, 110.0);
        setTopAnchor(buttons, 152.0);
        loginScreen.getChildren().add(buttons);
        signUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
                Stage stage = new Stage();
                stage.setTitle("New User Register");
                AnchorPane signUp = new AnchorPane();
                Scene scene = new Scene(signUp, 350, 250);
                Label title = new Label("Register");
                signUp.getChildren().add(title);
                setLeftAnchor(title, 110.0);
                setTopAnchor(title, 10.0);

                Label username = new Label("Enter Username: ");
                TextField getUsername = new TextField();
                HBox usernameInfo = new HBox();
                usernameInfo.getChildren().addAll(username, getUsername);
                usernameInfo.setSpacing(5.0);
                setLeftAnchor(usernameInfo, 37.0);
                setTopAnchor(usernameInfo, 50.0);
                signUp.getChildren().add(usernameInfo);

                Label password = new Label("Enter password: ");
                PasswordField getPassword = new PasswordField();
                getPassword.setPromptText("Enter your password");
                HBox passwordInfo = new HBox();
                passwordInfo.getChildren().addAll(password, getPassword);
                passwordInfo.setSpacing(5.0);
                setLeftAnchor(passwordInfo, 35.0);
                setTopAnchor(passwordInfo, 100.0);
                signUp.getChildren().add(passwordInfo);

                Button finish = new Button("Sign Up");
                signUp.getChildren().add(finish);
                setLeftAnchor(finish, 130.0);
                setTopAnchor(finish, 150.0);
                Label confirmed = new Label("Registration confirmed");
                confirmed.setVisible(false);
                confirmed.setTextFill(Color.GREEN);
                setLeftAnchor(confirmed, 192.0);
                setTopAnchor(confirmed, 154.0);
                signUp.getChildren().add(confirmed);
                finish.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String name = getUsername.getText();
                        String newPass = getPassword.getText();
                        Map.addOrChange(name, newPass);
                        confirmed.setVisible(true);

//                        String name = getUsername.getText();
//                        String newPass = getPassword.getText();
//                        Map.change(name, newPass);
//                        confirmed.setVisible(true);
                    }
                });

                Button back = new Button("Back to Login page");
                signUp.getChildren().add(back);
                setLeftAnchor(back, 100.0);
                setTopAnchor(back, 190.0);
                back.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        stage.close();
                        primaryStage.show();

                    }
                });

                stage.setScene(scene);
                stage.show();
            }
        });

        Label warning = new Label("Please try again");
        setTopAnchor(warning, 175.0);
        setLeftAnchor(warning, 115.0);
        warning.setTextFill(Color.RED);
        warning.setVisible(false);
        loginScreen.getChildren().add(warning);

        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String u = getUsername.getText();
                String p = getPassword.getText();
                boolean valid = processLogin(u, p);
                if (valid) {
                    warning.setText("Logged In");
                    ChatRoom chatroom = new ChatRoom(u);
                    try {
						chatroom.start(new Stage());
					} catch (Exception e) {
						e.printStackTrace();
					}
                    warning.setVisible(true);
                }
                else {
                	warning.setText("Username/Password incorrect");
                    warning.setVisible(true);
                }
            }
        });

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public boolean processLogin(String username, String password) {
        Set<String> keys = Map.UsernamePasswordMap.keySet();
        Iterator<String> iterator = keys.iterator();

        while (iterator.hasNext()) {
            String current = iterator.next();
            if (current.equals(username)) {
                String p = Map.UsernamePasswordMap.get(current);
                if (p.equals(password)) {
                    return true;
                }
            }
        }
        return false;

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
