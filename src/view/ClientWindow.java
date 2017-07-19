package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import network.GameClient;

public class ClientWindow extends JFrame{

	private JLabel label1 = new JLabel("");
	private JTextField textField = new JTextField(20);
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	private JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
	private JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
	private JPanel mainPanel = new JPanel(new BorderLayout());

	
	public ClientWindow(GameClient client){
		label1.setText("Enter Server IP: ");
	
		mainPanel.add(label1, BorderLayout.NORTH);
		centerPanel.setBorder(new EmptyBorder(20,0,0,0));
		centerPanel.add(textField);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
//		southPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		southPanel.add(cancelButton);
		southPanel.add(okButton);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		mainPanel.setBorder(new EmptyBorder(20,20,5,5));
		add(mainPanel);
		
		okButton.addActionListener(listener -> {
			client.setHostIP(textField.getText());
			client.start();
			this.dispose();
		});
		
		cancelButton.addActionListener(listener -> {
			this.dispose();
		});
		
		
		setTitle("Create Server");
		setSize(300,200);
//		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
//		textField.requestFocusInWindow();
	}
}