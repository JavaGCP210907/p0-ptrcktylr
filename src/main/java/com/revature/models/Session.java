package com.revature.models;

// when a user logs in, the session is created with their user_id
// log when a user successfully logs in

public class Session {
	
	private int user_id;
	
	public Session(int user_id) {
		this.user_id = user_id;
	}
	
	public int getUserId() {
		return this.user_id;
	}
	
	// when a user logs in, make the session object and return it
	public static Session login() {
		return null;
	}
}
