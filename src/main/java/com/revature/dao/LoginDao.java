package com.revature.dao;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;
import com.revature.models.Login;
import com.revature.models.Session;
import com.revature.utils.ConnectionUtil;

public class LoginDao {
	
	public static void addLogin(Login login) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "INSERT INTO logins (user_id, username, password_hash) "
					   + "VALUES (?, ?, ?)";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, login.getUser_id());
			ps.setString(2, login.getUsername());
			ps.setString(3, login.getPassword_hash());
			
			ps.executeUpdate(); // for anything that is NOT a SELECT statement, we use executeUpdate()
								
		} catch (SQLException e) {
			System.out.println("Failed to add Login!");
			e.printStackTrace();
		}
	}
	
	// generate password hash using google hash function (no salt..)
	public static String generateHash(String plaintext) {
		String hash = Hashing.sha256()
					 .hashString(plaintext, StandardCharsets.UTF_8)
					 .toString();

		return hash;
	}
	
	// logs user in and return a session identifying the current logged in user
	public static Session verifyUser(String username, String password) throws SQLException {
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			ResultSet rs = null;
			
			// make, prepare, & execute the query
			String sql = "SELECT user_id FROM logins WHERE username = ? AND password_hash = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, generateHash(password));
			rs = ps.executeQuery();
			
			// put results into a list
			List<Integer> memberIdList = new ArrayList<Integer>();
			
			while(rs.next()) {
				memberIdList.add(rs.getInt("user_id"));
			}
			
			// if there are more than 1 items in the list two users have the same password hash and username...
			// this should never happen
			if (memberIdList.size() > 1) {
				System.out.println("Something went terribly wrong..");
				System.exit(1);
				return null;
			}
			
			// if there are 0 items in the list login failed 
			if (memberIdList.size() == 0) {
				System.out.println("Your username or password are incorrect.");
				return null;
			}
			
			if (memberIdList.size() == 1) {
				// log user in
				System.out.println("Login successful!");
				return new Session(memberIdList.get(0));
			}
		}
		
		return null;
	}
	
}
