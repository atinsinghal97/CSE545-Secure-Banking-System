package forms;

import java.util.Date;

import model.User;

public class UserProfile {
	private String Username;
	private Integer status;
	private Integer incorrectAttempts;
	private Date createdDate;
	private Date modifiedDate;
	private String role;

	public UserProfile(User u) {
		this.setUsername(u.getUsername());
		this.setCreatedDate(u.getCreatedDate());
		this.setModifiedDate(u.getModifiedDate());
		this.setStatus(u.getStatus());
		this.setIncorrectAttempts(u.getIncorrectAttempts());
		this.setRole(u.getRole());
	}
	
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIncorrectAttempts() {
		return incorrectAttempts;
	}
	public void setIncorrectAttempts(Integer incorrectAttempts) {
		this.incorrectAttempts = incorrectAttempts;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
