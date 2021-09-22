package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Address;
import com.revature.utils.ConnectionUtil;

public class AddressDao {
	
	// gets the address id to use in other tables
	public static int getAddressId(String street_address, String city, String state, String zip_code) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			ResultSet rs = null;
			
			String sql = "SELECT address_id FROM addresses WHERE street_address = ? AND city = ? AND state = ? AND zip_code = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, street_address);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip_code);
			rs = ps.executeQuery();
			
			List<Integer> addressIdList = new ArrayList<Integer>();
			
			while (rs.next()) {
				addressIdList.add(rs.getInt("address_id"));
			}
			
			if (addressIdList.size() == 0) {
				return -1;
			} else {
				return addressIdList.get(0);
			}
					
		} catch (SQLException e) {
			System.out.println("Failed to get Address id!");
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public static void addAddress(Address address) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "INSERT INTO addresses (street_address, city, state, zip_code) "
					   + "VALUES (?, ?, ?, ?)";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, address.getStreet_address());
			ps.setString(2, address.getCity());
			ps.setString(3, address.getState());
			ps.setString(4, address.getZip_code());
			
			ps.executeUpdate(); // for anything that is NOT a SELECT statement, we use executeUpdate()
								
		} catch (SQLException e) {
			System.out.println("Failed to add Address!");
			e.printStackTrace();
		}
	}
}
