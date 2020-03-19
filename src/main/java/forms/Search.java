package forms;

public class Search {
	public String accountNumber;
	public String balance;
	public Boolean approvalStatus;

	public Search() {
	}

	public Search(String accountNumber,String balance, Boolean approvalStatus) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.approvalStatus = approvalStatus;
	}
	public void print()
	{
		System.out.println(accountNumber);
		System.out.println(balance);
		System.out.println(approvalStatus);
	}
	public String getAccountNumber()
	{
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber=accountNumber;
	}
	public String getBalance()
	{
		return balance;
	}
	
	public void setBalance(String balance)
	{
		this.balance=balance;
	}
	public Boolean getApprovalStatus()
	{
		return approvalStatus;
	}
	
	public void setApprovalStatus(Boolean approvalStatus)
	{
		this.approvalStatus=approvalStatus;
	}
	
	// Getter and Setter methods
}