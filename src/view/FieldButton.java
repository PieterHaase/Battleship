package view;

import javax.swing.JButton;

public class FieldButton extends JButton{

	private static final long serialVersionUID = 1L;
	
	private int xPos;
	private int yPos;
	
	public FieldButton(int x, int y){
//		super();
		xPos = x;
		yPos = y;
	}
	
	public int getXPos() {
		return xPos;
	}
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	public int getYPos() {
		return yPos;
	}
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
}
