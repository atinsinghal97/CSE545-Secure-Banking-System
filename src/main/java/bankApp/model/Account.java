package bankApp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@Table(name="account")
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="account_id", unique=true, nullable=false)
	private int accountId;

	@Column(name="account_number", nullable=false, length=255)
	private String accountNumber;

	@Column(name="account_type", nullable=false, length=1)
	private String accountType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="approval_date")
	private Date approvalDate;

	@Column(name="approval_status", nullable=false)
	private boolean approvalStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date", nullable=false)
	private Date createdDate;

	@Column(name="current_balance", precision=10, scale=5)
	private BigDecimal currentBalance;

	@Column(precision=10, scale=5)
	private BigDecimal interest;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="approver")
	private User user1;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	private User user2;
	
	private int approver_id;
	private int user_id;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="account1")
	private List<Transaction> transactions1;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="account2")
	private List<Transaction> transactions2;

	public Account() {
	}

	public int getAccountId() {
		return this.accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return this.accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Date getApprovalDate() {
		return this.approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public boolean getApprovalStatus() {
		return this.approvalStatus;
	}

	public void setApprovalStatus(boolean approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public BigDecimal getCurrentBalance() {
		return this.currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public BigDecimal getInterest() {
		return this.interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public User getUser1() {
		return this.user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return this.user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public List<Transaction> getTransactions1() {
		return this.transactions1;
	}

	public void setTransactions1(List<Transaction> transactions1) {
		this.transactions1 = transactions1;
	}

	public Transaction addTransactions1(Transaction transactions1) {
		getTransactions1().add(transactions1);
		transactions1.setAccount1(this);

		return transactions1;
	}

	public Transaction removeTransactions1(Transaction transactions1) {
		getTransactions1().remove(transactions1);
		transactions1.setAccount1(null);

		return transactions1;
	}

	public List<Transaction> getTransactions2() {
		return this.transactions2;
	}

	public void setTransactions2(List<Transaction> transactions2) {
		this.transactions2 = transactions2;
	}

	public Transaction addTransactions2(Transaction transactions2) {
		getTransactions2().add(transactions2);
		transactions2.setAccount2(this);

		return transactions2;
	}

	public Transaction removeTransactions2(Transaction transactions2) {
		getTransactions2().remove(transactions2);
		transactions2.setAccount2(null);

		return transactions2;
	}

}