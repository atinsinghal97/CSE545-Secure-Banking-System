package web;

import javax.persistence.ParameterMode;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import database.SessionManager;
import model.Account;

@Controller
public class FundsController {
	@RequestMapping(value = "/approve_transfer", method = RequestMethod.POST)
    public String approve_transfer(
    		@RequestParam(required = true, name="txn_id") Integer txnId,
    		@RequestParam(required = true, name="approval") Boolean approval) {
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		if (x == null || !x.isAuthenticated()) {
			return "";
		}
		
		boolean isTier1 = false;
		for (GrantedAuthority grantedAuthority : x.getAuthorities()) {
		  System.out.println(grantedAuthority.getAuthority());
		    if (grantedAuthority.getAuthority().equals("tier1")) {
		        isTier1 = true;
		        break;
		    }
		}
		
		Session s = SessionManager.getSession(x.getName());
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			model.Transaction txn = s.get(model.Transaction.class, txnId);
			txn.setApprovalStatus(approval);
			s.update(txn);
			
			if (approval && isTier1 && txn.getLevel2Approval()) {
				// Transfer
				Account from = s.get(Account.class, txn.getFromAccount()),
						to = s.get(Account.class, txn.getToAccount());
				from.setCurrentBalance(from.getCurrentBalance().subtract(txn.getAmount()));
				to.setCurrentBalance(to.getCurrentBalance().add(txn.getAmount()));
			}
			
			if (tx.isActive())
			    tx.commit();
			s.close();
		
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			s.close();
		}
		
		return "";
	}
	
	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public String transfer(
    		@RequestParam(required = true, name="from_account") Integer fromAccount,
    		@RequestParam(required = true, name="to_account") Integer toAccount,
    		@RequestParam(required = true, name="username") String username,
    		@RequestParam(required = true, name="amount") Double amount) {
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		if (x == null || !x.isAuthenticated()) {
			return "";
		}
		
		Session s = SessionManager.getSession(username);
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			ProcedureCall call = s.createStoredProcedureCall("create_user_transaction");
			call.registerParameter("transfer_type", String.class, ParameterMode.IN).bindValue("transfer");
			call.registerParameter("from_username", String.class, ParameterMode.IN).bindValue(username);
			call.registerParameter("from_account", Integer.class, ParameterMode.IN).bindValue(fromAccount);
			call.registerParameter("to_account", Integer.class, ParameterMode.IN).bindValue(toAccount);
			call.registerParameter("amount", Double.class, ParameterMode.IN).bindValue(amount);
			call.registerParameter("status", Integer.class, ParameterMode.OUT);
	
			call.execute();
			Integer status = (Integer) call.getOutputs().getOutputParameterValue("status");
			
			if (status == 0) {
				// Success
			} else if (status == 1) {
				// Critical Transaction
			} else if (status == 2) {
				// Error: not enough funds
			} else if (status == 3) {
				// Error: to_acount doesn't exist
			}
			
			if (tx.isActive())
			    tx.commit();
			s.close();
		
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			s.close();
		}
		
		return null;	
    }
}
