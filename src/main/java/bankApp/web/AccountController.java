package bankApp.web;

import java.util.Date;

import javax.persistence.NoResultException;
import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bankApp.model.Account;
import bankApp.model.User;
import bankApp.session.SessionManager;


@Controller
public class AccountController {
	@RequestMapping(value = "/create_account", method = RequestMethod.POST)
	public Account createAccount(
			@RequestParam(required=true,name="first_name") String first_name,
			@RequestParam(required=true,name="last_name") String last_name,
			@RequestParam(required=true,name="email") String email,
			@RequestParam(required=true,name="accountType") String accountType,
			@RequestParam(required=true,name="dob") Date dob,
			@RequestParam(required=true,name="question1") String question1,
			@RequestParam(required=true,name="question2") String question2
			) {
		
		Authentication x = SecurityContextHolder.getContext().getAuthentication();

		Session s = SessionManager.getSession(x.getName());

		if(accountType=="Savings" || accountType=="Checking" || accountType=="Credit") {
			Account account = new Account();
			account.setAccountType(accountType);
			account.setApprovalStatus(false);
			//account.setUser2();
			return account;
		}
		else {
			return null;
		}
	}
	
	@RequestMapping(value = "/approve_account", method = RequestMethod.POST)
	public void approveAccount(
			@RequestParam(required=true,name="User") User user,
			@RequestParam(required=true,name="accountNumber") String accountNumber
			) throws Exception {
			
			Authentication x = SecurityContextHolder.getContext().getAuthentication();

			Session s = SessionManager.getSession(x.getName());
			Account account = null;
			try {
				account = s.createQuery("FROM Account WHERE accountNumber = :accountNumber", Account.class)
						.setParameter("accountNumber", accountNumber).getSingleResult();
				account.setUser1(user);
				account.setApprovalDate(new Date());
				account.setApprovalStatus(true);
			}
			catch(NoResultException e){
				throw new Exception("No account found");
			}
		}
	@RequestMapping(value = "/decline_account", method = RequestMethod.POST)
	public void declineAccount(
			@RequestParam(required=true,name="User") User user,
			@RequestParam(required=true,name="accountNumber") String accountNumber
			) throws Exception {
			
			Authentication x = SecurityContextHolder.getContext().getAuthentication();

			Session s = SessionManager.getSession(x.getName());
			Account account = null;
			try {
				account = s.createQuery("FROM Account WHERE accountNumber = :accountNumber", Account.class)
						.setParameter("accountNumber", accountNumber).getSingleResult();
				account.setUser1(user);
				account.setApprovalDate(new Date());
				account.setApprovalStatus(false);
			}
			catch(NoResultException e){
				throw new Exception("No account found");
			}
		}
}

