package bankApp.model;

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
	@Column(name="user_id", unique=true, nullable=false)
	private int userId;

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

	@Column(name="user_type", length=1)
	private String userType;

	@Column(nullable=false, length=255)
	private String username;

	//bi-directional many-to-one association to Account
	@OneToMany(mappedBy="user1")
	private List<Account> accounts1;

	//bi-directional many-to-one association to Account
	@OneToMany(mappedBy="user2")
	private List<Account> accounts2;

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

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="user")
	private List<Transaction> transactions;

	//bi-directional one-to-one association to UserDetail
	@OneToOne(mappedBy="user", fetch=FetchType.LAZY)
	private UserDetail userDetail;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public List<Account> getAccounts1() {
		return this.accounts1;
	}

	public void setAccounts1(List<Account> accounts1) {
		this.accounts1 = accounts1;
	}

	public Account addAccounts1(Account accounts1) {
		getAccounts1().add(accounts1);
		accounts1.setUser1(this);

		return accounts1;
	}

	public Account removeAccounts1(Account accounts1) {
		getAccounts1().remove(accounts1);
		accounts1.setUser1(null);

		return accounts1;
	}

	public List<Account> getAccounts2() {
		return this.accounts2;
	}

	public void setAccounts2(List<Account> accounts2) {
		this.accounts2 = accounts2;
	}

	public Account addAccounts2(Account accounts2) {
		getAccounts2().add(accounts2);
		accounts2.setUser2(this);

		return accounts2;
	}

	public Account removeAccounts2(Account accounts2) {
		getAccounts2().remove(accounts2);
		accounts2.setUser2(null);

		return accounts2;
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

	public List<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Transaction addTransaction(Transaction transaction) {
		getTransactions().add(transaction);
		transaction.setUser(this);

		return transaction;
	}

	public Transaction removeTransaction(Transaction transaction) {
		getTransactions().remove(transaction);
		transaction.setUser(null);

		return transaction;
	}

	public UserDetail getUserDetail() {
		return this.userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}