package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ChatPanel extends JPanel {

	private ArrayList<String> messageList = new ArrayList<>();

	private Color bgColor = GUISettings.windowColor;
	private JPanel centerPanel = new JPanel(new BorderLayout());
	private JPanel bottomPanel = new JPanel(new BorderLayout());
	private JPanel textFieldPanel = new JPanel(new BorderLayout());

	private JTextArea textArea = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(textArea);
	private JTextField textField = new JTextField();
	private JButton sendButton = new JButton("Send");

	public ChatPanel() {
		this.setBackground(bgColor);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 10, 0, 10),
				BorderFactory.createTitledBorder(new EmptyBorder(0, 0, 0, 0), "Player Chat")));
		textArea.setBackground(Color.white);
		textArea.setEditable(false);
		textArea.setOpaque(true);
		scrollPane.setAutoscrolls(true);

		textFieldPanel.setBackground(bgColor);
		textFieldPanel.add(textField);
		textFieldPanel.setBorder(new EmptyBorder(0, 0, 0, 10));

		centerPanel.add(scrollPane);
		centerPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
		centerPanel.setBackground(bgColor);

		bottomPanel.add(textFieldPanel, BorderLayout.CENTER);
		bottomPanel.add(sendButton, BorderLayout.EAST);

		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
	}

	public void displayMessage(String source, String message) {
		if (textArea.getText().isEmpty())
			textArea.setText("[" + source + "]  " + message);
		else
			textArea.append("\n" + "[" + source + "]  " + message);

		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

	public void displayMessage(String message) {
		if (textArea.getText().isEmpty())
			textArea.setText(message);
		else
			textArea.append("\n" + message);

		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

	public JTextField getTextField() {
		return textField;
	}

	public JButton getSendButton() {
		return sendButton;
	}

}
