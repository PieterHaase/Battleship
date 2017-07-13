package view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;

public class ChatPanel extends JPanel{
	
	private ArrayList<String> messageList = new ArrayList<>();
	
	private Color bgColor = GUISettings.windowColor;
	private JPanel centerPanel = new JPanel(new BorderLayout());
	private JPanel bottomPanel = new JPanel(new BorderLayout());
	private JPanel textFieldPanel = new JPanel(new BorderLayout());
	
//	private JLabel label = new JLabel("");
	private JTextArea textArea = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(textArea);
	private JTextField textField = new JTextField();
	private JButton sendButton = new JButton("Send");
	
	public ChatPanel(){
//		Border titleBorder = BorderFactory.createCompoundBorder(new EmptyBorder(10,10,10,10), BorderFactory.createTitledBorder(new LineBorder(Color.white), " Player Chat "));
		this.setBackground(bgColor);
		this.setLayout(new BorderLayout());
//		this.setBorder(BorderFactory.createCompoundBorder(titleBorder,new EmptyBorder(0,5,5,5)));
		this.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0,10,10,10), BorderFactory.createTitledBorder(new EmptyBorder(0,0,0,0), "Player Chat")));
//		this.setBorder(new EmptyBorder(10,10,10,10));
//		textArea.setVerticalAlignment(SwingConstants.TOP);
		textArea.setBackground(Color.white);
		textArea.setEditable(false);
		textArea.setOpaque(true);
		scrollPane.setAutoscrolls(true);
		
		
		textFieldPanel.setBackground(bgColor);
		textFieldPanel.add(textField);
		textFieldPanel.setBorder(new EmptyBorder(0,0,0,10));
		
		centerPanel.add(scrollPane);
		centerPanel.setBorder(new EmptyBorder(0,0,10,0));
		centerPanel.setBackground(bgColor);
		
		bottomPanel.add(textFieldPanel, BorderLayout.CENTER);
		bottomPanel.add(sendButton, BorderLayout.EAST);
		
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		
	}
	
	public void displayMessage(String message){
		if (textArea.getText().isEmpty())
			textArea.setText(message);
		else
			textArea.append("\n " + message);
			
//		messageList.add(message);
//		String previousMessages = label.getText();
//		for (String currentMessage : messageList)
//		label.setText(label.getText() + " " + message);
//		label.setText("<html><body>" + label.getText()  + message + "<br></body></html>");
//		textArea.setText(label.getText() + "\n " + currentMessage);
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		ChatPanel chatPanel = new ChatPanel(); 
		frame.add(chatPanel);
		frame.setSize(500,300);
		//frame.setDefaultCloseOperation(operation);
		frame.setVisible(true);
		
		chatPanel.displayMessage("Hallo");
		chatPanel.displayMessage("dies");
		chatPanel.displayMessage("ist");
		chatPanel.displayMessage("ein");
		chatPanel.displayMessage("Test");

	}

}
