package web;

import java.util.List;

import org.hibernate.Session;

import database.SessionManager;
import model.Transaction;

public class TransactionServicesImpl {

	public List<Transaction> getPendingTransactions() {	

		Session session = SessionManager.getSession("currentSessionUser");
		List<Transaction> transactions = (List<Transaction>) session.createQuery("FROM Transactions WHERE approvalStatus = :approvalStatus and approvalDate = :approvalDate ", Transaction.class).setParameter("approvalStatus", false).setParameter("approvalDate", null);
		return transactions;
	
	}

}
