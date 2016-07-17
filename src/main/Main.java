package main;

import java.util.Scanner;

public class Main {
	
	static boolean dev = true;
	
	public static void main(String[] args){
		
		String username = "";
		
		if(!dev){
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			System.out.print("Enter your username: ");
			username = reader.nextLine();
			reader.close();
		}
		
		System.out.println("Connecting to server...");
		new Game(username).run();
	    
	}
	
}
