package com.revature;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.dao.AddressDao;
import com.revature.dao.LoginDao;
import com.revature.models.Menu;
import com.revature.models.Session;
import com.revature.utils.ConnectionUtil;

public class Driver {
	public static void main(String[] args) throws SQLException {
			
			try(Connection conn = ConnectionUtil.getConnection()) {
				// log successful connection
			} catch (SQLException e) {
				System.out.println("Connection failed..");
				e.printStackTrace();
			}
			
			Menu menu = new Menu();
			menu.displayMenu();
			
	}
}
