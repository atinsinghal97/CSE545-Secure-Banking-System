package com.example.hibernate;
// Generated Mar 13, 2020, 2:30:54 PM by Hibernate Tools 5.4.7.Final

/**
 * UserDetails generated by hbm2java
 */
public class UserDetails implements java.io.Serializable {

	private UserDetailsId id;
	private User user;

	public UserDetails() {
	}

	public UserDetails(UserDetailsId id, User user) {
		this.id = id;
		this.user = user;
	}

	public UserDetailsId getId() {
		return this.id;
	}

	public void setId(UserDetailsId id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
