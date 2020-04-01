package web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import constants.Constants;
import database.SessionManager;
import model.Account;
import model.CashiersCheck;
import model.Transaction;
import model.User;
import model.UserDetail;

public class CashiersCheckServices {

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
	
	public boolean orderCashiersCheck(String firstname, String middlename, String lastname, BigDecimal amount, Account FromAccount) {
		Session s = SessionManager.getSession("");
		org.hibernate.Transaction txn = null;
		try {
			txn = s.beginTransaction();
		CashiersCheck cashierscheck= new CashiersCheck();
		cashierscheck.setFromAccountNumber(FromAccount.getAccountNumber());
		cashierscheck.setFirstName(firstname);
		cashierscheck.setLastName(lastname);
		cashierscheck.setMiddleName(middlename);	
		cashierscheck.setDepositAmount(amount);
		cashierscheck.setTransactionStatus("0");
		s.save(cashierscheck);
				//create transaction o
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
	
	public boolean depositCashiersCheck(String firstname, String middlename, String lastname, String checknumber, Account ToAccount) {
		Session s = SessionManager.getSession("");
		org.hibernate.Transaction txn = null;
		try {
			txn = s.beginTransaction();
			CashiersCheck cc = new CashiersCheck();
			cc =s.createQuery("FROM User WHERE id = :id", CashiersCheck.class)
			.setParameter("id", Integer.parseInt(checknumber)).getSingleResult();
			if(cc==null || !cc.getFirstName().equals(firstname) || !cc.getLastName().equals(lastname) || !cc.getMiddleName().equals(middlename) || cc.getTransactionStatus().equals("1"))return false;
			Transaction transaction = createTransaction(cc.getFromAccountNumber(), ToAccount.getAccountNumber(), cc.getDepositAmount(), Constants.CHEQUE);
			cc.setTransactionStatus("1");
			s.update(cc);
			s.save(transaction);
			if (txn.isActive()) txn.commit();
			return true;
		}catch(Exception e) {
			if(txn != null && txn.isActive()) txn.rollback();
			e.printStackTrace();
			return false;
		}finally {
			s.close();
			}

	}
}
