package com.revature.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dao.AccountDao;
import com.revature.dao.AddressDao;
import com.revature.dao.LoginDao;
import com.revature.dao.UserDao;

public class Menu {
	
	private static Session currentUser;
	private static int currentBankAccount = -1;
	
	public void displayMenu() throws SQLException {
		
		boolean displayMenu = true;
		boolean inAccountMenu = false;
		boolean inAccountSelectionMenu = false;
		Scanner sc = new Scanner(System.in);
		
		Logger log = LogManager.getLogger(Menu.class);
		
		
		// greeting
		System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
		System.out.println("*~~~~~~~~~~~~~~~Welcome to New Horizon Financial!~~~~~~~~~~~~~~~*");
		System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
		System.out.println("*~Thank you for using our new and improved banking application!~*");
		System.out.println("*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*");
	
		while (displayMenu) {
			// choose options
			System.out.println("\n*~~~~~~~~~~~~~~~~~~~~~~~CHOOSE AN OPTION:~~~~~~~~~~~~~~~~~~~~~~~*\n");
			
			if (currentUser == null && !inAccountMenu && !inAccountSelectionMenu) {
				System.out.println("login -> Log into the application");
				System.out.println("create user -> Create a new user account");
			} else if (!inAccountMenu && !inAccountSelectionMenu) {
				System.out.println("summary -> See a summary of all your accounts");
				System.out.println("accounts -> Go to bank accounts menu");
				System.out.println("logout -> Log out of the application");
			} else if (!inAccountSelectionMenu) {
				System.out.println("select -> Select a bank account");
				System.out.println("create account -> Create a new bank account");
				System.out.println("back -> Go back to the User menu");
			} else {
				System.out.println("balance -> See current bank account balance");
				System.out.println("transfer funds -> Transfer funds to another account");
				System.out.println("add funds -> Add funds to bank account");
				System.out.println("close account -> Close your selected bank account");
				System.out.println("back -> Go back to the User menu");
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
					if (inAccountMenu == true) {
						System.out.println("I'm sorry, that isn't a valid menu option.");
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
						}
						
					}
					break;
					
				case "create user":
					printSeperator();
					
					if (currentUser != null) {
						System.out.println("You're already logged in!");
						break;
					}
					
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
					log.info("ACCOUNT WITH USERNAME: " + username + " CREATED");
					
					break;
				
				case "back":
					if (currentUser == null) {
						System.out.println("You are not logged in!");
						break;
					} else if (inAccountMenu == false) {
						System.out.println("You are not in the accounts menu!");
						break;
					}
					inAccountMenu = false;
					inAccountSelectionMenu = false;
					break;
					
				case "accounts":
					printSeperator();
					if (inAccountMenu == true) {
						System.out.println("I'm sorry, that isn't a valid menu option.");
						break;
					}
					if (currentUser == null) {
						System.out.println("You are not logged in!");
						break;
					} else {
						inAccountMenu = true;
					}
					System.out.println("Your Accounts: ");
					for (Account acc : AccountDao.getAccounts(currentUser.getUserId())) {
						System.out.println(acc);
					}
					
					break;
				
				case "create account":
					printSeperator();
					if (currentUser == null) {
						System.out.println("You are not logged in!");
						break;
					}
					System.out.println("What type of account would you like to create?");
					System.out.println("1 : Checking Account");
					System.out.println("2 : Savings Account");
					
					String accounttype_input = sc.nextLine();
					
					// validates account type input
					while (!accounttype_input.equals("1") && !accounttype_input.equals("2")) {
						System.out.println("Invalid account type for: " + accounttype_input);
						System.out.println("Please select from the options below.");
						System.out.println("1 : Checking Account");
						System.out.println("2 : Savings Account");
						
						accounttype_input = sc.nextLine();
					}
					
					AccountDao.addAccount(Integer.parseInt(accounttype_input), currentUser.getUserId());
					
					break;
					
				case "summary":
					printSeperator();
					if (currentUser == null) {
						System.out.println("You are not logged in!");
						break;
					}
					System.out.println("Your Accounts Summary: ");
					for (Account acc : AccountDao.getAccounts(currentUser.getUserId())) {
						System.out.println(acc);
					}
					if (inAccountMenu == true) {
						System.out.println("I'm sorry, that isn't a valid menu option.");
						break;
					}
					break;
				
				case "select":
					printSeperator();
					if (currentUser == null) {
						System.out.println("You are not logged in!");
						break;
					}
					
					// if user has no accounts, leave
					if (AccountDao.getAccounts(currentUser.getUserId()).isEmpty()) {
						System.out.println("You have no accounts!");
						break;
					}
					
					// user selects account, store that account id in currentBankAccount, set
					// inAccountSelectionMenu = true
					// print out users accounts
					System.out.println("Your Accounts:");
					List<Integer> validAccountIds = new ArrayList<Integer>();
					for (Account acc : AccountDao.getAccounts(currentUser.getUserId())) {
						System.out.println(acc);
						validAccountIds.add(acc.getAccount_id());
					}
					printSeperator();
					System.out.print("Select one of your accounts: ");
					String selectedAccountId = sc.nextLine();
					
					while (!validAccountIds.contains(Integer.parseInt(selectedAccountId))) {
						System.out.println("Invalid bank account number: " + selectedAccountId);
						System.out.println("Select one of your accounts: ");
						selectedAccountId = sc.nextLine();
					}
					
					System.out.println("Selected bank account: " + selectedAccountId);
					currentBankAccount = Integer.parseInt(selectedAccountId);
					inAccountSelectionMenu = true;
					
					break;
				
					
				case "add funds":
					printSeperator();
					// user not logged in
					if (currentUser == null) {
						System.out.println("You are not logged in!");
						break;
					}
					// user hasn't selected an account
					if (currentBankAccount == -1 || !inAccountSelectionMenu) {
						System.out.println("You haven't selected an account!");
						break;
					}
					
					System.out.print("How much would you like to add to your account?: ");
					double amountToAdd = sc.nextDouble();
					sc.nextLine();
					
					AccountDao.addFunds(currentBankAccount, amountToAdd);
					// print current balance
					System.out.println("Your current balance is now: $" 
					+ AccountDao.getAccountBalance(currentBankAccount, currentUser.getUserId()));
					
					break;
				
				case "balance":
					printSeperator();
					// user not logged in
					if (currentUser == null) {
						System.out.println("You are not logged in!");
						break;
					}
					// user hasn't selected an account
					if (currentBankAccount == -1 || !inAccountSelectionMenu) {
						System.out.println("You haven't selected an account!");
						break;
					}
					
					System.out.println("Your current balance for account " + currentBankAccount + " is: $"
							+ AccountDao.getAccountBalance(currentBankAccount, currentUser.getUserId()));
					
					break;
					
				case "transfer funds":
					
					// how much would you like to transfer
					System.out.print("How much would you like to transfer?: ");
					double amountToTransfer = sc.nextDouble();
					sc.nextLine();
					
					// check this account has enough
					while (amountToTransfer > AccountDao.getAccountBalance(currentBankAccount, currentUser.getUserId()) || amountToTransfer <= 0 ) {
						System.out.println("You don't have enough money!");
						System.out.println("Your current balance is: $" 
						+ AccountDao.getAccountBalance(currentBankAccount, currentUser.getUserId()));
						
						System.out.print("How much would you like to transfer?: ");
						amountToTransfer = sc.nextDouble();
						sc.nextLine();
					}
					
					// what account would you like to transfer to
					System.out.print("To what account would you like to transfer this to?: ");
					int accountToTransferTo = sc.nextInt();
					sc.nextLine();
					
					//
					while ((!AccountDao.getAllBankAccountIds().contains(accountToTransferTo)) || 
							(currentBankAccount == accountToTransferTo)) {
						System.out.println("This isn't a valid New Horizon Financial bank account!");
						System.out.println("You can send to any bank account number except your current account: " 
						+ currentBankAccount);
						
						System.out.print("To what account would you like to transfer this to?: ");
						accountToTransferTo = sc.nextInt();
						sc.nextLine();
					}
					
					System.out.println("Sending $" + amountToTransfer + " to account number: " + accountToTransferTo);
					System.out.print("Confirm transfer (Y/N): ");
					
					String response = sc.nextLine();
					if (response.toLowerCase().equals("y")) {
						AccountDao.transferFunds(currentBankAccount, accountToTransferTo, amountToTransfer);
						// TODO: LOG THIS!!!
					} else {
						System.out.println("Cancelling transfer!");
						break;
					}
					
					
					// current account balance
					System.out.println("Your current balance is now: $" 
					+ AccountDao.getAccountBalance(currentBankAccount, currentUser.getUserId()));
					
					break;
					
				case "close account":
					
					// close selected bank account
					System.out.println("Are you sure?");
					System.out.println("By closing your account you forfeit all remaining $"
							+ AccountDao.getAccountBalance(currentBankAccount, currentUser.getUserId()) 
							+ " to New Horizon Financial!");
					
					System.out.print("Confirm bank account closure (Y/N): ");
					String closeResponse = sc.nextLine();
					if (closeResponse.toLowerCase().equals("y")) {
						AccountDao.closeAccount(currentBankAccount, currentUser.getUserId());
					} else {
						System.out.println("Cancelling bank account closure!");
						break;
					}
					
					// session cleanup
					currentBankAccount = -1;
					inAccountSelectionMenu = false;
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
					if (inAccountMenu == true) {
						System.out.println("I'm sorry, that isn't a valid menu option.");
						break;
					}
					
					
					log.info("USER: " + currentUser.getUserId() + " SUCCESSFULLY LOGGED OUT");
					
					// clear session details
					currentUser = null;
					currentBankAccount = -1;
					inAccountMenu = false;
					inAccountSelectionMenu = false;
					
					System.out.println("Successfully logged out!");
					
					break;
					
				default:
					printSeperator();
					System.out.println("I'm sorry, that isn't a valid menu option.");
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
