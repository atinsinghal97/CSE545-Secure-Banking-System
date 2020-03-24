package web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import constants.Constants;
import database.SessionManager;
import forms.TransactionSearch;
import forms.TransactionSearchForm;
import model.Account;
import model.Transaction;

public class TransactionServicesImpl {

	public TransactionSearchForm getPendingTransactions() {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals(Constants.TIER1) || grantedAuthority.getAuthority().equals(Constants.TIER2)) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return null;
			}
		}
		Session session = SessionManager.getSession(currentSessionUser);
		List<Transaction> transactions = null;
		
		try{
			transactions = (List<Transaction>) session.createQuery("FROM Transaction WHERE approval_status = :approval_status", Transaction.class).setParameter("approval_status", 0).getResultList();
		}catch(NoResultException e){
			return null;
		}
		
		if(transactions==null)
			return null;
		
		TransactionSearchForm transactionSearchForm = new TransactionSearchForm();
		
		List<TransactionSearch> transactionSearch = new ArrayList<TransactionSearch>();
		
		int amount = Integer.MAX_VALUE;
		
		if(currentSessionUser.equals(Constants.TIER1))
			amount = Constants.THRESHOLD_AMOUNT.intValue();
		
		for(Transaction temp : transactions)
		{
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
				if (grantedAuthority.getAuthority().equals(Constants.TIER1) || grantedAuthority.getAuthority().equals(Constants.TIER2)) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return false;
			}
		}
		
		Session session = SessionManager.getSession(currentSessionUser);

		org.hibernate.Transaction txn = null;
		txn = session.beginTransaction();
		
		Transaction transaction = null;
		try{
			transaction = session.createQuery("FROM Transaction WHERE id = :id", Transaction.class).setParameter("id", transactionId).getSingleResult();
		}catch(NoResultException e){
			return false;
		}
		
		if(transaction==null)
			return false;
		
		
		transaction.setDecisionDate(new Date());

		if(!addMoneyToAccount(transaction.getToAccount(), transaction.getAmount()) || !removeMoneyFromAccount(transaction.getFromAccount(), transaction.getAmount())) {
			transaction.setApprovalStatus(false);
			session.save(transaction);
			if (txn.isActive())
			    txn.commit();
			session.close();
			return false;
		}
		
		transaction.setApprovalStatus(true);
		session.save(transaction);

		if (txn.isActive())
		    txn.commit();
		session.close();
		return true;
	}

	public Boolean declineTransactions(Integer transactionId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals(Constants.TIER1) || grantedAuthority.getAuthority().equals(Constants.TIER2)) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return false;
			}
		}
		Session session = SessionManager.getSession(currentSessionUser);
		org.hibernate.Transaction txn = null;
		txn = session.beginTransaction();
		
		Transaction transaction = null;
		try{
			 transaction = session.createQuery("FROM Transaction WHERE id = :id", Transaction.class).setParameter("id", transactionId).getSingleResult();
		}catch(NoResultException e){
			return false;
		}
		
		if(transaction==null)
			return false;
		
		transaction.setApprovalStatus(false);
		transaction.setDecisionDate(new Date());
		session.save(transaction);
		if (txn.isActive())
		    txn.commit();
		session.close();
		return true;
	}
	
	public Boolean depositMoney(BigDecimal amount, String accountNumber) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals(Constants.TIER1) || grantedAuthority.getAuthority().equals(Constants.TIER2)) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return false;
			}
		}
		
		Session session = SessionManager.getSession(currentSessionUser);
		
		org.hibernate.Transaction txn = null;
		txn = session.beginTransaction();
		
		Transaction transaction = new Transaction();
		transaction.setToAccount(accountNumber);
		transaction.setAmount(amount);
		transaction.setApprovalStatus(true);
		transaction.setDecisionDate(new Date());
		transaction.setRequestedDate(new Date());
		transaction.setTransactionType(Constants.CREDIT);
		if(amount.intValue()<=Constants.THRESHOLD_AMOUNT.intValue()) {
			transaction.setIsCriticalTransaction(false);
			transaction.setRequestAssignedTo(Constants.DEFAULT_TIER1);
			transaction.setApprovalLevelRequired(Constants.TIER1);
		}
		else {
			transaction.setIsCriticalTransaction(true);
			transaction.setRequestAssignedTo(Constants.DEFAULT_TIER2);
			transaction.setApprovalLevelRequired(Constants.TIER2);
		}
		if(addMoneyToAccount(accountNumber, amount)) {
			session.save(transaction);
			if (txn.isActive())
			    txn.commit();
			session.close();
			return true;
		}
		else {
			if (txn.isActive())
			    txn.rollback();
			session.close();
			return false;
		}
	}
	
	public Boolean withdrawMoney(BigDecimal amount, String accountNumber) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals(Constants.TIER1) || grantedAuthority.getAuthority().equals(Constants.TIER2)) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return false;
			}
		}
		
		Session session = SessionManager.getSession(currentSessionUser);
		
		org.hibernate.Transaction txn = null;
		txn = session.beginTransaction();
		
		Transaction transaction = new Transaction();
		transaction.setFromAccount(accountNumber);
		transaction.setAmount(amount);
		transaction.setApprovalStatus(true);
		transaction.setDecisionDate(new Date());
		transaction.setRequestedDate(new Date());
		transaction.setTransactionType(Constants.DEBIT);
		if(amount.intValue()<=Constants.THRESHOLD_AMOUNT.intValue()) {
			transaction.setIsCriticalTransaction(false);
			transaction.setRequestAssignedTo(Constants.DEFAULT_TIER1);
			transaction.setApprovalLevelRequired(Constants.TIER1);
		}
		else {
			transaction.setIsCriticalTransaction(true);
			transaction.setRequestAssignedTo(Constants.DEFAULT_TIER2);
			transaction.setApprovalLevelRequired(Constants.TIER2);
		}
		
		if(removeMoneyFromAccount(accountNumber, amount)) {
			session.save(transaction);
			if (txn.isActive())
				txn.commit();
			session.close();
			return true;
		}
		else {
			if (txn.isActive())
				txn.rollback();
			session.close();
			return false;
		}
	}
	
	
	public Boolean createTransaction(BigDecimal amount, String fromAccountNumber, String toAccountNumber) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals(Constants.TIER1) || grantedAuthority.getAuthority().equals(Constants.TIER2)) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return false;
			}
		}
		
		Session session = SessionManager.getSession(currentSessionUser);
		org.hibernate.Transaction txn = null;
		txn = session.beginTransaction();
		Transaction transaction = new Transaction();
		transaction.setFromAccount(fromAccountNumber);	
		transaction.setToAccount(toAccountNumber);
		transaction.setAmount(amount);
		transaction.setDecisionDate(new Date());
		transaction.setRequestedDate(new Date());
		transaction.setTransactionType(Constants.TRANSFER);
		if(amount.intValue()<=Constants.THRESHOLD_AMOUNT.intValue()) {
			transaction.setIsCriticalTransaction(false);
			transaction.setRequestAssignedTo(Constants.DEFAULT_TIER1);
			transaction.setApprovalLevelRequired(Constants.TIER1);
		}
		else {
			transaction.setIsCriticalTransaction(true);
			transaction.setRequestAssignedTo(Constants.DEFAULT_TIER2);
			transaction.setApprovalLevelRequired(Constants.TIER2);
		}
		
		if(!addMoneyToAccount(transaction.getToAccount(), transaction.getAmount()) || !removeMoneyFromAccount(transaction.getFromAccount(), transaction.getAmount())) {
			transaction.setApprovalStatus(false);
			session.save(transaction);
			if (txn.isActive())
			    txn.commit();
			session.close();
			return false;
		}
		
		transaction.setApprovalStatus(true);
		session.save(transaction);

		if (txn.isActive())
		    txn.commit();
		session.close();
		return true;
	}
	
	
	@SuppressWarnings("null")
	public Boolean depositCheque(int chequeId, BigDecimal amount, String accountNumber) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals(Constants.TIER1) || grantedAuthority.getAuthority().equals(Constants.TIER2)) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return false;
			}
		}
		Session session = SessionManager.getSession(currentSessionUser);
		org.hibernate.Transaction txn = null;
		txn = session.beginTransaction();

		Transaction transaction = null;
		try {
			transaction = session.createQuery("FROM Transaction WHERE id = : id", Transaction.class).setParameter("id", chequeId).getSingleResult();
		}catch (NoResultException e){
			return false;
		}
		
		if(transaction.getAmount().intValue()!=amount.intValue())
			return false;
			
		transaction.setToAccount(accountNumber);
		transaction.setApprovalStatus(true);
		transaction.setDecisionDate(new Date());
		
		if(addMoneyToAccount(accountNumber, amount)) {
			session.save(transaction);
			if (txn.isActive())
			    txn.commit();
			session.close();
			return true;
		}
		else {
			if(txn.isActive())
				txn.rollback();
			session.close();
			return false;
		}
	}
	
	public Boolean issueCheque(BigDecimal amount, String accountNumber) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals(Constants.TIER1) || grantedAuthority.getAuthority().equals(Constants.TIER2)) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return false;
			}
		}
		Session session = SessionManager.getSession(currentSessionUser);
		org.hibernate.Transaction txn = null;
		txn = session.beginTransaction();
		Transaction transaction = new Transaction();
		transaction.setFromAccount(accountNumber);
		transaction.setAmount(amount);
		transaction.setApprovalStatus(false);
		transaction.setDecisionDate(null);
		transaction.setRequestedDate(new Date());
		transaction.setTransactionType(Constants.CHEQUE);
		if(amount.intValue()<=Constants.THRESHOLD_AMOUNT.intValue()) {
			transaction.setIsCriticalTransaction(false);
			transaction.setRequestAssignedTo(Constants.DEFAULT_TIER1);
			transaction.setApprovalLevelRequired(Constants.TIER1);
		}
		else {
			transaction.setIsCriticalTransaction(true);
			transaction.setRequestAssignedTo(Constants.DEFAULT_TIER2);
			transaction.setApprovalLevelRequired(Constants.TIER2);
		}
		if(removeMoneyFromAccount(accountNumber, amount)) {
			session.save(transaction);
			if (txn.isActive())
			    txn.commit();
			session.close();
			return true;
		}
		else {
			if(txn.isActive())
				txn.rollback();
			session.close();
			return false;
		}
	}
	
	public boolean doesTransactionExists(int transactionId, String transactionType) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals(Constants.TIER1) || grantedAuthority.getAuthority().equals(Constants.TIER2)) {
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
				if (grantedAuthority.getAuthority().equals(Constants.TIER1) || grantedAuthority.getAuthority().equals(Constants.TIER2)) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return false;
			}
		}
		
		Session session = SessionManager.getSession(currentSessionUser);
		
		org.hibernate.Transaction txn = null;
		txn = session.beginTransaction();
		
		Account account = null;
		try{
			account = session.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class).setParameter("accountNumber", accountNumber).getSingleResult();
		}catch(NoResultException e) {
			return false;
		}
		
		if(account==null)
			return false;
		
		
		BigDecimal cuurentBalance = account.getCurrentBalance();
		account.setCurrentBalance(cuurentBalance.add(amount));
		
		session.save(account);
		if (txn.isActive())
		    txn.commit();
		session.close();
		
		return true;
		
	}
	
	public boolean removeMoneyFromAccount(String accountNumber, BigDecimal amount) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentSessionUser = null;
		
		if(auth!=null || auth.isAuthenticated()) {
			for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
				if (grantedAuthority.getAuthority().equals(Constants.TIER1) || grantedAuthority.getAuthority().equals(Constants.TIER2)) {
					currentSessionUser = grantedAuthority.getAuthority();
				}
			}
			if(currentSessionUser==null) {
				return false;
			}
		}
		
		Session session = SessionManager.getSession(currentSessionUser);
		
		Account account = null;
		try{
			account = session.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class).setParameter("accountNumber", accountNumber).getSingleResult();
		}catch(NoResultException e) {
			return false;
		}
		
		if(account==null)
			return false;
		
		org.hibernate.Transaction txn = null;
		txn = session.beginTransaction();
		
		BigDecimal cuurentBalance = account.getCurrentBalance();

		if(cuurentBalance.intValue() < amount.intValue()) {
			if (txn.isActive())
			    txn.rollback();
			session.close();
			return false;
		
		}
		account.setCurrentBalance(cuurentBalance.subtract(amount));
		session.save(account);
		if (txn.isActive())
		    txn.commit();
		session.close();
		return true;
		
	}
	
}
