package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="incorrect_attempts")
	private Integer incorrectAttempts;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	private String password;

	private String role;

	private Integer status;

	private String username;

	//bi-directional many-to-one association to Account
	@OneToMany(mappedBy="user")
	private List<Account> accounts;

	//bi-directional many-to-one association to Appointment
	@OneToMany(mappedBy="user1")
	private List<Appointment> appointments1;

	//bi-directional many-to-one association to Appointment
	@OneToMany(mappedBy="user2")
	private List<Appointment> appointments2;

	//bi-directional many-to-one association to LoginHistory
	@OneToMany(mappedBy="user")
	private List<LoginHistory> loginHistories;

	//bi-directional many-to-one association to Request
	@OneToMany(mappedBy="user1")
	private List<Request> requests1;

	//bi-directional many-to-one association to Request
	@OneToMany(mappedBy="user2")
	private List<Request> requests2;

	//bi-directional many-to-one association to UserDetail
	@OneToMany(mappedBy="user")
	private List<UserDetail> userDetails;

	public User() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getIncorrectAttempts() {
		return this.incorrectAttempts;
	}

	public void setIncorrectAttempts(Integer incorrectAttempts) {
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

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Account addAccount(Account account) {
		getAccounts().add(account);
		account.setUser(this);

		return account;
	}

	public Account removeAccount(Account account) {
		getAccounts().remove(account);
		account.setUser(null);

		return account;
	}

	public List<Appointment> getAppointments1() {
		return this.appointments1;
	}

	public void setAppointments1(List<Appointment> appointments1) {
		this.appointments1 = appointments1;
	}

	public Appointment addAppointments1(Appointment appointments1) {
		getAppointments1().add(appointments1);
		appointments1.setUser1(this);

		return appointments1;
	}

	public Appointment removeAppointments1(Appointment appointments1) {
		getAppointments1().remove(appointments1);
		appointments1.setUser1(null);

		return appointments1;
	}

	public List<Appointment> getAppointments2() {
		return this.appointments2;
	}

	public void setAppointments2(List<Appointment> appointments2) {
		this.appointments2 = appointments2;
	}

	public Appointment addAppointments2(Appointment appointments2) {
		getAppointments2().add(appointments2);
		appointments2.setUser2(this);

		return appointments2;
	}

	public Appointment removeAppointments2(Appointment appointments2) {
		getAppointments2().remove(appointments2);
		appointments2.setUser2(null);

		return appointments2;
	}

	public List<LoginHistory> getLoginHistories() {
		return this.loginHistories;
	}

	public void setLoginHistories(List<LoginHistory> loginHistories) {
		this.loginHistories = loginHistories;
	}

	public LoginHistory addLoginHistory(LoginHistory loginHistory) {
		getLoginHistories().add(loginHistory);
		loginHistory.setUser(this);

		return loginHistory;
	}

	public LoginHistory removeLoginHistory(LoginHistory loginHistory) {
		getLoginHistories().remove(loginHistory);
		loginHistory.setUser(null);

		return loginHistory;
	}

	public List<Request> getRequests1() {
		return this.requests1;
	}

	public void setRequests1(List<Request> requests1) {
		this.requests1 = requests1;
	}

	public Request addRequests1(Request requests1) {
		getRequests1().add(requests1);
		requests1.setUser1(this);

		return requests1;
	}

	public Request removeRequests1(Request requests1) {
		getRequests1().remove(requests1);
		requests1.setUser1(null);

		return requests1;
	}

	public List<Request> getRequests2() {
		return this.requests2;
	}

	public void setRequests2(List<Request> requests2) {
		this.requests2 = requests2;
	}

	public Request addRequests2(Request requests2) {
		getRequests2().add(requests2);
		requests2.setUser2(this);

		return requests2;
	}

	public Request removeRequests2(Request requests2) {
		getRequests2().remove(requests2);
		requests2.setUser2(null);

		return requests2;
	}

	public List<UserDetail> getUserDetails() {
		return this.userDetails;
	}

	public void setUserDetails(List<UserDetail> userDetails) {
		this.userDetails = userDetails;
	}

	public UserDetail addUserDetail(UserDetail userDetail) {
		getUserDetails().add(userDetail);
		userDetail.setUser(this);

		return userDetail;
	}

	public UserDetail removeUserDetail(UserDetail userDetail) {
		getUserDetails().remove(userDetail);
		userDetail.setUser(null);

		return userDetail;
	}

}