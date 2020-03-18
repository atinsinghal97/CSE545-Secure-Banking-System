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
public class PaymentsController {
	@RequestMapping(value= {"/payments"}, method = RequestMethod.POST)
	public ModelAndView payments(HttpServletRequest request, HttpSession session){
		HttpSession session = request.getSession(false);
		ModelMap model = new ModelMap();
			return new ModelAndView(("accounts/Payments"), model);
	}
	
	
	@RequestMapping(value= {"/paymentactionacc"}, method = RequestMethod.POST)
    public ModelAndView paymentactionacc(HttpServletRequest request, HttpSession session) throws NumberFormatException, AccountNotFoundException, TransactionFailedException{
		HttpSession session = request.getSession(false);
		ModelMap model = new ModelMap();
		return new ModelAndView(("redirect:/accinfo"), model);
		}
	
	
	@RequestMapping(value= {"/paymentcc"}, method = RequestMethod.POST)
    public ModelAndView paymentcc(HttpServletRequest request, HttpSession session){
		HttpSession session = request.getSession(false);
		ModelMap model = new ModelMap();

		return new ModelAndView(("redirect:/accinfo"), model);
	}
}