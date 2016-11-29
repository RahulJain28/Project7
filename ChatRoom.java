package assignment7;
import static javafx.scene.layout.AnchorPane.setLeftAnchor;
import static javafx.scene.layout.AnchorPane.setTopAnchor;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import static javafx.scene.layout.AnchorPane.setLeftAnchor;
import static javafx.scene.layout.AnchorPane.setTopAnchor;
import java.util.Set;

import javafx.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ChatRoom extends Application{
	String username;
	
	public ChatRoom(String u){
		username = u;
	}
	public static void main(String[] args) {
		try {
			
	        ChatRoom.launch(ChatRoom.class);
	    }catch (Exception e) {
	    	e.printStackTrace(); }
	}
	@Override
	public void start(Stage arg0) throws Exception {
		makeRoom(arg0);
	}
	
	public void makeRoom(Stage primaryStage){
		AnchorPane loginScreen = new AnchorPane();
		Scene scene = new Scene(loginScreen, 335, 220);
		primaryStage.setTitle("Chat Room");
		Label heading = new Label("List of Friends: ");
		Label x; 
		@SuppressWarnings("unchecked")
		Set<String> keys = Map.getUsernames();
		HashMap<String, Integer> ids = Map.getId(); 
		double y = 20.0;
				
		for(Object key: keys){
			x = new Label((String)key);
			loginScreen.getChildren().add(x);
			setLeftAnchor(x, 10.0);
			setTopAnchor(x, y);
			y += 20; 
		}
		loginScreen.getChildren().add(heading);
		setLeftAnchor(heading, 0.0);
		setTopAnchor(heading, 0.0);
		
		x = new Label("Chat with: ");
        TextField chat = new TextField();
        chat.setPromptText("Enter Username");
        HBox chatInfo = new HBox();
        chatInfo.getChildren().addAll(x, chat);
        chatInfo.setSpacing(4.0);
        setLeftAnchor(chatInfo, 45.0);
        setTopAnchor(chatInfo, 100.0);
        loginScreen.getChildren().add(chatInfo);
        Button chatbutton = new Button("Chat");
        setLeftAnchor(chatbutton, 110.0);
        setTopAnchor(chatbutton, 152.0);
        loginScreen.getChildren().add(chatbutton);
        chatbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String u = chat.getText();
                Label warning = new Label("");
                setTopAnchor(warning, 175.0);
                setLeftAnchor(warning, 115.0);
                warning.setTextFill(Color.RED);
                warning.setVisible(true);
                loginScreen.getChildren().add(warning);
                if(!keys.contains(u)){
                	warning.setText("Invalid Username");
                	return; 
                }
                else{
                	int id1 = ids.get(u);
                	int id2 = ids.get(username);
                    ChatClient chat = new ChatClient(u,id1,id2);
                    try {
						chat.run();
					} catch (Exception e) {
						e.printStackTrace();
					}
                    
                }
            }
        });
		primaryStage.setScene(scene);
		

		primaryStage.show();

	}
	
	
	
}
