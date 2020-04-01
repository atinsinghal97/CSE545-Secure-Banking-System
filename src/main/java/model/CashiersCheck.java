package model;

	import java.io.Serializable;
	import javax.persistence.*;
	import java.math.BigDecimal;
	import java.util.Date;


	/**
	 * The persistent class for the account database table.
	 * 
	 */
	@Entity
	@Table(name="cashierscheck",schema = "secure_banking_system")
	@NamedQuery(name="CashiersCheck.findAll", query="SELECT a FROM CashiersCheck a")
	public class CashiersCheck implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Integer id;

		@Column(name="from_account_number")
		private String fromAccountNumber;
		
		@Column(name="first_name")
		private String firstName;

		@Column(name="last_name")
		private String lastName;

		@Column(name="middle_name")
		private String middleName;

		@Column(name="transaction_status")
		private String transactionStatus;
		
		@Column(name="deposit_amount")
		private BigDecimal DepositAmount;
		
		

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getFromAccountNumber() {
			return fromAccountNumber;
		}

		public void setFromAccountNumber(String fromAccountNumber) {
			this.fromAccountNumber = fromAccountNumber;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getMiddleName() {
			return middleName;
		}

		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}

		public String getTransactionStatus() {
			return transactionStatus;
		}

		public void setTransactionStatus(String transactionStatus) {
			this.transactionStatus = transactionStatus;
		}

		public BigDecimal getDepositAmount() {
			return DepositAmount;
		}

		public void setDepositAmount(BigDecimal amount) {
			DepositAmount = amount;
		}
		
		
}
