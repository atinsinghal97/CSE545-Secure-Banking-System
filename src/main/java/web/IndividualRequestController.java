package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
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
	@RequestMapping(value="/OrderCCheck", method=RequestMethod.GET)
	public ModelAndView OrderCCheck(HttpServletRequest request, HttpSession session){

		ModelMap model = new ModelMap();
		return new ModelAndView(("ServiceRequests/CashiersCheckOrder"), model);
		}
	
	@RequestMapping("/UpdatePasswords")
    public String CustomerUpdatePassword(final HttpServletRequest request) {
		
        return "CustomerPasswordUpdate";
  
        
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
}