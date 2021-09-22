package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.utils.ConnectionUtil;

public class AccountDao {
	
	// opens a new user account given the user_id
	public static void addAccount(int userId) {
		
	}
	
	// closes a user account given its account id
	public static Account getAccountById(int accountId) {
		return null;
	}
	
	
	public static void closeAccount(int accountId) {
		
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
						rs.getInt("balance")
						);
				
				accountList.add(account);
			}
			
			return accountList;
			
			
		} catch (SQLException e) {
			System.out.println("Something went wrong with your database!");
			e.printStackTrace();
		}
		return null;
	}
	
	// sends funds from one account to another account
	// make sure user only sending from their account
	// make sure they have enough
	// make sure receiver exists
	public static void transferFunds(int senderAccountId, int receiverAccountId, double amount) {
		
	}
	
	// adds funds to the account
	public static void addFunds(int accountId) {
		
	}
	
	
}
