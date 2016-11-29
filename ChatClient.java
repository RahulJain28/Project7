package assignment7;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatClient {
	private JTextArea incoming;
	private JTextField outgoing;
	private BufferedReader reader;
	private PrintWriter writer;
	String username;
	int id1;
	int id2;

	public ChatClient(String u, int id1, int id2){
		username =u;
		this.id1 = id1;
		this.id2 = id2;
	}

	public void run() throws Exception {
		initView();
		setUpNetworking();
	}

	private void initView() {
		JFrame frame = new JFrame("Chat with " + username);
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
		Socket sock = new Socket("127.0.0.1", 4242);
		InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
		reader = new BufferedReader(streamReader);
		writer = new PrintWriter(sock.getOutputStream());
		System.out.println("networking established");
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();
	}

	class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			writer.println(String.valueOf(id1) + " " + String.valueOf(id2) + " " + outgoing.getText());
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
					System.out.println(id1);
					System.out.println(id2);
					System.out.println(message);
					String[] words = message.split(" "); 
					System.out.print(words[0]);
					System.out.print(words[1]);
					if((words[0].equals(String.valueOf(id1)) && words[1].equals(String.valueOf(id2))) || (words[0].equals(String.valueOf(id2)) && words[1].equals(String.valueOf(id1)))){
						System.out.println("prints");
						incoming.append(message.substring(3) + "\n");
					}

				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
