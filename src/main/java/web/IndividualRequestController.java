package web;

import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import database.SessionManager;
import model.Account;
import model.User;
@Controller
public class IndividualRequestController {
	
	//for transaction
	@RequestMapping(value="/transactions", method = RequestMethod.POST)
	public ModelAndView transaction(HttpServletRequest request, HttpSession session) {
		 session = request.getSession(false);
		ModelMap model = new ModelMap();
		
		 return new ModelAndView(("accounts/Transactions"), model);
	}
	
	@RequestMapping(value="/ServiceRequest", method=RequestMethod.GET)
	public String ServiceRequest(){

		return "ServiceRequests/ServiceRequests";
	}
	
	@RequestMapping(value= {"/depositwithdrawal"}, method = RequestMethod.POST)
	public ModelAndView depositwithdrawal(HttpServletRequest request, HttpSession session){
		 session = request.getSession(false);
		ModelMap model = new ModelMap();
		try {
			Session s = SessionManager.getSession("");
			List<User> user=null;
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", x.getName()).getResultList();	
			//Account accounts  = user.get(0).getAccounts().stream()
			//		.filter(a->a.getAccountNumber().equals(request.getParameter("accountid"))).findFirst().get();
			Account account = new Account();
			account.setAccountNumber("1111111");
			account.setInterest(new BigDecimal("1.25"));
			account.setAccountType("checkings");
			account.setCurrentBalance(new BigDecimal("10000"));
			model.addAttribute("balance",account.getCurrentBalance());
			model.addAttribute("accountid",account.getAccountNumber());
			session.setAttribute("SelectedAccount", account.getAccountNumber());
			if(account.getAccountType().equals("checking"))model.addAttribute("accType","checking");
			if(account.getAccountType().equals("savings"))model.addAttribute("accType","savings");
			s.close();
			return new ModelAndView(("accounts/DepositWithdrawal"), model);	
	}catch(Exception e) {
		System.out.print(e);
		return new ModelAndView("Login");
	}
		
	}
	
	@RequestMapping(value="/accinfo", method = RequestMethod.GET)
	public ModelAndView accinfo(HttpServletRequest request, HttpSession session){
		ModelMap model = new ModelMap();
		return new ModelAndView(("redirect:/homepage"), model);
		
}
	@RequestMapping(value="/CashiersCheck", method=RequestMethod.GET)
	public ModelAndView CashiersCheck(HttpServletRequest request, HttpSession session){
		ModelMap model = new ModelMap();
		try {
			Session s = SessionManager.getSession("");
			List<User> user=null;
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", x.getName()).getResultList();	
			
			 List<Account> account = user.get(0).getAccounts();
			 List<String> accounts = new ArrayList<>();
			 accounts.add("Lets go nigga!!");
			 for(Account a:account) {
				 accounts.add(a.getAccountType());
			 }
			 model.addAttribute("accounts",accounts);
			 s.close();
		}catch(Exception e) {
			System.out.print(e);
			return new ModelAndView("Login");
		}

		return new ModelAndView(("ServiceRequests/CashiersCheckOrder"), model);
		}

	@RequestMapping(value="/updateAccInfo", method = RequestMethod.POST)
	public ModelAndView transactions(HttpServletRequest request, HttpSession session) {
		 session = request.getSession(false);
		ModelMap model = new ModelMap();
		return new ModelAndView(("MainCustomerPage"), model);
	}
	
	@RequestMapping(value= {"/CCheckOrderAction"}, method = RequestMethod.POST)
	public ModelAndView ccheckOrderAction(HttpServletRequest request, HttpSession session){

		ModelMap model = new ModelMap();
		return new ModelAndView(("redirect:/accinfo"), model);
	}
	
	@RequestMapping(value= {"/ccheckDepositAction"}, method = RequestMethod.POST)
	public ModelAndView ccheckDepositAction(HttpServletRequest request, HttpSession session) {

		ModelMap model = new ModelMap();
		return new ModelAndView(("redirect:/accinfo"), model);
		}
	
	@RequestMapping(value= {"/OpenAccount"}, method = RequestMethod.POST)
	public ModelAndView openAccountAfterOtp(HttpServletRequest request, HttpSession session){

		ModelMap model = new ModelMap();
		try {
			if(null==session.getAttribute("OtpValid")) {		
				return new ModelAndView("redirect:/login");
			}
			session.setAttribute("OtpValid", null);
		return new ModelAndView(("RegistrationExternal"), model);
		}catch(Exception e) {
			return new ModelAndView("Login");
		}
	}
	
	@RequestMapping(value= {"/PrimeAccount"}, method=RequestMethod.GET)
	public ModelAndView setDefaultAccount(HttpServletRequest request, HttpSession session) {
		ModelMap model = new ModelMap();
		try {
			
		}catch(Exception e){
			return new ModelAndView("Login");
		}
		return new ModelAndView("ServiceRequests/PrimaryAccount");
		
	}
}