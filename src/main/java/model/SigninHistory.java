package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the transaction database table.
 * 
 */
@Entity
@Table(name="login_history",schema = "secure_banking_system")
@NamedQuery(name="SigninHistory.findAll", query="SELECT t FROM Transaction t")

public class SigninHistory  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="ip_address")
	private String ipAddress;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="logged_in")
	private Date loggedIn;
	
	public SigninHistory() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public Date getLoggedIn() {
		return this.loggedIn;
	}

	public void setLoggedIn(Date loggedIn) {
		this.loggedIn = loggedIn;
	}
}
