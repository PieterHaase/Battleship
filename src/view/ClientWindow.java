package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import controller.Controller;
import network.GameClient;

public class ClientWindow extends JFrame {

	private Controller controller;
	private View view;
	private JLabel label1 = new JLabel("");
	private JTextField ipAddress = new JTextField(20);
	private JLabel label2 = new JLabel("Enter your name: ");
	private JTextField playerName = new JTextField(20);
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	private JPanel subPanel1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
	private JPanel subPanel2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
	private JPanel mainPanel = new JPanel(new BorderLayout());
	private JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

	public ClientWindow(Controller controller, GameClient client) {
		this.controller = controller;
		view = controller.getView();
		view.setEnabled(false);
		label1.setText("Enter Server IP: ");
		okButton.setEnabled(false);

		setLayout(new BorderLayout());
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(new EmptyBorder(5, 20, 20, 20));
		subPanel1.add(label1);
		subPanel1.add(ipAddress);
		subPanel2.add(label2);
		subPanel2.add(playerName);
		mainPanel.add(subPanel1);
		mainPanel.add(subPanel2);
		southPanel.add(cancelButton);
		southPanel.add(okButton);
		add(mainPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);

		ipAddress.requestFocusInWindow();

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				view.setEnabled(true);
			}
		});

		playerName.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				if (playerName.getText().isEmpty())
					okButton.setEnabled(false);
				else
					okButton.setEnabled(true);
			}
		});

		okButton.addActionListener(listener -> {
			controller.getModel().setPlayerName(playerName.getText());
			client.setHostIP(ipAddress.getText());
			client.start();
			view.setEnabled(true);
			this.dispose();
		});

		cancelButton.addActionListener(listener -> {
			view.setEnabled(true);
			this.dispose();
		});

		setTitle("Join Game");
		setSize(300, 200);
		// pack();
		setResizable(false);
		setLocationRelativeTo(view);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}