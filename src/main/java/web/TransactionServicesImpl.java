package web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import constants.Constants;
import database.SessionManager;
import forms.TransactionSearch;
import forms.TransactionSearchForm;
import model.Account;
import model.Transaction;

@Component(value = "transactionServiceImpl")
public class TransactionServicesImpl {

	public TransactionSearchForm getPendingTransactions() {	
		String currentSessionUser = WebSecurityConfig
		  .getCurrentSessionAuthority()
		  .filter(a -> a.equals(Constants.TIER1) || a.equals(Constants.TIER2))
		  .findFirst().orElse(null);

		if (currentSessionUser == null)
		  return null;

		Session session = SessionManager.getSession(currentSessionUser);
		List<Transaction> transactions = null;
		
		try {
			transactions = session
				.createNamedQuery("Transaction.findPendingByCriticality", Transaction.class)
				.setParameter("is_critical_transaction", currentSessionUser.equals(Constants.TIER2))
				.getResultList();
		} catch(NoResultException e) {
			return null;
		}

		TransactionSearchForm transactionSearchForm = new TransactionSearchForm();
		List<TransactionSearch> transactionSearch = transactions.stream()
//            .filter(t -> currentSessionUser.equals(Constants.TIER1) ? t.getAmount().compareTo(Constants.THRESHOLD_AMOUNT) == -1 : true)
              .map(temp -> new TransactionSearch(temp.getId(), temp.getFromAccount(), temp.getToAccount(), temp.getAmount()))
              .collect(Collectors.toList());
		
		transactionSearchForm.setTransactionSearches(transactionSearch);
		return transactionSearchForm;			
	}
	
