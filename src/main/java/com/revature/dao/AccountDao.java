package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Account;
import com.revature.models.Menu;
import com.revature.utils.ConnectionUtil;

public class AccountDao {
	
	static Logger log = LogManager.getLogger(Menu.class);
	
	// opens a new bank account given the user_id and accounttype_id
	public static void addAccount(int accounttypeId, int userId) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "INSERT INTO accounts (accounttype_id, user_id) VALUES (?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, accounttypeId);
			ps.setInt(2, userId);
			
			ps.executeUpdate();
			
			System.out.println("Successfully created new bank account!");
			// TODO: LOG USER_ID CREATED BANK ACCOUNT
			
		} catch (SQLException e) {
			System.out.println("Failed to create new account!");
			e.printStackTrace();
		}
	}
	
	// closes a user account given its account id
	
	public static void closeAccount(int accountId, int userId) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "DELETE FROM accounts WHERE account_id = ? AND user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, accountId);
			ps.setInt(2, userId);
			
			ps.executeUpdate();
			
			System.out.println("Successfully deleted bank account!");
			log.info("USER: " + userId + " DELETED BANK ACCOUNT: " + accountId);
			
		} catch (SQLException e) {
			System.out.println("Failed to delete bank account!");
			e.printStackTrace();
		}
	}
	
	public static double getAccountBalance(int accountId, int userId) {
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			ResultSet rs = null;
			String sql = "SELECT balance FROM accounts WHERE account_id = ? AND user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, accountId);
			ps.setInt(2, userId);
			
			rs = ps.executeQuery();
			
			List<Double> balanceList = new ArrayList<Double>();
			while (rs.next()) {
				balanceList.add(rs.getDouble("balance"));
			}
			
			if (balanceList.size() == 0) {
				return -1;
			} else {
				return balanceList.get(0);
			}
			
			
		} catch (SQLException e) {
			System.out.println("Couldn't get account balance!");
			e.printStackTrace();
		}
		
		return -1;
	}
	
	// returns list of all the users accounts 
	public static List<Account> getAccounts(int userId) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			ResultSet rs = null;
			
			// need to join employees to their roles in order to access the role_title column and return employees
			String sql = "SELECT * FROM accounts WHERE user_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			
			List<Account> accountList = new ArrayList<Account>();
			
			while(rs.next()) { // while there are results in the ResultSet..
				
				
				Account account = new Account(
						rs.getInt("account_id"),
						rs.getString("date_opened"),
						rs.getString("date_closed"),
						rs.getBoolean("is_open"),
						rs.getInt("accounttype_id"),
						rs.getInt("user_id"),
						rs.getDouble("balance")
						);
				
				accountList.add(account);
			}
			
			return accountList;
			
			
		} catch (SQLException e) {
			System.out.println("Couldn't get user bank accounts!");
			e.printStackTrace();
		}
		return null;
	}
	
	// sends funds from one account to another account
	// make sure user only sending from their account
	// make sure they have enough
	// make sure receiver exists
	public static void transferFunds(int senderAccountId, int receiverAccountId, double amount) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			// reduce sender balance
			String sql1 = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setDouble(1, amount);
			ps1.setInt(2, senderAccountId);
			
			// increase receiver balance
			String sql2 = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setDouble(1, amount);
			ps2.setInt(2, receiverAccountId);
			
			ps1.executeUpdate();
			ps2.executeUpdate();
			
			System.out.println("Successfully transfered $" + amount + " from account " + senderAccountId
					+ " to account " + receiverAccountId);
			
			log.info("BALANCE TRANSFER OF $" + amount 
					+ " FROM ACCOUNT: " + senderAccountId 
					+ " TO ACCOUNT: " + receiverAccountId);
			
		} catch (SQLException e) {
			System.out.println("Unable to transfer funds!");
			e.printStackTrace();
		}
	}
	
	// adds funds to the account
	public static void addFunds(int accountId, double amount) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, amount);
			ps.setInt(2, accountId);
			
			ps.executeUpdate();
			
			System.out.println("Successfully added " + amount + " to your bank account!");
			// TODO: log this
			
		} catch (SQLException e) {
			System.out.println("Couldn't add funds to your account!");
			e.printStackTrace();
		}
	}
	
	// get all bank account ids
	// this is used to check for an existing account before transfer of funds
	public static List<Integer> getAllBankAccountIds() {
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			ResultSet rs = null;
			
			String sql = "SELECT account_id FROM accounts";
			Statement s = conn.createStatement();
			
			rs = s.executeQuery(sql);
			
			List<Integer> allBankAccountIds = new ArrayList<Integer>();
			
			while (rs.next()) {
				allBankAccountIds.add(rs.getInt("account_id"));
			}
			
			return allBankAccountIds;
			
		} catch (SQLException e) {
			System.out.println("Couldn't add funds to your account!");
			e.printStackTrace();
		}
		return null;
	}
	
}
