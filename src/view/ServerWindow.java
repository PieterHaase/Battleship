package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import controller.Controller;
import network.GameServer;

public class ServerWindow extends JFrame {

	private Controller controller;
	private View view;
	private JLabel label1 = new JLabel("Creating Server, IP: ");
	private JLabel label2 = new JLabel("");
	private JLabel label3 = new JLabel("Enter your name: ");
	private JTextField playerName = new JTextField(20);
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	private JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
	private JPanel mainPanel = new JPanel();
	private JPanel subPanel1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
	private JPanel subPanel2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
	private int counter = 0;
	private boolean hasConnection = false;
	private Timer timer = new Timer(500, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			counter++;
			if (counter == 1)
				label2.setText("Waiting for client connection");
			if (counter == 2)
				label2.setText(label2.getText() + ".");
			if (counter == 3)
				label2.setText(label2.getText() + ".");
			if (counter == 4) {
				label2.setText(label2.getText() + ".");
				counter = 0;
			}
		}
	});

	public ServerWindow(Controller controller, GameServer server, String hostIP) {
		view = controller.getView();
		controller.getView().setEnabled(false);
		timer.start();
		okButton.setEnabled(false);
		label1.setText("Creating Server, IP: " + hostIP);
		this.setLayout(new BorderLayout());

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(new EmptyBorder(5, 20, 20, 20));
		subPanel2.add(label1);
		subPanel2.add(label2);
		mainPanel.add(subPanel2);
		subPanel1.add(label3);
		subPanel1.add(playerName);
		mainPanel.add(subPanel1);
		add(mainPanel, BorderLayout.CENTER);
		southPanel.add(cancelButton);
		southPanel.add(okButton);
		add(southPanel, BorderLayout.SOUTH);

		playerName.requestFocusInWindow();

		playerName.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				view.setEnabled(true);
			}
		});

		okButton.addActionListener(listener -> {
			controller.getModel().setPlayerName(playerName.getText());
			controller.getNetService().sendPlayerName(controller.getModel().getPlayerName());
			view.setEnabled(true);
			this.dispose();
		});

		cancelButton.addActionListener(listener -> {
			view.setEnabled(true);
			// Server Beenden
			this.dispose();
		});

		playerName.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				if (hasConnection) {
					if (playerName.getText().isEmpty())
						okButton.setEnabled(false);
					else
						okButton.setEnabled(true);
				}
			}
		});

		setTitle("Create Server");
		setSize(300, 200);
		setResizable(false);
		setLocationRelativeTo(view);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public JButton getOKButton() {
		return okButton;
	}

	public JTextField getPlayerName() {
		return playerName;
	}

	public void connectionReceived() {
		timer.stop();
		hasConnection = true;
		label2.setText("Connection received from ");
		if (!playerName.getText().isEmpty())
			okButton.setEnabled(true);
	}
}
