package web;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import constants.Constants;
import database.SessionManager;
import forms.TransactionSearch;
import forms.TransactionSearchForm;
import model.Account;
import model.Transaction;
import model.User;
import model.UserDetail;

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
			// Tier 1 and Tier 2 can only see transactions approved by customer
			transactions = session
				.createNamedQuery("Transaction.findPendingByCriticality", Transaction.class)
				.setParameter("is_critical_transaction", currentSessionUser.equals(Constants.TIER2))
				.getResultList();
		} catch(NoResultException e) {
			session.close();
			return null;
		}

		TransactionSearchForm transactionSearchForm = new TransactionSearchForm();
		List<TransactionSearch> transactionSearch = transactions.stream()
//            .filter(t -> currentSessionUser.equals(Constants.TIER1) ? t.getAmount().compareTo(Constants.THRESHOLD_AMOUNT) == -1 : true)
              .map(temp -> new TransactionSearch(temp.getId(), temp.getFromAccount(), temp.getToAccount(), temp.getAmount(), temp.getTransactionType()))
              .collect(Collectors.toList());
		
		transactionSearchForm.setTransactionSearches(transactionSearch);
		session.close();

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
			session.close();
			return false;
		} finally {
			session.close();
		}

		return true;
	}

	// Any employee has full power to decline transactions
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
			session.close();
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
			
			Account to = getAccountByNumber(accountNumber, session);

			if (applyTransaction(null, to, transaction, currentSessionUser))
				session.update(to);
			session.save(transaction);

			if (txn.isActive()) txn.commit();
			
		} catch (Exception e) {
			if(txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			session.close();
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
			
			Account from = getAccountByNumber(accountNumber, session);
			if (applyTransaction(from, null, transaction, currentSessionUser))
				session.update(from);
			session.save(transaction);
			
			if (txn.isActive()) txn.commit();
			
		} catch (Exception e) {
			if(txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			session.close();
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

			Account to   = getAccountByNumber(toAccountNumber, session),
					from = getAccountByNumber(fromAccountNumber, session);
			
			if (applyTransaction(from, to, transaction, currentSessionUser)) {
				session.update(to);
				session.update(from);
			}

			session.save(transaction);
			if (txn.isActive()) txn.commit();
			
		} catch (Exception e) {
			if(txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			session.close();
			return false;
		} finally {
			session.close();
		}

		return true;
	}

	public BigDecimal depositCheque(int chequeId, String accountNumber) {
		String currentSessionUser = WebSecurityConfig
		  .getCurrentSessionAuthority()
		  .filter(a -> a.equals(Constants.CUSTOMER))
		  .findFirst().orElse(null);

		if (currentSessionUser == null)
		  return null;

		Session session = SessionManager.getSession(currentSessionUser);
		org.hibernate.Transaction txn = null;
		
		BigDecimal value = null;
		
		try {
			txn = session.beginTransaction();
			
			Transaction transaction = session.get(Transaction.class, chequeId);
			
			if (!transaction.getApprovalStatus())
				throw new Exception("Invalid Cheque Deposit request!");
			
			Transaction depositTransaction = createTransaction(null, accountNumber, transaction.getAmount(), Constants.CHEQUE);
			Account to = getAccountByNumber(accountNumber, session);

			if (applyTransaction(null, to, depositTransaction, currentSessionUser))
				session.update(to);
			session.save(depositTransaction);
			
			if (txn.isActive()) txn.commit();
			
			value = transaction.getAmount();
			
		} catch (Exception e) {
			if(txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			session.close();
			return null;
		} finally {
			session.close();
		}
		
		return value;
	}
	
	public Boolean depositCheque(int chequeId, BigDecimal amount, String accountNumber) {
		String currentSessionUser = WebSecurityConfig
		  .getCurrentSessionAuthority()
		  .filter(a -> a.equals(Constants.TIER1) || a.equals(Constants.TIER2) || a.equals(Constants.CUSTOMER))
		  .findFirst().orElse(null);

		if (currentSessionUser == null)
		  return null;

		Session session = SessionManager.getSession(currentSessionUser);
		org.hibernate.Transaction txn = null;
		
		try {
			txn = session.beginTransaction();
			
			Transaction transaction = session.get(Transaction.class, chequeId);
			
			if ((amount != null && transaction.getAmount().compareTo(amount) != 0) || !transaction.getApprovalStatus())
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
			session.close();
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
		
		if ((type.equals(Constants.CHEQUE) || type.equals(Constants.CREDIT)) && from == null) {
			transaction.setCustomerApproval(1);
		} else {
			transaction.setCustomerApproval(0);
		}

		if (amount.compareTo(Constants.THRESHOLD_AMOUNT) < 0) {
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
	
	private Account getUserAccountByNumber(User user, String accountNumber, Session session) {
		return session.createQuery("FROM Account WHERE account_number = :number AND status = 1 AND user_id = :user_id", Account.class)
			.setParameter("number", accountNumber)
			.setParameter("user_id", user.getId())
			.getSingleResult();
	}

	private Account getAccountByEmailID(String email, Session session) {
		return session.createQuery("FROM Account WHERE default_flag = 1 AND user_id = (SELECT u.userId FROM UserDetail u WHERE email = :email)", Account.class)
				.setParameter("email", email)
				.getSingleResult();
	}

	private Account getAccountByPhoneNumber(String phone, Session session) {
		return session.createQuery("FROM Account WHERE default_flag = 1 AND user_id = (SELECT u.userId FROM UserDetail u WHERE phone = :phone)", Account.class)
				.setParameter("phone", phone)
				.getSingleResult();
	}
	
	private Boolean applyTransaction(Account from, Account to, Transaction transaction, String currentSessionUser) throws Exception {
		if (from != null && from.getCurrentBalance().compareTo(transaction.getAmount()) == -1) {
			throw new Exception("Not enough funds.");
		}
		
		if ((from != null && from.getStatus() != 1) || (to != null && to.getStatus() != 1)) {
			throw new Exception("Account is frozen.");
		}
		
		if (currentSessionUser.equals(Constants.TIER1)) {
			transaction.setLevel1Approval(true);
		} else if (currentSessionUser.equals(Constants.CUSTOMER)) {
			transaction.setCustomerApproval(1);
		} else if (currentSessionUser.equals(Constants.TIER2)) {
			transaction.setLevel2Approval(true);
		}

		System.out.println(transaction);
		
		// Tier 1 can execute a transaction if not critical & has customer approval
		// Tier 2 can execute a transaction if critical & has customer approval
		// Customer can execute a transaction if not critical
		if ((currentSessionUser.equals(Constants.TIER1) && !transaction.getIsCriticalTransaction() && transaction.getCustomerApproval() == 1) ||
			(currentSessionUser.equals(Constants.CUSTOMER) && !transaction.getIsCriticalTransaction()) ||
			(currentSessionUser.equals(Constants.TIER2) && transaction.getIsCriticalTransaction() && transaction.getCustomerApproval() == 1)
		   ) {
			
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
	
	public int issueCheque(BigDecimal amount, String accountNumber) {
		String currentSessionUser = WebSecurityConfig
		  .getCurrentSessionAuthority()
		  .filter(a -> a.equals(Constants.TIER1) || a.equals(Constants.TIER2) || a.equals(Constants.CUSTOMER))
		  .findFirst().orElse(null);

		int count = 0;
		
		if (currentSessionUser == null)
		  return count;

		Session session = SessionManager.getSession(currentSessionUser);
		org.hibernate.Transaction txn = null;
		try {
			txn = session.beginTransaction();
			
			Transaction transaction = createTransaction(accountNumber, null, amount, Constants.CHEQUE);
			session.save(transaction);

			if (txn.isActive()) txn.commit();			
			txn = session.beginTransaction();

			Account from = getAccountByNumber(accountNumber, session);
			if (applyTransaction(from, null, transaction, currentSessionUser)) {
				session.update(from);

			}
			
			session.update(transaction);

			if (txn.isActive()) txn.commit();
			
			count = transaction.getId();
			session.close();
			return count;
			
		} catch (Exception e) {
			if(txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			session.close();
			return count;
		}		
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
			session.close();
			return false;
		}

		session.close();
		return true;
	}

	public boolean trasactionEmPh(String payeracc, String emailID, String phno, double amount) {
		// TODO Auto-generated method stub
		Session s = SessionManager.getSession("");
		org.hibernate.Transaction txn = null;
		try {
			txn = s.beginTransaction();
			Transaction transaction =  new Transaction();
			Account reciever = new Account();
			if(null!=emailID) {
				reciever = getAccountByEmailID(emailID,s);
				if(reciever==null)return false;
				transaction = createTransaction(payeracc,reciever.getAccountNumber(), new BigDecimal(amount), Constants.EMAIL);
				
				}
				else {
					reciever = getAccountByPhoneNumber(phno,s);
					if(reciever==null)return false;
					transaction = createTransaction(payeracc,reciever.getAccountNumber(), new BigDecimal(amount), Constants.PHONE);
				}
			
				s.save(transaction);
				if (txn.isActive()) txn.commit();
				return true;
		} catch (Exception e) {
		if(txn != null && txn.isActive()) txn.rollback();
		e.printStackTrace();
		return false;
		} finally {
		s.close();
		}
		}

	public TransactionSearchForm getPendingTransactionsUser(User user) {
		String currentSessionUser = WebSecurityConfig
		  .getCurrentSessionAuthority()
		  .filter(a -> a.equals(Constants.CUSTOMER))
		  .findFirst().orElse(null);

		if (currentSessionUser == null)
		  return null;

		Session session = SessionManager.getSession(currentSessionUser);
		List<Transaction> transactions = null;
		
		try {
			transactions = session.createQuery("FROM Transaction WHERE from_account IN (SELECT a.accountNumber FROM Account a WHERE user_id = :user_id AND status = 1) AND customerApproval = :customerApproval AND decision_date IS NULL", Transaction.class)
					.setParameter("user_id", user.getId())
					.setParameter("customerApproval", 0).getResultList();
		} catch(NoResultException e) {
			return null;
		}

		session.close();

		TransactionSearchForm transactionSearchForm = new TransactionSearchForm();
		List<TransactionSearch> transactionSearch = transactions.stream()
              .map(temp -> new TransactionSearch(temp.getId(), temp.getFromAccount(), temp.getToAccount(), temp.getAmount(), temp.getTransactionType()))
              .collect(Collectors.toList());
		
		transactionSearchForm.setTransactionSearches(transactionSearch);
		return transactionSearchForm;
	}

	public Boolean approveDeclineTransactionsUser(User user, Integer transactionId, Boolean customerApproval) {
		String currentSessionUser = WebSecurityConfig
		  .getCurrentSessionAuthority()
		  .filter(a -> a.equals(Constants.CUSTOMER))
		  .findFirst().orElse(null);

		if (currentSessionUser == null)
		  return false;
		
		Session s = SessionManager.getSession("");
		org.hibernate.Transaction txn = null;
		
		try {
			txn = s.beginTransaction();

			Transaction transaction = s.createQuery("FROM Transaction WHERE id = :id AND from_account IN (SELECT a.accountNumber FROM Account a WHERE user_id = :user_id) AND decision_date IS NULL", Transaction.class)
					.setParameter("id", transactionId)
					.setParameter("user_id", user.getId())
					.getSingleResult();

			if (customerApproval) {
				
				Account to = null, from = null;
				if (transaction.getToAccount() != null)
					to = getAccountByNumber(transaction.getToAccount(), s);
				if (transaction.getFromAccount() != null)
					from = getAccountByNumber(transaction.getFromAccount(), s);
				
				// If this is not a critical transaction and from account has enough funds
				// The transaction will be executed
				if (applyTransaction(from, to, transaction, currentSessionUser)) {
					s.update(from);
					s.update(to);
				}

			} else {
				transaction.setCustomerApproval(2);
				transaction.setDecisionDate(new Date());
				transaction.setApprovalStatus(customerApproval);
			}
			
			s.update(transaction);
			if (txn.isActive()) txn.commit();

		} catch (Exception e) {
			if (txn != null && txn.isActive()) txn.rollback();
			s.close();
			e.printStackTrace();
			return false;
		} finally {
			s.close();
		}

		return true;
	}
	
	
	public Boolean depositMoneyCustomer(BigDecimal amount, String accountNumber) {
		Session s = SessionManager.getSession("");
		Account account = null;
		org.hibernate.Transaction txn = null;
		try {
			txn = s.beginTransaction();

		account=s.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
				.setParameter("accountNumber", accountNumber).getSingleResult();
		if(account== null)return false;
		
		account.setCurrentBalance(account.getCurrentBalance().add(amount));
		
		s.update(account);
		if (txn.isActive()) txn.commit();
		
		}catch(Exception e) {
			if (txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			return false;
		}finally {
			s.close();
		}
		
		return true;
	}
	
	
	public Boolean withdrawMoneyCustomer(BigDecimal amount, String accountNumber) {
		Session s = SessionManager.getSession("");
		Account account = null;
		org.hibernate.Transaction txn = null;
		try {
			txn = s.beginTransaction();

		account=s.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
				.setParameter("accountNumber", accountNumber).getSingleResult();
		if(account== null || account.getCurrentBalance().compareTo(amount) == -1)return false;
		
		account.setCurrentBalance(account.getCurrentBalance().subtract(amount));
		
		s.update(account);
		if (txn.isActive()) txn.commit();
		
		}catch(Exception e) {
			if (txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			return false;
		}finally {
			s.close();
		}
		
		return true;
	}

	public Boolean transferToAccountByUserDefaults(User user, String fromAccount, String email, String phone, BigDecimal amount) {
		String currentSessionUser = WebSecurityConfig
		  .getCurrentSessionAuthority()
		  .filter(a -> a.equals(Constants.CUSTOMER))
		  .findFirst().orElse(null);

		if (currentSessionUser == null)
		  return false;
		
		Session s = SessionManager.getSession("");
		org.hibernate.Transaction txn = null;
		try {
			txn = s.beginTransaction();
			
			Account from = getUserAccountByNumber(user, fromAccount, s);
			Account to = null;
			if (email != null && !email.trim().isEmpty()) {
				to = getAccountByEmailID(email, s);
			} else if (phone != null && !phone.trim().isEmpty()) {
				to = getAccountByPhoneNumber(phone, s);
			} else {
				throw new Exception("Email or Phone is required to do a default transfer.");
			}

			System.out.println("GOT TO ACCOUNT: " + to.getAccountNumber());
			
			Transaction t = createTransaction(fromAccount, to.getAccountNumber(), amount, Constants.TRANSFER);
			if (applyTransaction(from, to, t, currentSessionUser)) {
				s.update(from);
				s.update(to);
			}
			
			s.save(t);
			if (txn.isActive()) txn.commit();
		} catch (Exception e) {
			if(txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			s.close();
			return false;
		} finally {
			s.close();
		}
		
		return true;
	}
	
	public Boolean transferToAccountByUser(User user, String fromAccount, String toAccount, BigDecimal amount) {
		String currentSessionUser = WebSecurityConfig
		  .getCurrentSessionAuthority()
		  .filter(a -> a.equals(Constants.CUSTOMER))
		  .findFirst().orElse(null);

		if (currentSessionUser == null)
		  return false;
		
		Session s = SessionManager.getSession("");
		org.hibernate.Transaction txn = null;
		try {
			txn = s.beginTransaction();
			
			Account from = getUserAccountByNumber(user, fromAccount, s);
			Account to   = getAccountByNumber(toAccount, s);
			
			Transaction t = createTransaction(fromAccount, toAccount, amount, Constants.TRANSFER);
			
			if (applyTransaction(from, to, t, currentSessionUser)) {
				s.update(from);
				s.update(to);
			}
			
			s.save(t);
			if (txn.isActive()) txn.commit();
		} catch (Exception e) {
			if(txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			s.close();
			return false;
		} finally {
			s.close();
		}
		
		return true;
	}
	
	public boolean trasactionAcc(String payeracc, String recipientaccnum, BigDecimal amount) {
		Session s = SessionManager.getSession("");
		org.hibernate.Transaction txn = null;
		Account account= null;
		account=s.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
				.setParameter("accountNumber", recipientaccnum).getSingleResult();
		try {
			txn = s.beginTransaction();
			if(account==null)return false;
			Transaction transaction = createTransaction(payeracc, recipientaccnum, amount, Constants.ACCOUNT);
			s.save(transaction);
				//create transaction object
				if (txn.isActive()) txn.commit();
				s.close();
				return true;
		} catch (Exception e) {
			if(txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			s.close();
			return false;
		}
	}

	public boolean creditcardtransfer(String fromAcc, String toAcc, String amount) {
		Session s = SessionManager.getSession("");
		org.hibernate.Transaction txn = null;
		try {
			txn = s.beginTransaction();
			Transaction transaction = createTransaction(fromAcc, toAcc, new BigDecimal(amount), Constants.CREDITCARD);
			s.save(transaction);
			if (txn.isActive()) txn.commit();
			s.close();
			return true;
	}catch(Exception e) {
		if(txn != null && txn.isActive()) txn.rollback();
		e.printStackTrace();
		s.close();
		return false;
	}

	}
	
	}
	


