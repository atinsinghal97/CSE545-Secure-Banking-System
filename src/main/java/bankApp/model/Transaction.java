package bankApp.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the transaction database table.
 * 
 */
@Entity
@Table(name="transaction")
@NamedQuery(name="Transaction.findAll", query="SELECT t FROM Transaction t")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="transaction_id", unique=true, nullable=false)
	private int transactionId;

	@Column(precision=10, scale=5)
	private BigDecimal amount;

	@Column(name="approval_status", nullable=false)
	private boolean approvalStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="decision_date", nullable=false)
	private Date decisionDate;

	@Column(name="is_critical_transaction", nullable=false)
	private boolean isCriticalTransaction;

	@Column(name="level_1_approval")
	private boolean level1Approval;

	@Column(name="level_2_approval")
	private boolean level2Approval;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="requested_date", nullable=false)
	private Date requestedDate;

	@Column(name="transaction_type", nullable=false, length=1)
	private String transactionType;

	//bi-directional many-to-one association to Account
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="from_account", nullable=false)
	private Account account1;

	//bi-directional many-to-one association to Account
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="to_account", nullable=false)
	private Account account2;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="approver", nullable=false)
	private User user;

	public Transaction() {
	}

	public int getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public boolean getApprovalStatus() {
		return this.approvalStatus;
	}

	public void setApprovalStatus(boolean approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Date getDecisionDate() {
		return this.decisionDate;
	}

	public void setDecisionDate(Date decisionDate) {
		this.decisionDate = decisionDate;
	}

	public boolean getIsCriticalTransaction() {
		return this.isCriticalTransaction;
	}

	public void setIsCriticalTransaction(boolean isCriticalTransaction) {
		this.isCriticalTransaction = isCriticalTransaction;
	}

	public boolean getLevel1Approval() {
		return this.level1Approval;
	}

	public void setLevel1Approval(boolean level1Approval) {
		this.level1Approval = level1Approval;
	}

	public boolean getLevel2Approval() {
		return this.level2Approval;
	}

	public void setLevel2Approval(boolean level2Approval) {
		this.level2Approval = level2Approval;
	}

	public Date getRequestedDate() {
		return this.requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Account getAccount1() {
		return this.account1;
	}

	public void setAccount1(Account account1) {
		this.account1 = account1;
	}

	public Account getAccount2() {
		return this.account2;
	}

	public void setAccount2(Account account2) {
		this.account2 = account2;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}