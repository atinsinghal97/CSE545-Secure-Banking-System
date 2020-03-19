package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the login_history database table.
 * 
 */
@Entity
@Table(name="login_history")
@NamedQuery(name="LoginHistory.findAll", query="SELECT l FROM LoginHistory l")
public class LoginHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="device_type")
	private String deviceType;

	@Column(name="ip_address")
	private String ipAddress;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="logged_in")
	private Date loggedIn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="logged_out")
	private Date loggedOut;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public LoginHistory() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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

	public Date getLoggedOut() {
		return this.loggedOut;
	}

	public void setLoggedOut(Date loggedOut) {
		this.loggedOut = loggedOut;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}