package web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import database.SessionManager;
import model.Transaction;
import model.User;

public class TransactionServicesImpl {

	public List<Transaction> getPendingTransactions() {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("tier1") || grantedAuthority.getAuthority().equals("tier2")) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return null;
			}
		}
		Session session = SessionManager.getSession(currentSessionUser);
		List<Transaction> transactions = (List<Transaction>) session.createQuery("FROM Transactions WHERE approvalStatus = :approvalStatus and decisionDate = :decisionDate ", Transaction.class).setParameter("approvalStatus", false).setParameter("decisionDate", null);
		return transactions;
	}
	
	public void approveTransactions(Integer transactionId) {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("tier1") || grantedAuthority.getAuthority().equals("tier2")) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return;
			}
		}
		Session session = SessionManager.getSession(currentSessionUser);
		Transaction transaction = session.createQuery("FROM Transactions WHERE transactionId = :transactionId and decisionDate = :decisionDate ", Transaction.class).setParameter("transactionId", transactionId).getSingleResult();
		transaction.setApprovalStatus(true);
		transaction.setDecisionDate(new Date());
	}

	public void declineTransactions(Integer transactionId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("tier1") || grantedAuthority.getAuthority().equals("tier2")) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return;
			}
		}
		Session session = SessionManager.getSession(currentSessionUser);
		Transaction transaction = session.createQuery("FROM Transactions WHERE transactionId = :transactionId and decisionDate = :decisionDate ", Transaction.class).setParameter("transactionId", transactionId).getSingleResult();
		transaction.setApprovalStatus(true);
		transaction.setDecisionDate(new Date());
	}
	
	public Transaction depositMoney(BigDecimal amount, String accountNumber) {
		Transaction transaction = new Transaction();
		transaction.setFromAccount("100");	//there needs to be a default bank account
		transaction.setToAccount(accountNumber);
		transaction.setAmount(amount);
		transaction.setApprovalStatus(true);
		transaction.setDecisionDate(new Date());
		transaction.setRequestedDate(new Date());
		transaction.setTransactionType("Deposit");
		if(amount.intValue()<1000)
			transaction.setIsCriticalTransaction(false);
		else
			transaction.setIsCriticalTransaction(true);
		return transaction;
	}
	
	public Transaction withdrawMoney(BigDecimal amount, String accountNumber) {
		Transaction transaction = new Transaction();
		transaction.setFromAccount(accountNumber);	
		transaction.setToAccount("100"); //there needs to be a default bank account
		transaction.setAmount(amount);
		transaction.setApprovalStatus(true);
		transaction.setDecisionDate(new Date());
		transaction.setRequestedDate(new Date());
		transaction.setTransactionType("Withdraw");
		if(amount.intValue()<1000)
			transaction.setIsCriticalTransaction(false);
		else
			transaction.setIsCriticalTransaction(true);
		return transaction;
	}
	
	
	public Transaction createTransaction(BigDecimal amount, String fromAccountNumber, String toAccountNumber) {
		Transaction transaction = new Transaction();
		transaction.setFromAccount(fromAccountNumber);	
		transaction.setToAccount(toAccountNumber);
		transaction.setAmount(amount);
		transaction.setApprovalStatus(true);
		transaction.setDecisionDate(new Date());
		transaction.setRequestedDate(new Date());
		transaction.setTransactionType("Transfer");
		if(amount.intValue()<1000)
			transaction.setIsCriticalTransaction(false);
		else
			transaction.setIsCriticalTransaction(true);
		return transaction;
	}
	
	
	public Transaction depositCheque(BigDecimal amount, String accountNumber) {
		Transaction transaction = new Transaction();
		transaction.setFromAccount("100");	//there needs to be a default bank account
		transaction.setToAccount(accountNumber);
		transaction.setAmount(amount);
		transaction.setApprovalStatus(true);
		transaction.setDecisionDate(new Date());
		transaction.setRequestedDate(new Date());
		transaction.setTransactionType("DepositCheque");
		if(amount.intValue()<1000)
			transaction.setIsCriticalTransaction(false);
		else
			transaction.setIsCriticalTransaction(true);
		return transaction;
	}
	
	public Transaction issueCheque(BigDecimal amount, String accountNumber) {
		Transaction transaction = new Transaction();
		transaction.setFromAccount(accountNumber);	
		transaction.setToAccount("100"); //there needs to be a default bank account
		transaction.setAmount(amount);
		transaction.setApprovalStatus(true);
		transaction.setDecisionDate(new Date());
		transaction.setRequestedDate(new Date());
		transaction.setTransactionType("IssueCheque");
		if(amount.intValue()<1000)
			transaction.setIsCriticalTransaction(false);
		else
			transaction.setIsCriticalTransaction(true);
		return transaction;
	}
	
	

}
