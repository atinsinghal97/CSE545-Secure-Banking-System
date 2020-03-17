package bankApp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the checking_account database table.
 * 
 */
@Entity
@Table(name="checking_account")
@NamedQuery(name="CheckingAccount.findAll", query="SELECT c FROM CheckingAccount c")
public class CheckingAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="account_id", nullable=false)
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

	private int approver;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date", nullable=false)
	private Date createdDate;

	@Column(name="current_balance", precision=10, scale=5)
	private BigDecimal currentBalance;

	@Column(precision=10, scale=5)
	private BigDecimal interest;

	@Column(name="user_id", nullable=false)
	private int userId;

	public CheckingAccount() {
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

	public int getApprover() {
		return this.approver;
	}

	public void setApprover(int approver) {
		this.approver = approver;
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

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}