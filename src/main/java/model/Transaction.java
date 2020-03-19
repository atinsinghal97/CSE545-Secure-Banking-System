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

	@Column(name="approval_status")
	private Boolean approvalStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="decision_date")
	private Date decisionDate;

	@Column(name="is_critical_transaction")
	private Boolean isCriticalTransaction;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="requested_date")
	private Date requestedDate;

	@Column(name="transaction_type")
	private String transactionType;

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="from_account")
	private Account account1;

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="to_account")
	private Account account2;

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

	public Boolean getApprovalStatus() {
		return this.approvalStatus;
	}

	public void setApprovalStatus(Boolean approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Date getDecisionDate() {
		return this.decisionDate;
	}

	public void setDecisionDate(Date decisionDate) {
		this.decisionDate = decisionDate;
	}

	public Boolean getIsCriticalTransaction() {
		return this.isCriticalTransaction;
	}

	public void setIsCriticalTransaction(Boolean isCriticalTransaction) {
		this.isCriticalTransaction = isCriticalTransaction;
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

}