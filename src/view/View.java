package view;
import model.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * 
 * @author Pieter Haase
 * @version 1.0
 */

public class View extends JFrame{

	private static final long serialVersionUID = 1L;

	private JPanel mainPanel = new JPanel(new BorderLayout());
	private JPanel middlePanel = new JPanel(new GridLayout(1,2,5,0));
	private JPanel leftPanel = new JPanel();
	private JPanel rightPanel = new JPanel(new BorderLayout());
	private JPanel leftGameField = new JPanel(new GridLayout(10,10,0,0));
	
	private JLabel myShips = new JLabel("My Ships");
	private JLabel enemyShips = new JLabel("Enemy ships");
	
	public View (Model model){
		
		for (int i=0; i < 100; i++){
			JButton button = new JButton();
			//button.setPreferredSize(new Dimension(20,20));
			leftGameField.add(button);
		}
		leftGameField.setBackground(Color.gray);
		leftPanel.setBorder(new EmptyBorder(10, 10, 20, 10));
		leftPanel.setBackground(Color.gray);
		leftPanel.add(myShips/*, BorderLayout.NORTH*/);
		leftPanel.add(leftGameField);
		rightPanel.add(enemyShips, BorderLayout.NORTH);
		//rightPanel.setBackground(Color.gray);
		leftGameField.setPreferredSize(new Dimension(350,350));
		middlePanel.add(leftPanel);
		middlePanel.add(rightPanel);
		//middlePanel.add(button);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(mainPanel);
		
		setTitle("Battleship");
		pack();
		setSize(800, 450);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
