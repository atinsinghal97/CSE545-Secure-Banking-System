package web;

import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	return new ModelAndView(("accounts/DepositWithdrawal"), model);	
	}
	
	@RequestMapping(value="/accinfo", method = RequestMethod.GET)
	public ModelAndView accinfo(HttpServletRequest request, HttpSession session){
		 session = request.getSession(false);
		ModelMap model = new ModelMap();
		
		return new ModelAndView("redirect:/homepage");
		
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
			 for(Account a:account) {
				 accounts.add(a.getAccountType());
			 }
			 model.addAttribute("accounts",accounts);
		}catch(Exception e) {
			System.out.print(e);
			return new ModelAndView("Login");
		}

		return new ModelAndView(("ServiceRequests/CashiersCheckOrder"), model);
		}
	
	@RequestMapping("/UpdatePasswords")
    public String CustomerUpdatePassword(final HttpServletRequest request) {
		
        return "CustomerUpdatePassword";
  
        
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
}