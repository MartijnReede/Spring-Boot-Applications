package com.CMA.session;

public class Session {
	
	boolean loggedIn;
	private String userType;
	private long userId;
	
	public Session(boolean loggedIn, String userType, long userId) {
		this.loggedIn = loggedIn;
		this.userType = userType;
		this.userId = userId;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
