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

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.EventHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import static javafx.scene.layout.AnchorPane.setLeftAnchor;
import static javafx.scene.layout.AnchorPane.setTopAnchor;

public class ChatClient {
	private TextArea incoming1;
    private TextField outgoing1;
//	private JTextArea incoming;
//	private JTextField outgoing;
	private BufferedReader reader;
	private PrintWriter writer;
	ArrayList<String> username;
	ArrayList<Integer> ids = new ArrayList<Integer>();

	public ChatClient(ArrayList<String> u, ArrayList<Integer> id){
		username =u;
		ids = id;
	}

	public void run() throws Exception {
		initView();
		setUpNetworking();
	}

	private void initView() {
		String message = ""; 
		String starter = username.remove(username.size()-1);
		for(String i: username){
			message += i;
			message += ", ";
		}
        Stage stage = new Stage();
        stage.setTitle(starter + " is chatting with " + message);
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 500, 350);
        incoming1 = new TextArea();
        incoming1.setEditable(true);
        ScrollPane qScroller = new ScrollPane(incoming1);
        outgoing1 = new TextField();
        Button sendButton = new Button("Send");
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sendButtonListener();
            }
        });
        anchorPane.getChildren().addAll(qScroller);
        anchorPane.getChildren().addAll(outgoing1);
        anchorPane.getChildren().add(incoming1);
        anchorPane.getChildren().add(sendButton);

        setLeftAnchor(incoming1, 5.0);
        setTopAnchor(incoming1, 5.0);
        setLeftAnchor(outgoing1, 10.0);
        setTopAnchor(outgoing1, 200.0);
        setLeftAnchor(sendButton, 400.0);
        setTopAnchor(sendButton, 200.0);

        stage.setScene(scene);
        stage.show();

	}

	private void setUpNetworking() throws Exception {
		@SuppressWarnings("resource")
		Socket sock = new Socket("localhost", 5000);
		InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
		reader = new BufferedReader(streamReader);
		writer = new PrintWriter(sock.getOutputStream());
		System.out.println("networking established");
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();
	}

    public void sendButtonListener() {
        String message = "";
			for(int i: ids){
				message += i;
				message += " ";
			}
			System.out.println("ids : " + ids.size());
			writer.println(message + "$*!~" + " " + outgoing1.getText());
			writer.flush();
			outgoing1.setText("");
			outgoing1.requestFocus();
    }

	public static void main(String[] args) {
		//		try {
		//			new ChatClient(username, id1, id2).run();
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
	}

	class IncomingReader implements Runnable {
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					boolean print = false;
					int count = 0;
					System.out.println(message);
					System.out.println(ids.size());
					String[] words = message.split(" ");
					if(words.length >= ids.size()){
						for(int i: ids){
							for(int j=0; j<ids.size(); j++){
								if(words[j].equals(String.valueOf(i))){
									count++;
								}
							}
						}
						if(count == ids.size()){
							if(words[count].equals("$*!~")) print = true;
						}
					}
					
					if(print){
						System.out.println("prints");
						incoming1.appendText(message.substring(2*ids.size()+4) + "\n");
					}

				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
