/**
 * Project 7 Chat Room
 * EE 422C submission by
 * Aditya Kharosekar amk3587
 * Rahul Jain rj8656
 * Fall 2016
 * Slip days used - 1 (on this project) (overall - 2)
 * This is the second slip day that we have used this semester
 */
package assignment7;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static javafx.scene.layout.AnchorPane.setLeftAnchor;
import static javafx.scene.layout.AnchorPane.setTopAnchor;

public class ChatRoom extends Application{
	String username;
	ArrayList<String> chatParticipants = new ArrayList<String>();
	String u;
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
		Button chatbutton = new Button("Begin Chat");
		Button adduser = new Button("Add To Chat");
		HBox buttons = new HBox();
		buttons.getChildren().addAll(chatbutton, adduser); 
		setLeftAnchor(buttons, 110.0);
		setTopAnchor(buttons, 152.0);
		buttons.setSpacing(5.0);
		loginScreen.getChildren().add(buttons);
		adduser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				u = chat.getText();
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
					chatParticipants.add(u); 
				}
			}
		});
		chatbutton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ArrayList<Integer> participantIds = new ArrayList<Integer>();
				chatParticipants.add(username);
				for(String u: chatParticipants){
					participantIds.add(ids.get(u));
				}
				ChatClient chat = new ChatClient(chatParticipants, participantIds);
				try {
					chat.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
				chatParticipants.clear();
			}
		});
		primaryStage.setScene(scene);


		primaryStage.show();

	}



}