	public Boolean approveTransactions(Integer transactionId) {	
		String currentSessionUser = WebSecurityConfig
		  .getCurrentSessionAuthority()
		  .filter(a -> a.equals(Constants.TIER1) || a.equals(Constants.TIER2))
		  .findFirst().orElse(null);

		if (currentSessionUser == null)
		  return null;
		
		Session session = SessionManager.getSession(currentSessionUser);
		org.hibernate.Transaction txn = null;
		
		try {
			txn = session.beginTransaction();

			Transaction transaction = session.get(Transaction.class, transactionId);

			Account to = null, from = null;
			if (transaction.getToAccount() != null)
				to = getAccountByNumber(transaction.getToAccount(), session);
			if (transaction.getFromAccount() != null)
				from = getAccountByNumber(transaction.getFromAccount(), session);

			if (applyTransaction(from, to, transaction, currentSessionUser)) {
				if (to != null)
					session.update(to);
				if (from != null)
					session.update(from);
			}

			session.update(transaction);
			if (txn.isActive()) txn.commit();

		} catch (Exception e) {
			if (txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}

		return true;
	}

	public Boolean declineTransactions(Integer transactionId) {
		String currentSessionUser = WebSecurityConfig
		  .getCurrentSessionAuthority()
		  .filter(a -> a.equals(Constants.TIER1) || a.equals(Constants.TIER2))
		  .findFirst().orElse(null);

		if (currentSessionUser == null)
		  return null;

		Session session = SessionManager.getSession(currentSessionUser);
		org.hibernate.Transaction txn = null;
		
		try {

			txn = session.beginTransaction();

			Transaction transaction = session.get(Transaction.class, transactionId);
			transaction.setApprovalStatus(false);

			if (currentSessionUser.equals(Constants.TIER1))
				transaction.setLevel1Approval(false);
			if (currentSessionUser.equals(Constants.TIER2))
				transaction.setLevel2Approval(false);

			transaction.setDecisionDate(new Date());

			session.save(transaction);
			if (txn.isActive()) txn.commit();
			
		} catch (Exception e) {
			if(txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
		
		return true;
	}
	
	public Boolean depositMoney(BigDecimal amount, String accountNumber) {
		String currentSessionUser = WebSecurityConfig
		  .getCurrentSessionAuthority()
		  .filter(a -> a.equals(Constants.TIER1) || a.equals(Constants.TIER2))
		  .findFirst().orElse(null);

		if (currentSessionUser == null)
		  return null;

		Session session = SessionManager.getSession(currentSessionUser);
		org.hibernate.Transaction txn = null;
		
		try {

			txn = session.beginTransaction();
			
			Transaction transaction = createTransaction(null, accountNumber, amount, Constants.CREDIT);
			
			session.save(transaction);
			if (txn.isActive()) txn.commit();
			
			txn = session.beginTransaction();
			
			Account to = getAccountByNumber(accountNumber, session);
			if (applyTransaction(null, to, transaction, currentSessionUser))
				session.update(to);
			session.update(transaction);

			if (txn.isActive()) txn.commit();
			
		} catch (Exception e) {
			if(txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
		
		return true;
	}
	
	public Boolean withdrawMoney(BigDecimal amount, String accountNumber) {
		String currentSessionUser = WebSecurityConfig
		  .getCurrentSessionAuthority()
		  .filter(a -> a.equals(Constants.TIER1) || a.equals(Constants.TIER2))
		  .findFirst().orElse(null);

		if (currentSessionUser == null)
		  return null;
		
		Session session = SessionManager.getSession(currentSessionUser);
		org.hibernate.Transaction txn = null;
		
		try {

			txn = session.beginTransaction();
			
			Transaction transaction = createTransaction(accountNumber, null, amount, Constants.DEBIT);
			
			session.save(transaction);
			if (txn.isActive()) txn.commit();
			
			txn = session.beginTransaction();
			
			Account from = getAccountByNumber(accountNumber, session);
			if (applyTransaction(from, null, transaction, currentSessionUser))
				session.update(from);
			session.update(transaction);
			
			if (txn.isActive()) txn.commit();
			
		} catch (Exception e) {
			if(txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}

		return true;
	}
	
	
	public Boolean createTransaction(BigDecimal amount, String fromAccountNumber, String toAccountNumber) {
		String currentSessionUser = WebSecurityConfig
		  .getCurrentSessionAuthority()
		  .filter(a -> a.equals(Constants.TIER1) || a.equals(Constants.TIER2))
		  .findFirst().orElse(null);

		if (currentSessionUser == null)
		  return null;
		
		Session session = SessionManager.getSession(currentSessionUser);
		org.hibernate.Transaction txn = null;
		
		try {
			
			txn = session.beginTransaction();
			
			Transaction transaction = createTransaction(fromAccountNumber, toAccountNumber, amount, Constants.TRANSFER);
			
			session.save(transaction);
			if (txn.isActive()) txn.commit();
			
			txn = session.beginTransaction();

			Account to   = getAccountByNumber(toAccountNumber, session),
					from = getAccountByNumber(fromAccountNumber, session);
			
			if (applyTransaction(from, to, transaction, currentSessionUser)) {
				session.update(to);
				session.update(from);
			}
			session.update(transaction);
			
			if (txn.isActive()) txn.commit();
			
		} catch (Exception e) {
			if(txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}

		return true;
	}

	public Boolean depositCheque(int chequeId, BigDecimal amount, String accountNumber) {
		String currentSessionUser = WebSecurityConfig
		  .getCurrentSessionAuthority()
		  .filter(a -> a.equals(Constants.TIER1) || a.equals(Constants.TIER2))
		  .findFirst().orElse(null);

		if (currentSessionUser == null)
		  return null;

		Session session = SessionManager.getSession(currentSessionUser);
		org.hibernate.Transaction txn = null;
		
		try {
			txn = session.beginTransaction();
			
			Transaction transaction = session.get(Transaction.class, chequeId);
			
			if (transaction.getAmount().compareTo(amount) != 0)
				throw new Exception("Invalid Cheque Deposit request!");
			
			Transaction depositTransaction = createTransaction(null, accountNumber, transaction.getAmount(), Constants.CHEQUE);
			Account to = getAccountByNumber(accountNumber, session);

			if (applyTransaction(null, to, depositTransaction, currentSessionUser))
				session.update(to);
			session.save(depositTransaction);
			
			if (txn.isActive()) txn.commit();
			
		} catch (Exception e) {
			if(txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
		
		return true;
	}
	
	private Transaction createTransaction(String from, String to, BigDecimal amount, String type) throws Exception {
		if (amount.compareTo(new BigDecimal(0)) <= 0) {
			throw new Exception("Invalid amount for transaction.");
		}
		
		Transaction transaction = new Transaction();
		transaction.setFromAccount(from);
		transaction.setToAccount(to);
		transaction.setAmount(amount);
		transaction.setApprovalStatus(false);
		transaction.setDecisionDate(null);
		transaction.setRequestedDate(new Date());
		transaction.setTransactionType(type);

		if (amount.compareTo(Constants.THRESHOLD_AMOUNT) <= 0) {
			transaction.setIsCriticalTransaction(false);
			transaction.setRequestAssignedTo(Constants.DEFAULT_TIER1);
			transaction.setApprovalLevelRequired(Constants.TIER1);
		} else {
			transaction.setIsCriticalTransaction(true);
			transaction.setRequestAssignedTo(Constants.DEFAULT_TIER2);
			transaction.setApprovalLevelRequired(Constants.TIER2);
		}
		
		return transaction;
	}
	
	private Account getAccountByNumber(String accountNumber, Session session) {
		return session.createQuery("FROM Account WHERE account_number = :number AND status = 1", Account.class)
			.setParameter("number", accountNumber)
			.getSingleResult();
	}
	
	private Boolean applyTransaction(Account from, Account to, Transaction transaction, String currentSessionUser) throws Exception {
		if (from != null && from.getCurrentBalance().compareTo(transaction.getAmount()) == -1) {
			throw new Exception("Not enough funds.");
		}
		
		transaction.setLevel1Approval(currentSessionUser.equals(Constants.TIER1));
		if ((currentSessionUser.equals(Constants.TIER1) && !transaction.getIsCriticalTransaction()) ||
			currentSessionUser.equals(Constants.TIER2)) {

			transaction.setLevel2Approval(currentSessionUser.equals(Constants.TIER2));
			
			if (from != null)
				from.setCurrentBalance(from.getCurrentBalance().subtract(transaction.getAmount()));
			if (to != null) {
				to.setCurrentBalance(to.getCurrentBalance().add(transaction.getAmount()));
			}

			transaction.setDecisionDate(new Date());
			transaction.setApprovalStatus(true);
			
			return true;
		}
		
		return false;
	}
	
	public Boolean issueCheque(BigDecimal amount, String accountNumber) {
		String currentSessionUser = WebSecurityConfig
		  .getCurrentSessionAuthority()
		  .filter(a -> a.equals(Constants.TIER1) || a.equals(Constants.TIER2))
		  .findFirst().orElse(null);

		if (currentSessionUser == null)
		  return null;

		Session session = SessionManager.getSession(currentSessionUser);
		org.hibernate.Transaction txn = null;
		try {
			txn = session.beginTransaction();
			
			Transaction transaction = createTransaction(accountNumber, null, amount, Constants.CHEQUE);
			session.save(transaction);

			if (txn.isActive()) txn.commit();			
			txn = session.beginTransaction();

			Account from = getAccountByNumber(accountNumber, session);
			if (applyTransaction(from, null, transaction, currentSessionUser))
				session.update(from);
			session.update(transaction);

			if (txn.isActive()) txn.commit();
			
		} catch (Exception e) {
			if(txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
		
		return true;
	}
	
	public boolean doesTransactionExists(int transactionId, String transactionType) {
		String currentSessionUser = WebSecurityConfig
		  .getCurrentSessionAuthority()
		  .filter(a -> a.equals(Constants.TIER1) || a.equals(Constants.TIER2))
		  .findFirst().orElse(null);

		if (currentSessionUser == null)
		  return false;

		Session session = SessionManager.getSession(currentSessionUser);
		try {
			session
				.createQuery("FROM Transaction WHERE id = : id and transaction_type = : transaction_type", Transaction.class)
				.setParameter("id", transactionId)
				.setParameter("transaction_type", transactionType)
				.getSingleResult();
		} catch (NoResultException e) {
			return false;
		}

		return true;
	}
	
}
