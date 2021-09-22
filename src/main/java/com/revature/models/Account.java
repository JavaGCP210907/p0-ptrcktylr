package com.revature.models;

public class Account {
	private int account_id;
	private String date_opened;
	private String date_closed;
	private boolean is_open;
	private int accounttype_id;
	private int user_id;
	private double balance;
	
	
	
	// main constructor for adding accounts
	// accounttype_id=1 : checking
	// accounttype_id=2 : savings
	public Account(int accounttype_id, int user_id) {
		super();
		this.accounttype_id = accounttype_id;
		this.user_id = user_id;
	}
	
	// all args constructor
	public Account(int account_id, String date_opened, String date_closed, boolean is_open, int accounttype_id,
			int user_id, double balance) {
		super();
		this.account_id = account_id;
		this.date_opened = date_opened;
		this.date_closed = date_closed;
		this.is_open = is_open;
		this.accounttype_id = accounttype_id;
		this.user_id = user_id;
		this.balance = balance;
	}
	
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public String getDate_opened() {
		return date_opened;
	}
	public void setDate_opened(String date_opened) {
		this.date_opened = date_opened;
	}
	public String getDate_closed() {
		return date_closed;
	}
	public void setDate_closed(String date_closed) {
		this.date_closed = date_closed;
	}
	public boolean isIs_open() {
		return is_open;
	}
	public void setIs_open(boolean is_open) {
		this.is_open = is_open;
	}
	public int getAccounttype_id() {
		return accounttype_id;
	}
	public void setAccounttype_id(int accounttype_id) {
		this.accounttype_id = accounttype_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + account_id;
		result = prime * result + accounttype_id;
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((date_closed == null) ? 0 : date_closed.hashCode());
		result = prime * result + ((date_opened == null) ? 0 : date_opened.hashCode());
		result = prime * result + (is_open ? 1231 : 1237);
		result = prime * result + user_id;
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
		Account other = (Account) obj;
		if (account_id != other.account_id)
			return false;
		if (accounttype_id != other.accounttype_id)
			return false;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (date_closed == null) {
			if (other.date_closed != null)
				return false;
		} else if (!date_closed.equals(other.date_closed))
			return false;
		if (date_opened == null) {
			if (other.date_opened != null)
				return false;
		} else if (!date_opened.equals(other.date_opened))
			return false;
		if (is_open != other.is_open)
			return false;
		if (user_id != other.user_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [account_id=" + account_id + ", date_opened=" + date_opened + ", date_closed=" + date_closed
				+ ", is_open=" + is_open + ", accounttype_id=" + accounttype_id + ", user_id=" + user_id + ", balance="
				+ balance + "]";
	}
	
	
	
}
