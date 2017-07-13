package controller;

import java.util.Scanner;

public class ConsoleIO {
	
	public static String read(){
		Scanner input = new Scanner(System.in);
		String string = input.nextLine();
		input.close();
		return string;
	}
	
	public static void write(String string){
		System.out.println(string);
	}
	
	public static void write(int integer){
		System.out.println(integer);
	}
}