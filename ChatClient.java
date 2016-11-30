package assignment7;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatClient {
	private JTextArea incoming;
	private JTextField outgoing;
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
			message += " ";
		}
		JFrame frame = new JFrame(starter+ " is chatting with " + message);
		JPanel mainPanel = new JPanel();
		incoming = new JTextArea(15, 50);
		incoming.setLineWrap(true); 
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane qScroller = new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		outgoing = new JTextField(20);
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());
		mainPanel.add(qScroller);
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(650, 500);
		frame.setVisible(true);

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

	class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			String message = ""; 
			for(int i: ids){
				message += i;
				message += " ";
			}
			System.out.println("ids : " + ids.size());
			writer.println(message + "$*!~" + " " + outgoing.getText());
			writer.flush();
			outgoing.setText("");
			outgoing.requestFocus();
		}
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
						incoming.append(message.substring(2*ids.size()+4) + "\n");
					}

				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
