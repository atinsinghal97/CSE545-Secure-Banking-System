package forms;

import java.math.BigDecimal;

public class TransactionSearch {

	int id;
	String fromAccountNumber;
	String toAccountNumber;
	BigDecimal amount;
	
	public TransactionSearch(int id, String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
		this.id = id;
		this.fromAccountNumber = fromAccountNumber;
		this.toAccountNumber = toAccountNumber;
		this.amount = amount;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFromAccountNumber() {
		return fromAccountNumber;
	}
	public void setFromAccountNumber(String fromAccountNumber) {
		this.fromAccountNumber = fromAccountNumber;
	}
	public String getToAccountNumber() {
		return toAccountNumber;
	}
	public void setToAccountNumber(String toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
	
}
