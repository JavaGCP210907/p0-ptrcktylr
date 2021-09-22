package com.revature.models;

public class Login {
	private int login_id; // primary key
	private String username;
	private String password_hash;

	private int user_id; // foreign key

	public Login(String username, String password_hash, int user_id) {
		super();
		this.username = username;
		this.password_hash = password_hash;
		this.user_id = user_id;
	}

	public Login(int login_id, String username, String password_hash, int user_id) {
		super();
		this.login_id = login_id;
		this.username = username;
		this.password_hash = password_hash;
		this.user_id = user_id;
	}

	public int getLogin_id() {
		return login_id;
	}

	public void setLogin_id(int login_id) {
		this.login_id = login_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword_hash() {
		return password_hash;
	}

	public void setPassword_hash(String password_hash) {
		this.password_hash = password_hash;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + login_id;
		result = prime * result + ((password_hash == null) ? 0 : password_hash.hashCode());
		result = prime * result + user_id;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Login other = (Login) obj;
		if (login_id != other.login_id)
			return false;
		if (password_hash == null) {
			if (other.password_hash != null)
				return false;
		} else if (!password_hash.equals(other.password_hash))
			return false;
		if (user_id != other.user_id)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
}
