package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the non_critical_transaction database table.
 * 
 */
@Entity
@Table(name="non_critical_transaction")
@NamedQuery(name="NonCriticalTransaction.findAll", query="SELECT n FROM NonCriticalTransaction n")
public class NonCriticalTransaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(precision=10, scale=5)
	private BigDecimal amount;

	@Column(name="approval_status", nullable=false)
	private boolean approvalStatus;

	@Column(nullable=false)
	private int approver;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="decision_date", nullable=false)
	private Date decisionDate;

	@Column(name="from_account", nullable=false)
	private int fromAccount;

	@Column(name="is_critical_transaction", nullable=false)
	private boolean isCriticalTransaction;

	@Column(name="level_1_approval")
	private boolean level1Approval;

	@Column(name="level_2_approval")
	private boolean level2Approval;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="requested_date", nullable=false)
	private Date requestedDate;

	@Column(name="to_account", nullable=false)
	private int toAccount;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="transaction_id", nullable=false)
	private int transactionId;

	@Column(name="transaction_type", nullable=false, length=1)
	private String transactionType;

	public NonCriticalTransaction() {
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

	public int getApprover() {
		return this.approver;
	}

	public void setApprover(int approver) {
		this.approver = approver;
	}

	public Date getDecisionDate() {
		return this.decisionDate;
	}

	public void setDecisionDate(Date decisionDate) {
		this.decisionDate = decisionDate;
	}

	public int getFromAccount() {
		return this.fromAccount;
	}

	public void setFromAccount(int fromAccount) {
		this.fromAccount = fromAccount;
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

	public int getToAccount() {
		return this.toAccount;
	}

	public void setToAccount(int toAccount) {
		this.toAccount = toAccount;
	}

	public int getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

}