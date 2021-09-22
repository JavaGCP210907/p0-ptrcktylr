package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class UserDao {

	public static void addUser(User user) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "INSERT INTO users (first_name, last_name, email, address_id) "
					   + "VALUES (?, ?, ?, ?)";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, user.getFirst_name());
			ps.setString(2, user.getLast_name());
			ps.setString(3, user.getEmail());
			ps.setInt(4, user.getAddress_id());
			
			ps.executeUpdate(); // for anything that is NOT a SELECT statement, we use executeUpdate()
								
		} catch (SQLException e) {
			System.out.println("Failed to add User!");
			e.printStackTrace();
		}
	}
	
	// since emails are unique we can use it to get user_id
	public static int getUserId(String email) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			ResultSet rs = null;
			
			String sql = "SELECT user_id FROM users WHERE email = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			
			List<Integer> userIdList = new ArrayList<Integer>();
			
			while (rs.next()) {
				userIdList.add(rs.getInt("user_id"));
			}
			
			if (userIdList.size() == 0) {
				return -1;
			} else {
				return userIdList.get(0);
			}
					
		} catch (SQLException e) {
			System.out.println("Failed to get User id!");
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public static boolean isUsernameAvailable(String username) {
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			ResultSet rs = null;
			String sql = "SELECT * FROM logins WHERE username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			
			// if this is empty username is available
			rs = ps.executeQuery();
			
			// if rs.next is false the result set is empty, so the username isn't in the table
			return (rs.next() == false);
					
		} catch (SQLException e) {
			System.out.println("Failed to check if username was available!");
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean isEmailAvailable(String email) {
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			ResultSet rs = null;
			String sql = "SELECT * FROM users WHERE email = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			
			// if this is empty email is available
			rs = ps.executeQuery();
			
			// if rs.next is false the result set is empty, so the email isn't in the table
			return (rs.next() == false);
					
		} catch (SQLException e) {
			System.out.println("Failed to check if email was available!");
			e.printStackTrace();
		}
		
		return false;
	}
	
}
