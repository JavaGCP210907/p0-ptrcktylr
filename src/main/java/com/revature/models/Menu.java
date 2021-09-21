package com.revature.models;

import java.util.Scanner;

public class Menu {
	
	public void displayMenu() {
		boolean displayMenu = true;
		Scanner sc = new Scanner(System.in);
	
		// greeting
		System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
		System.out.println("*~~~~~~~~~~~~~~~Welcome to New Horizon Financial!~~~~~~~~~~~~~~~*");
		System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
		System.out.println("*~Thank you for using our new and improved banking application!~*");
		System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
	
		while (displayMenu) {
			// choose options
			System.out.println("\n*~~~~~~~~~~~~~~~~~~~~~~~CHOOSE AN OPTION:~~~~~~~~~~~~~~~~~~~~~~~*\n");
			
			System.out.println("login -> Log into the application");
			System.out.println("create account -> Create a new user account");
			System.out.println("quit -> Quit the application \n");
			
			// get user input
			String input = sc.nextLine();
			switch (input) {
			
				case "login":
					printSeperator();
					boolean valid = false;
					
					while (!valid) {
						
						// get username & password
						System.out.print("Please enter your account username: ");
						String username = sc.nextLine();
						
						System.out.print("Please enter your account password: ");
						String password = sc.nextLine();
						
						// verify with database
						
						
						// if valid, set current user to User object
						
						
						
					}
					 
					
					break;
					
				case "create account":
					printSeperator();
					System.out.println("***create account***");
					break;
				
				case "quit":
					printSeperator();
					System.out.println("Thank you for using our new and improved banking application!");
					System.out.println("Goodbye!");
					displayMenu = false;
					break;
					
				default:
					printSeperator();
					System.out.println("I'm sorry, that it's a valid menu option.");
					break;
					
			}
		}
		
		// close scanner to prevent resource leak
		sc.close();
	}
	
	public void printSeperator() {
		System.out.println("\n*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*\n");
	}
}
