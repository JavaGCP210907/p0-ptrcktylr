package com.revature.models;

import java.sql.SQLException;
import java.util.Scanner;

import com.revature.dao.AddressDao;
import com.revature.dao.LoginDao;
import com.revature.dao.UserDao;

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
					
					// if email or username are already in the table keep asking
					
					
					// add to logins table
						// username (check if exists)
					System.out.print("Please enter your desired username: ");
					String username = sc.nextLine();
					
					// check if username available
					while (!UserDao.isUsernameAvailable(username)) {
						System.out.println("That username is taken. Please use another one.");
						System.out.print("Please enter your desired username: ");
						username = sc.nextLine();
					}
					
						// password
					System.out.print("Please enter a password: ");
					String password = sc.nextLine();
					
					
					// add to users table
						// first name
					System.out.print("Please enter your first name: ");
					String first_name = sc.nextLine();
						// last name
					System.out.print("Please enter your last name: ");
					String last_name = sc.nextLine();
						// email (check if exists)
					System.out.print("Please enter your email address: ");
					String email = sc.nextLine();
					
					// check if email available
					while (!UserDao.isEmailAvailable(email)) {
						System.out.println("That email is taken. Please use another one.");
						System.out.print("Please enter your desired email: ");
						email = sc.nextLine();
					}
					
					
					// add to addresses table
						// street address
					System.out.print("Please enter your street number and address: ");
					String street_address = sc.nextLine();
						// city
					System.out.print("Please enter your city: ");
					String city = sc.nextLine();
						// state
					System.out.print("Please enter your state (ex: NY): ");
					String state = sc.nextLine();
					
					while (state.length() != 2) {
						System.out.println("Invalid state. Please use your state's two letter character abbreviation.");
						System.out.print("Please enter your state (ex: NY): ");
						state = sc.nextLine();
					}
					
						// zip code
					System.out.print("Please enter your zip code: ");
					String zip_code = sc.nextLine();
					
					// create address record
					Address address = new Address(street_address, city, state, zip_code);
					AddressDao.addAddress(address);
					
					// get added address id so we can add it to new user
					int newAddressId = AddressDao.getAddressId(street_address, city, state, zip_code);
					
					// create user record
					User user = new User(first_name, last_name, email, newAddressId);
					UserDao.addUser(user);
					
					// get user_id for login creation
					int newUserId = UserDao.getUserId(email);
					
					// create login record
					Login login = new Login(username, LoginDao.generateHash(password), newUserId);
					LoginDao.addLogin(login);
					
					
					System.out.println("User account successfully added!");
					
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
