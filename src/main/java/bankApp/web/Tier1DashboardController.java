package bankApp.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import bankApp.model.Tier1;
import bankApp.model.Transaction;
import bankApp.session.SessionManager;

@Controller
public class Tier1DashboardController {
	@Autowired
	@RequestMapping(value = "/tier1_dashboard", method = RequestMethod.POST)
	public ModelAndView Dashboard(HttpServletRequest request, HttpSession session){
		ModelMap model = new ModelMap();
		try {
			Tier1 tier1 = (Tier1) session.getAttribute(null);
			if (tier1 == null)
				return new ModelAndView("redirect:/login");
			else
				return new ModelAndView(("tier1_dashboard"), model);
		}
		catch(Exception e){
			return new ModelAndView("redirect:/login");
		}
	}
	
	@RequestMapping(value = "/pending_transaction", method = RequestMethod.POST)
	public ModelAndView pendingTransaction(HttpServletRequest request, HttpSession session){
		ModelMap model = new ModelMap();
		
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		Session s = SessionManager.getSession(x.getName());
		org.hibernate.Transaction tx = null;
		
		try {
			Tier1 tier1 = (Tier1) session.getAttribute(null);
			if(tier1 == null){
				return new ModelAndView("redirect:/login");
			}
			else{
				tx = s.beginTransaction();
				
				List<Transaction> transactions = (List<Transaction>) s.createQuery("FROM Transactions WHERE approvalStatus = :approvalStatus and level1Approval = :level1Approval ", Transaction.class)
						.setParameter("approvalStatus", false).setParameter("level1Approval",null);
				
				for(Transaction transaction : transactions) {
					if(transaction.getTransactionId()==Integer.parseInt(request.getParameter("transactionId"))) {
						transaction.setApprovalStatus(true);
						//transaction.setUser();
						tx.commit();
					}
					else {
						throw new Exception("Transaction can't be processed");
					}
				}
				return new ModelAndView("pending_transaction");
			}
		}
		catch(Exception ex){
			return new ModelAndView("redirect:/login");
		}
	}
	
	@RequestMapping(value = "/issue_cheque", method = RequestMethod.POST)
	public ModelAndView issueCheque(HttpServletRequest request, HttpSession session){
		ModelMap model = new ModelMap();
		
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		Session s = SessionManager.getSession(x.getName());
		org.hibernate.Transaction tx = null;
		
		try {
			Tier1 tier1 = (Tier1) session.getAttribute(null);
			if(tier1 == null){
				return new ModelAndView("redirect:/login");
			}
			else{
				tx = s.beginTransaction();
				
				Transaction transaction = new Transaction();
				//transaction.setAccount1(account1);
				//transaction.setAmount(amount);
				transaction.setTransactionType("Cashier's Cheque");
				tx.commit();
			}
			return new ModelAndView("tier1_dashboard");
		}
		catch(Exception ex){
			return new ModelAndView("redirect:/login");
		}
	}
	
}
