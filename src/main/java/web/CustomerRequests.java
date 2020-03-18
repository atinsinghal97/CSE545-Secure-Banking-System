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
public class CustomerRequests {
	@RequestMapping(value="/ServiceRequest", method=RequestMethod.GET)
	public String ServiceRequest(){

		return "ServiceRequests/ServiceRequests";
	}
	
	@RequestMapping(value="/OrderCCheck", method=RequestMethod.GET)
	public ModelAndView OrderCCheck(HttpServletRequest request, HttpSession session){
		HttpSession session = request.getSession(false);
		ModelMap model = new ModelMap();

		return new ModelAndView(("ServiceRequests/CashiersCheckOrder"), model);
	}
	
	@RequestMapping(value= {"/PrimeAccount"}, method = RequestMethod.GET)
	public ModelAndView PrimeAccount(HttpServletRequest request, HttpSession session){
		HttpSession session = request.getSession(false);
		ModelMap model = new ModelMap();
		return new ModelAndView(("ServiceRequests/PrimaryAccount"), model);
	}
	
	@RequestMapping(value= {"/setprimary"}, method = RequestMethod.POST)
	public ModelAndView SetPrimary(HttpServletRequest request, HttpSession session){
		HttpSession session = request.getSession(false);
		ModelMap model = new ModelMap();
		return new ModelAndView(("redirect:/accinfo"), model);
	}
	
}