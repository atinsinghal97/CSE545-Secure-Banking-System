package web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TemporalType;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import database.SessionManager;
import forms.Search;
import forms.SearchForm;
import forms.TransactionSearch;
import forms.TransactionSearchForm;
import model.Account;
import model.Transaction;
import model.User;

public class TransactionServicesImpl {

	public TransactionSearchForm getPendingTransactions() {	
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
		
		List<Transaction> transactions = (List<Transaction>) session.createQuery("FROM Transaction WHERE approval_status = :approval_status", Transaction.class).setParameter("approval_status", 0).getResultList();
		
		if(transactions==null)
			return null;
		
		TransactionSearchForm transactionSearchForm = new TransactionSearchForm();
		
		List<TransactionSearch> transactionSearch = new ArrayList<TransactionSearch>();
		
		int amount = Integer.MAX_VALUE;
		
		if(currentSessionUser.equals("tier1"))
			amount = 1000;
		
		for(Transaction temp : transactions)
		{
			System.out.println(temp.getId() + "  " + temp.getDecisionDate() + "  " + temp.getTransactionType());
			if(temp.getTransactionType().equals("transfer") && temp.getDecisionDate()==null && temp.getAmount().intValue()<=amount) {
				TransactionSearch tempSearch = new TransactionSearch(temp.getId(),temp.getFromAccount(),temp.getToAccount(),temp.getAmount());
				transactionSearch.add(tempSearch);
			}
		}
		
		transactionSearchForm.setTransactionSearches(transactionSearch);
		return transactionSearchForm;			
	}
	
	public Boolean approveTransactions(Integer transactionId) {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("tier1") || grantedAuthority.getAuthority().equals("tier2")) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return false;
			}
		}
		Session session = SessionManager.getSession(currentSessionUser);
		Transaction transaction = session.createQuery("FROM Transaction WHERE id = :id", Transaction.class).setParameter("id", transactionId).getSingleResult();
		if(transaction==null)
			return false;
		transaction.setApprovalStatus(true);
		transaction.setDecisionDate(new Date());
		return true;
	}

	public Boolean declineTransactions(Integer transactionId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("tier1") || grantedAuthority.getAuthority().equals("tier2")) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return false;
			}
		}
		Session session = SessionManager.getSession(currentSessionUser);
		Transaction transaction = session.createQuery("FROM Transaction WHERE id = :id", Transaction.class).setParameter("id", transactionId).getSingleResult();
		if(transaction==null)
			return false;
		transaction.setApprovalStatus(false);
		transaction.setDecisionDate(new Date());
		return true;
	}
	
	public Transaction depositMoney(BigDecimal amount, String accountNumber) {
		Transaction transaction = new Transaction();
		transaction.setToAccount(accountNumber);
		transaction.setAmount(amount);
		transaction.setApprovalStatus(true);
		transaction.setDecisionDate(new Date());
		transaction.setRequestedDate(new Date());
		transaction.setTransactionType("credit");
		if(amount.intValue()<=1000)
			transaction.setIsCriticalTransaction(false);
		else
			transaction.setIsCriticalTransaction(true);
		
		if(addMoneyToAccount(accountNumber, amount))
			return transaction;
		
		else
			return null;
	}
	
	public Transaction withdrawMoney(BigDecimal amount, String accountNumber) {
		Transaction transaction = new Transaction();
		transaction.setFromAccount(accountNumber);	
		transaction.setAmount(amount);
		transaction.setApprovalStatus(true);
		transaction.setDecisionDate(new Date());
		transaction.setRequestedDate(new Date());
		transaction.setTransactionType("debit");
		if(amount.intValue()<=1000)
			transaction.setIsCriticalTransaction(false);
		else
			transaction.setIsCriticalTransaction(true);
		
		if(removeMoneyFromAccount(accountNumber, amount))
			return transaction;
		
		else
			return null;
	}
	
	
	public Boolean createTransaction(BigDecimal amount, String fromAccountNumber, String toAccountNumber) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("tier1") || grantedAuthority.getAuthority().equals("tier2")) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return false;
			}
		}
		
		Session session = SessionManager.getSession(currentSessionUser);
		Transaction transaction = new Transaction();
		transaction.setFromAccount(fromAccountNumber);	
		transaction.setToAccount(toAccountNumber);
		transaction.setAmount(amount);
		transaction.setApprovalStatus(false);
		transaction.setDecisionDate(null);
		transaction.setRequestedDate(new Date());
		transaction.setTransactionType("transfer");
		if(amount.intValue()<=1000)
			transaction.setIsCriticalTransaction(false);
		else
			transaction.setIsCriticalTransaction(true);

		return true;
	}
	
	
	public Transaction depositCheque(int chequeId, BigDecimal amount, String accountNumber) {
		Transaction transaction = new Transaction();
		transaction.setToAccount(accountNumber);
		transaction.setAmount(amount);
		transaction.setApprovalStatus(true);
		transaction.setDecisionDate(new Date());
		//can remove critical transaction flag set as will already be set in issue
		if(amount.intValue()<=1000)
			transaction.setIsCriticalTransaction(false);
		else
			transaction.setIsCriticalTransaction(true);
		
		if(addMoneyToAccount(accountNumber, amount))
			return transaction;
		
		else
			return null;
	}
	
	public Transaction issueCheque(BigDecimal amount, String accountNumber) {
		Transaction transaction = new Transaction();
		transaction.setFromAccount(accountNumber);	
		transaction.setAmount(amount);
		transaction.setApprovalStatus(false);
		transaction.setDecisionDate(null);
		transaction.setRequestedDate(new Date());
		transaction.setTransactionType("cc");
		if(amount.intValue()<=1000)
			transaction.setIsCriticalTransaction(false);
		else
			transaction.setIsCriticalTransaction(true);
		
		if(removeMoneyFromAccount(accountNumber, amount))
			return transaction;
		else
			return null;
	}
	
	public boolean doesTransactionExists(int transactionId, String transactionType) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("tier1") || grantedAuthority.getAuthority().equals("tier2")) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return false;
			}
		}
		
		Session session = SessionManager.getSession(currentSessionUser);
		Transaction transaction = null;
		try {
			transaction = session.createQuery("FROM Transaction WHERE id = : id and transaction_type = : transaction_type", Transaction.class).setParameter("id", transactionId).setParameter("transaction_type", transactionType).getSingleResult();
		}catch (NoResultException e){
			return false;
		}
		if(transaction==null)
			return false;
		return true;
	}
	
	
	public boolean addMoneyToAccount(String accountNumber, BigDecimal amount) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("tier1") || grantedAuthority.getAuthority().equals("tier2")) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return false;
			}
		}
		
		Session session = SessionManager.getSession(currentSessionUser);
		Account account= session.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
				.setParameter("accountNumber", accountNumber).getSingleResult();
		
		if(account==null)
			return false;
		
		BigDecimal cuurentBalance = account.getCurrentBalance();
		account.setCurrentBalance(cuurentBalance.add(amount));
		return true;
		
	}
	
	public boolean removeMoneyFromAccount(String accountNumber, BigDecimal amount) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals("tier1") || grantedAuthority.getAuthority().equals("tier2")) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return false;
			}
		}
		
		Session session = SessionManager.getSession(currentSessionUser);
		Account account= session.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
				.setParameter("accountNumber", accountNumber).getSingleResult();
		
		if(account==null)
			return false;
		
		BigDecimal cuurentBalance = account.getCurrentBalance();

		if(cuurentBalance.intValue() < amount.intValue())
			return false;
		
		account.setCurrentBalance(cuurentBalance.subtract(amount));
		return true;
		
	}

}
