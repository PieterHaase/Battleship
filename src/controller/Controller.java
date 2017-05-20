package controller;
import model.*;
import view.*;


public class Controller {
	
	public Controller(){
		Model model = new Model();
		View view = new View(model);
		
	}
	
	public static void main(String[] args) {
		new Controller();
	}
	
}
