package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import network.GameServer;

public class ServerWindow extends JFrame{
	
	private JLabel label1 = new JLabel("");
	private JLabel label2 = new JLabel("");
//	private JTextField textField = new JTextField(20);
	private JButton cancelButton = new JButton("Cancel");
	private JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
	private JPanel mainPanel = new JPanel(new BorderLayout());
	private int counter = 0;
	private Timer timer = new Timer(500, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			counter++;
			if(counter == 1)
				label2.setText("Waiting for client connection");
			if(counter == 2)
				label2.setText(label2.getText() + ".");
			if(counter == 3)
				label2.setText(label2.getText() + ".");
			if(counter == 4){
				label2.setText(label2.getText() + ".");
				counter = 0;
			}
		}
	});
	
	public ServerWindow(GameServer server, String hostIP){
		timer.start();
		label1.setText("Creating Server, IP: " + hostIP);
		this.setLayout(new BorderLayout());

		
	
		mainPanel.add(label1, BorderLayout.NORTH);
		mainPanel.add(label2, BorderLayout.CENTER);
//		southPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		southPanel.add(cancelButton);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		mainPanel.setBorder(new EmptyBorder(20,20,5,5));
		add(mainPanel);
		
		cancelButton.addActionListener(listener -> {
			//Server Beenden
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

