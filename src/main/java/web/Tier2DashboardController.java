package web;

import java.math.BigDecimal;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import forms.SearchForm;
import forms.TransactionSearchForm;

@Controller
public class Tier2DashboardController {
	
	
	@RequestMapping("/Tier2/Dashboard")
    public String tier2Page(final HttpServletRequest request, Model model) {
		
        return "Tier2Dashboard";
        
    }
	
	@RequestMapping("/Tier2PendingTransaction")
    public ModelAndView tier2PendingTransaction(final HttpServletRequest request, Model model) {
		TransactionServicesImpl transactionService = new TransactionServicesImpl();
		TransactionSearchForm transactionSearchForm = transactionService.getPendingTransactions();
		if(transactionSearchForm==null)
			return new ModelAndView("Tier2PendingTransaction","message","No Pending Transactions");

		else {
			return new ModelAndView("Tier2PendingTransaction", "transactionSearchForm", transactionSearchForm);
		}  
        
    }
	@RequestMapping(value = "/Tier2/AuthorizeTransaction", method = RequestMethod.POST)
    public ModelAndView tier2AuthorizeTransaction(@RequestParam(required = true, name="id") int id, @RequestParam(required = true, name="fromAccountNumber") String fromAccountNumber, @RequestParam(required = true, name="toAccountNumber") String toAccountNumber, @RequestParam(required = true, name="id") BigDecimal amount, Model model) throws ParseException {
		
		TransactionServicesImpl transactionServicesImpl = new TransactionServicesImpl();
		
		if(transactionServicesImpl.approveTransactions(id))
			return new ModelAndView("redirect:/Tier2PendingTransaction");  
		
		else
			return new ModelAndView("/Tier2PendingTransaction","message","Transaction doesn't exist");
        
    }
	
	@RequestMapping(value = "/Tier2/DeclineTransaction", method = RequestMethod.POST)
    public ModelAndView tier2DeclineTransaction(@RequestParam(required = true, name="id") int id, @RequestParam(required = true, name="fromAccountNumber") String fromAccountNumber, @RequestParam(required = true, name="toAccountNumber") String toAccountNumber, @RequestParam(required = true, name="id") BigDecimal amount, Model model) throws ParseException {
		
		TransactionServicesImpl transactionServicesImpl = new TransactionServicesImpl();
		
		if(transactionServicesImpl.declineTransactions(id))
			return new ModelAndView("redirect:/Tier2PendingTransaction");  
		
		else
			return new ModelAndView("/Tier2PendingTransaction","message","Transaction doesn't exist");
        
    }
	
	@RequestMapping("/Tier2/UpdatePassword")
    public String tier2UpdatePassword(final HttpServletRequest request, Model model) {
		
        return "Tier2UpdatePassword";
  
        
    }
	
	
	@RequestMapping("/Tier2/PendingAccounts")
    public String tier2PendingAccounts(final HttpServletRequest request, Model model) {
		
        return "redirect:/Tier2/PendingAccountView";
  
        
    }
	
	@RequestMapping("/Tier2/SearchAccount")
    public String tier2SearchAccount(final HttpServletRequest request, Model model) {
		
        return "Tier2SearchAccount";
  
        
    }
	
	
	@RequestMapping("/Tier2/DeleteAccount")
    public String tier2DeleteAccount(final HttpServletRequest request, Model model) {
		
        return "/Tier2DeleteAccount";
  
        
    }
	

	@RequestMapping(value = "/Tier2/DelAcc", method = RequestMethod.POST)
    public ModelAndView deleteAccount(@RequestParam(required = true, name="accountnumber") String accNumber,Model model) throws ParseException {
	
		AccountServicesImpl accountServicesImpl = new AccountServicesImpl();
		
		Boolean flag=accountServicesImpl.deleteAccounts(accNumber);
		
		if(flag==null)
		{
			return new ModelAndView("Login");
		}
		else 
		{
			if(flag)
				return new ModelAndView("Tier2DeleteAccount","message","The account was successfully deleted");
			else
				return new ModelAndView("Tier2DeleteAccount","message","An active account was not found");
		}        
    } 
	
	@RequestMapping("/Tier2/PendingAccountView")
    public ModelAndView retrievePendingAccounts(Model model) {
		
		
		AccountServicesImpl accountServicesImpl = new AccountServicesImpl();
		
		SearchForm searchForm=accountServicesImpl.getAllPendingAccounts();

		if(searchForm==null)
			return new ModelAndView("Login");
		else
		return new ModelAndView("Tier2PendingAccounts" , "searchForm", searchForm);
        
    }
	

	@RequestMapping(value = "/Tier2/AuthAcc", method = RequestMethod.POST)
    public ModelAndView authorizeAccount(@RequestParam(required = true, name="accountnumber") String accNumber,Model model) throws ParseException {
		
		AccountServicesImpl accountServicesImpl = new AccountServicesImpl();
		
		Boolean flag=accountServicesImpl.authorizeAccounts(accNumber);
		if(flag==null)
			return new ModelAndView("Login");
		else
			return new ModelAndView("redirect:/Tier2/PendingAccountView");  
        
    }
	
	@RequestMapping(value = "/Tier2/DecAcc", method = RequestMethod.POST)
    public ModelAndView declineAccount(@RequestParam(required = true, name="accountnumber") String accNumber,Model model) throws ParseException {
		
	AccountServicesImpl accountServicesImpl = new AccountServicesImpl();
		
		Boolean flag=accountServicesImpl.declineAccounts(accNumber);
		if(flag==null)
			return new ModelAndView("Login");
		else
			return new ModelAndView("redirect:/Tier2/PendingAccountView");  
  
        
    }
	@RequestMapping(value = "/Tier2/Search", method = RequestMethod.POST)
    public ModelAndView tier2Page(@RequestParam(required = true, name="accountnumber") String accNumber, Model model) {

		AccountServicesImpl accountServicesImpl = new AccountServicesImpl();
		
		SearchForm searchForm=accountServicesImpl.getAccounts(accNumber);
		if(searchForm==null)
			return new ModelAndView("Login");
		else
			if(searchForm.getSearchs().size()==0)
				return new ModelAndView("Tier2SearchAccount" , "message", "An account not found");
			else
				return new ModelAndView("Tier2SearchAccount" , "searchForm", searchForm);  
    }
	
	
	
	
	

}
