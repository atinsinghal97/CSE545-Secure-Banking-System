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

import java.math.BigDecimal;
import java.util.Optional;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import database.SessionManager;
import model.Account;
import model.User;
@Controller
public class PaymentsController {
	AccountServicesImpl accountservicesimpl = new AccountServicesImpl();
	TransactionServicesImpl transactionservicesimpl = new TransactionServicesImpl();
	@RequestMapping(value= {"/payments"}, method = RequestMethod.POST)
	public ModelAndView payments(HttpServletRequest request, HttpSession session){
		session = request.getSession(false);
		ModelMap model = new ModelMap();
			return new ModelAndView(("accounts/Payments"), model);
	}
	
	
	@RequestMapping(value= {"/paymentactionacc"}, method = RequestMethod.POST)
    public ModelAndView paymentactionacc(HttpServletRequest request, HttpSession session) throws Exception {
		 session = request.getSession(false);
		 
		ModelMap model = new ModelMap();
		String deposit = (String)request.getParameter("Deposit");
		String withdraw = (String)request.getParameter("Withdraw");
		String recipientaccnum = (String)request.getParameter("Reciepient Account Number");
		String lastname= (String)request.getParameter("Reciepient Last Name");
		String amo = request.getParameter("Amount").toString();
		BigDecimal amount = new BigDecimal(amo);
		String payeracc = session.getAttribute("SelectedAmount").toString();
		boolean accountExists =false;
		boolean depo=false,with=false, successfulTransactoin=false;
		try {
			Session s = SessionManager.getSession("");
			User user=null;
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", x.getName()).getSingleResult();
			accountExists = user.getAccounts().stream().distinct().anyMatch(f->{
				if(f.getAccountNumber().equals(payeracc) && !f.getAccountType().contentEquals("CreditCard"))
						return true;
				else 
							return false;
				});
			
			if(accountExists&& Integer.parseInt(amo)>0) {
				if("choicemade".equalsIgnoreCase(deposit)) {
					 depo = transactionservicesimpl.depositMoneyCustomer(amount,payeracc);
				}
				else if("choicemade".equalsIgnoreCase(withdraw)) {
					 with = transactionservicesimpl.withdrawMoneyCustomer(amount,payeracc);
				}
				else if(accountservicesimpl.findByAccountNumberAndLastName(recipientaccnum,lastname)) {
					 successfulTransactoin = transactionservicesimpl.trasactionAcc(payeracc,recipientaccnum,amount);
				}
				
			}
		
		}catch(Exception e) {
			return new ModelAndView("Login");
		}
		
		if(depo || with||successfulTransactoin) {
			return new ModelAndView(("redirect:/accinfo"), model);
		}
		else
			throw new Exception("no such emailid or phonenumber"); 
		
		
}
	
	
	
	

	@RequestMapping(value= {"/paymentactionemph"}, method = RequestMethod.POST)
    public ModelAndView paymentactionemph(HttpServletRequest request, HttpSession session) throws Exception {
		ModelMap model = new ModelMap();
		String emailID = (String)request.getParameter("Email Address");
		String phno = (String)request.getParameter("Phone Number");
		String payeracc =session.getAttribute("SelectedAccount").toString();
		double amount = Double.parseDouble(request.getParameter("Amount").toString());
		boolean isPresent = false;
		try {
		Session s = SessionManager.getSession("");
		User user=null;
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		user=s.createQuery("FROM User WHERE username = :username", User.class)
				.setParameter("username", x.getName()).getSingleResult();	
		isPresent = user.getAccounts().stream().distinct().anyMatch(f->{
			if(f.getAccountNumber().equals(payeracc) && !f.getAccountType().contentEquals("CreditCard"))
					return true;
			else 
						return false;
			});
		s.close();
		}catch(Exception e) {
			return new ModelAndView("Login");
		}
		if(amount>0 && (isPresent && (emailID!=null && !"".equals(emailID)) || (phno!=null && !"".equals(phno)))) {
		boolean successfulTransactoin = transactionservicesimpl.trasactionEmPh(payeracc,emailID,phno,amount);
		if(successfulTransactoin)
		model.addAttribute("accountid",session.getAttribute("SelectedAccount"));
		else
			throw new Exception("no such emailid or phonenumber"); 
		}
		else {
			return new ModelAndView("Login");
		}
		return new ModelAndView(("redirect:/accinfo"), model);
		
	}
	
	
	
	@RequestMapping(value= {"/OpenPayments"}, method = RequestMethod.POST)
    public ModelAndView paymentactionCC(HttpServletRequest request, HttpSession session) throws Exception {
		ModelMap model = new ModelMap();
		boolean isPresent = false;
		String account = request.getParameter("accountid");
		try {
		Session s = SessionManager.getSession("");
		User user=null;
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		user=s.createQuery("FROM User WHERE username = :username", User.class)
				.setParameter("username", x.getName()).getSingleResult();	
		isPresent = user.getAccounts().stream().distinct().anyMatch(f->{
			if(f.getAccountNumber().equals(account) && f.getAccountType().contentEquals("CreditCard"))
					return true;
			else 
						return false;
			});
		s.close();
		}catch(Exception e) {
			return new ModelAndView("Login");
		}
		if(isPresent) {
		session.setAttribute("SelectedAccount",Integer.parseInt(account));
		return new ModelAndView("accounts/CreditCardPayments",model);
		}
		request.getSession().setAttribute("message", "no creditcard account");
		return new ModelAndView("redirect:/homepage");
	}
	
	@RequestMapping(value= {"/paymentactionemph"}, method = RequestMethod.POST)
    public ModelAndView paymentactionCCard(HttpServletRequest request, HttpSession session) throws Exception {
		ModelMap model = new ModelMap();
		String amount = request.getParameter("Amount").toString();
		String FromAcc =session.getAttribute("SelectedAccount").toString();
		String ToAcc = request.getParameter("Account");
		Optional<Account> AccExists ;
		boolean transfer =false;
		try {
			Session s = SessionManager.getSession("");
			User user=null;
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", x.getName()).getSingleResult();	
			AccExists = user.getAccounts().stream().distinct().filter(f->{
				if(f.getAccountNumber().equals(FromAcc) && f.getAccountType().equals("CreditCard")
						)return true;
				else return false;
			}).findFirst();
			
			if(AccExists.isPresent() && Integer.parseInt(amount)>0) {
				transfer = transactionservicesimpl.creditcardtransfer(FromAcc,ToAcc,amount);
			}
		}catch(Exception e) {
			return new ModelAndView("Login");
		}
		return null;
	}
	
}