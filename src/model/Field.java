package model;

public class Field {
	
	private String content; 
	private boolean isHit = false;
	
	public Field(String content){
		this.content = content;
	}

	public boolean isHit(){				//gibt zurück, ob das Feld bereits getroffen wurde
		return isHit;
	}
	
	public String getContent(){
		return content;
	}
}


