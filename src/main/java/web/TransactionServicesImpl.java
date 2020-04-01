package web;

import java.math.BigDecimal;
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
	
	private Transaction createTransaction(String from, String to, BigDecimal amount, String type) {
		Transaction transaction = new Transaction();
		transaction.setFromAccount(from);
		transaction.setToAccount(to);
		transaction.setAmount(amount);
		transaction.setApprovalStatus(false);
		transaction.setDecisionDate(null);
		transaction.setRequestedDate(new Date());
		transaction.setTransactionType(type);
		transaction.setCustomerApproval(0);

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
	
	private Account getAccountByPhoneNumber(String Phno, Session session) {
		
		UserDetail ud =  session.createQuery("FROM UserDetail WHERE phone = :phone", UserDetail.class)
				.setParameter("phone", Phno).getSingleResult();	
		
		List<Account> accounts =ud.getUser().getAccounts();
		for(Account a:accounts) {
			if(a.getDefaultFlag()==1) return a;
		}
		return null;
		
	}
	
	private Account getAccountByEmailID(String email, Session session) {
		
		UserDetail ud =  session.createQuery("FROM UserDetail WHERE email = :email", UserDetail.class)
				.setParameter("email", email).getSingleResult();	
		
		List<Account> accounts =ud.getUser().getAccounts();
		for(Account a:accounts) {
			if(a.getDefaultFlag()==1) return a;
		}
		return null;
		
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

	public TransactionSearchForm getPendingTransactionsUser() {
		Session s = SessionManager.getSession("");
				User user = null;
				Authentication x = SecurityContextHolder.getContext().getAuthentication();
				user=s.createQuery("FROM User WHERE username = :username", User.class)
						.setParameter("username", x.getName()).getSingleResult();
				List<Transaction> transactions = null;
				List<Account> account = user.getAccounts();
				List<String> accountnumber = new ArrayList<String>();
				TransactionSearchForm transactionSearchForm = new TransactionSearchForm();
				for(Account a:account) {
					if(a.getStatus()==1)accountnumber.add(a.getAccountNumber());
				}
				try {
					transactions = s.createQuery("FROM Transaction WHERE fromAccount = : fromAccount AND customerApproval = : customerApproval", Transaction.class)
							.setParameter("fromAccount", accountnumber).setParameter("customerApproval", 0).getResultList();
					List<TransactionSearch> transactionSearch = transactions.stream()
//			            .filter(t -> currentSessionUser.equals(Constants.TIER1) ? t.getAmount().compareTo(Constants.THRESHOLD_AMOUNT) == -1 : true)
			              .map(temp -> new TransactionSearch(temp.getId(), temp.getFromAccount(), temp.getToAccount(), temp.getAmount()))
			              .collect(Collectors.toList());
					
					transactionSearchForm.setTransactionSearches(transactionSearch);
				} catch(NoResultException e) {
					System.out.print("exception in fucntion"+e);
					return null;
				}

				
				return transactionSearchForm;
	}

	public boolean approveTransactionsUser(Integer transactionId) {
		Session s = SessionManager.getSession("");
				org.hibernate.Transaction txn = null;
				
				try {
					txn = s.beginTransaction();

					Transaction transaction = s.get(Transaction.class, transactionId);

					transaction.setCustomerApproval(1);
					s.update(transaction);
					if (txn.isActive()) txn.commit();

				} catch (Exception e) {
					if (txn != null && txn.isActive()) txn.rollback();
					e.printStackTrace();
					return false;
				} finally {
					s.close();
				}

				return true;
	}

	public boolean declineTransactionsUser(int transactionId) {
		Session s = SessionManager.getSession("");
				org.hibernate.Transaction txn = null;
				
				try {
					txn = s.beginTransaction();

					Transaction transaction = s.get(Transaction.class, transactionId);

					transaction.setCustomerApproval(2);
					s.update(transaction);
					if (txn.isActive()) txn.commit();

				} catch (Exception e) {
					if (txn != null && txn.isActive()) txn.rollback();
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
				return true;
		} catch (Exception e) {
		if(txn != null && txn.isActive()) txn.rollback();
		e.printStackTrace();
		return false;
		} finally {
		s.close();
		}
	}

	
	
	}
	


