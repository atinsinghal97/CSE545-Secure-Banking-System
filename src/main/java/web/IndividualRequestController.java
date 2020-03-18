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
@Controller
public class IndividualRequestController {
	
	//for transaction
	@RequestMapping(value="/transactions", method = RequestMethod.POST)
	public ModelAndView transactions(HttpServletRequest request, HttpSession session) {
		HttpSession session = request.getSession(false);
		ModelMap model = new ModelMap();
		
		 return new ModelAndView(("accounts/Transactions"), model);
	}
	
	
	@RequestMapping(value= {"/depositwithdrawal"}, method = RequestMethod.POST)
	public ModelAndView depositwithdrawal(HttpServletRequest request, HttpSession session){
		HttpSession session = request.getSession(false);
		ModelMap model = new ModelMap();
	return new ModelAndView(("accounts/DepositWithdrawal"), model);	
	}
	
	@RequestMapping(value="/accinfo", method = RequestMethod.GET)
	public ModelAndView accinfo(HttpServletRequest request, HttpSession session){
		HttpSession session = request.getSession(false);
		ModelMap model = new ModelMap();
		
		return new ModelAndView(("MainCustomerPage"), model);
		
}
}