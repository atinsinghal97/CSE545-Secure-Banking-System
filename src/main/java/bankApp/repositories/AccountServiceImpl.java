package bankApp.repositories;

import java.util.Date;
import java.util.Optional;

import bankApp.model.Account;
import bankApp.model.User;

public class AccountServiceImpl {
	
	UserRepository userRepo;
	AccountRepository accountRepo;
	
	public Account createAccount(User currentUser, String first_name, String last_name, String email, String accountType, Date dob, String question1, String question2){
				
		if(accountType=="Savings" || accountType=="Checking" || accountType=="Credit") {
			Account account = new Account();
			account.setAccountType(accountType);
			account.setApprovalStatus(false);
			account.setUser2(currentUser);
			return account;
		}
		else {
			return null;
		}
	}
	
	public Account getAccount(String accountNumber) {
		Optional<Account> account = accountRepo.findAccountsByAccountNumber(accountNumber);
		
		if(account!=null)
			return account.get();
		else
			return null;
	}
	
	public void approveAccount(User user, String accountNumber) {
		
		Optional<Account> account = accountRepo.findAccountsByAccountNumber(accountNumber);
		account.get().setApprovalStatus(true);
		account.get().setUser1(user);
		account.get().setApprovalDate(new Date());
		
	}
	
	public void declineAccount(User user, String accountNumber) {
		Optional<Account> account = accountRepo.findAccountsByAccountNumber(accountNumber);
		account.get().setApprovalStatus(false);
		account.get().setUser1(user);
		account.get().setApprovalDate(new Date());
	}
	
}
