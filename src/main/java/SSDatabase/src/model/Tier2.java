package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tier2 database table.
 * 
 */
@Entity
@Table(name="tier2")
@NamedQuery(name="Tier2.findAll", query="SELECT t FROM Tier2 t")
public class Tier2 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="incorrect_attempts")
	private int incorrectAttempts;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	@Column(nullable=false, length=60)
	private String password;

	@Column(nullable=false)
	private int status;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="user_id", nullable=false)
	private int userId;

	@Column(name="user_type", length=1)
	private String userType;

	@Column(nullable=false, length=255)
	private String username;

	public Tier2() {
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getIncorrectAttempts() {
		return this.incorrectAttempts;
	}

	public void setIncorrectAttempts(int incorrectAttempts) {
		this.incorrectAttempts = incorrectAttempts;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}