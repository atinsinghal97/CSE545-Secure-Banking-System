package web;

import javax.persistence.ParameterMode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.procedure.ProcedureCall;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import database.SessionManager;
import model.User;

@Controller
public class FundsController {
	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public String transfer(
    		@RequestParam(required = true, name="from_account") Integer fromAccount,
    		@RequestParam(required = true, name="to_account") Integer toAccount,
    		@RequestParam(required = true, name="amount") Double amount) {
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		if (x == null || !x.isAuthenticated()) {
			return "";
		}
		
		Session s = SessionManager.getSession(x.getName());

		ProcedureCall call = s.createStoredProcedureCall("create_user_transaction");
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
		
		return null;	
    }
}
