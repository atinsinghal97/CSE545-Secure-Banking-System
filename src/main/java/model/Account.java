package model;

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
	private Integer id;

	@Column(name="account_number")
	private String accountNumber;

	@Column(name="account_type")
	private String accountType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="approval_date")
	private Date approvalDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="current_balance")
	private BigDecimal currentBalance;

	private BigDecimal interest;

	private Integer status;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="account1")
	private List<Transaction> transactions1;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="account2")
	private List<Transaction> transactions2;

	public Account() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
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