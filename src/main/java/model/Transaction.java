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
@Table(name="transaction")
@NamedQuery(name="Transaction.findAll", query="SELECT t FROM Transaction t")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private BigDecimal amount;

	@Column(name="approval_level_required")
	private String approvalLevelRequired;

	@Column(name="approval_status")
	private Boolean approvalStatus;

	private Boolean approved;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="decision_date")
	private Date decisionDate;

	@Column(name="from_account")
	private String fromAccount;

	@Column(name="is_critical_transaction")
	private Boolean isCriticalTransaction;

	@Column(name="level_1_approval")
	private Boolean level1Approval;

	@Column(name="level_2_approval")
	private Boolean level2Approval;

	@Column(name="request_assigned_to")
	private int requestAssignedTo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="requested_date")
	private Date requestedDate;

	@Column(name="to_account")
	private String toAccount;

	@Column(name="transaction_type")
	private String transactionType;

	public Transaction() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getApprovalLevelRequired() {
		return this.approvalLevelRequired;
	}

	public void setApprovalLevelRequired(String approvalLevelRequired) {
		this.approvalLevelRequired = approvalLevelRequired;
	}

	public Boolean getApprovalStatus() {
		return this.approvalStatus;
	}

	public void setApprovalStatus(Boolean approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Boolean getApproved() {
		return this.approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Date getDecisionDate() {
		return this.decisionDate;
	}

	public void setDecisionDate(Date decisionDate) {
		this.decisionDate = decisionDate;
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public Boolean getIsCriticalTransaction() {
		return this.isCriticalTransaction;
	}

	public void setIsCriticalTransaction(Boolean isCriticalTransaction) {
		this.isCriticalTransaction = isCriticalTransaction;
	}

	public Boolean getLevel1Approval() {
		return this.level1Approval;
	}

	public void setLevel1Approval(Boolean level1Approval) {
		this.level1Approval = level1Approval;
	}

	public Boolean getLevel2Approval() {
		return this.level2Approval;
	}

	public void setLevel2Approval(Boolean level2Approval) {
		this.level2Approval = level2Approval;
	}

	public int getRequestAssignedTo() {
		return this.requestAssignedTo;
	}

	public void setRequestAssignedTo(int requestAssignedTo) {
		this.requestAssignedTo = requestAssignedTo;
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

}