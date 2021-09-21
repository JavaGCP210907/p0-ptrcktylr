package com.revature.models;

import java.sql.SQLException;
import java.util.Scanner;

import com.revature.dao.LoginDao;

public class Menu {
	
	private static Session currentUser;
	
	public void displayMenu() throws SQLException {
		
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
			
			if (currentUser == null) {
				System.out.println("login -> Log into the application");
				System.out.println("create account -> Create a new user account");
			} else {
				System.out.println("logout -> Log out of the application");
			}

			System.out.println("quit -> Quit the application \n");
			
			// get user input
			String input = sc.nextLine();
			switch (input) {
			
				case "login":
					printSeperator();
					
					if (currentUser != null) {
						System.out.println("You're already logged in!");
						break;
					}
					
					boolean valid = false;
					
					while (!valid) {
						
						// get username & password
						System.out.print("Please enter your account username: ");
						String username = sc.nextLine();
						
						System.out.print("Please enter your account password: ");
						String password = sc.nextLine();
						
						// verify with database
						// if valid, set current user to User object
						Session currentUserSession = LoginDao.verifyUser(username, password);
						if (currentUserSession != null) {
							valid = true;
							currentUser = currentUserSession;
							System.out.println("Thanks for logging in!");
						}
						
					}
					 
					
					break;
					
				case "create account":
					printSeperator();
					
					if (currentUser != null) {
						System.out.println("You're already logged in!");
						break;
					}
					
					System.out.println("***create account***");
					break;
				
				case "quit":
					printSeperator();
					System.out.println("Thank you for using our new and improved banking application!");
					System.out.println("Goodbye!");
					displayMenu = false;
					break;
					
				case "logout":
					printSeperator();
					if (currentUser == null) {
						System.out.println("You are not logged in!");
						break;
					}
					currentUser = null;
					System.out.println("Successfully logged out!");
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
