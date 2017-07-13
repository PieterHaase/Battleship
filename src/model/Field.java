package model;

import java.io.Serializable;

public class Field  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int xPos, yPos;
	Ship parent;
	private String content; 
	private boolean isHit = false;
	
	public Field(int xPos, int yPos, String content){
		this.xPos = xPos;
		this.yPos = yPos;
		this.content = content;		
	}	

	public boolean isHit(){				//gibt zurück, ob das Feld bereits getroffen wurde
		return isHit;
	}
	
	public String getContent(){
		return content;
	}
	
	public void markAsHit(){
		isHit = true;
	}
	
	public Ship getParent(){
		return parent;
	}
	
	public void setParent(Ship parent){
		this.parent = parent;
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
	public void setPosition(int x, int y){
		xPos = x;
		yPos = y;
	}
}


