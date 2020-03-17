package web;

import java.math.BigDecimal;

import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.Account;
import database.SessionManager;

public class TransactionController {
	@RequestMapping(value = "/debit_transfer", method = RequestMethod.POST)
	public void debitTransfer(
			@RequestParam(required=true,name="fromAccount") String fromAccountNumber,
			@RequestParam(required=true,name="toAccount") String toAccountNumber,
			@RequestParam(required=true,name="amount") BigDecimal amount) throws Exception {
		
		Account fromAccount = null;
		Account toAccount = null;
		
		Authentication x = SecurityContextHolder.getContext().getAuthentication();

		Session s = SessionManager.getSession(x.getName());
		
		try {
			fromAccount = s.createQuery("FROM Account WHERE accountNumber = :fromAccountNumber", Account.class)
					.setParameter("fromAccountNumber", fromAccountNumber).getSingleResult();
			toAccount = s.createQuery("FROM Account WHERE accountNumber = :toAccountNumber", Account.class)
					.setParameter("toAccountNumber", toAccountNumber).getSingleResult();
			
			int res = amount.compareTo(fromAccount.getCurrentBalance());
			
			if(res<0) {
				fromAccount.setCurrentBalance(fromAccount.getCurrentBalance().subtract(amount));
				toAccount.setCurrentBalance(toAccount.getCurrentBalance().add(amount));
			}
			else {
				throw new Exception("Insufficient balance in account");
			}
		}
		catch(Exception e) {
			throw new Exception("Account not found");
		}
	}
	
	
}
