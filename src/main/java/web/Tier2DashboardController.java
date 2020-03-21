package web;

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

@Controller
public class Tier2DashboardController {
	
	
	@RequestMapping("/Tier2/Dashboard")
    public String tier2Page(final HttpServletRequest request, Model model) {
		
        return "Tier2Dashboard";
        
    }
	
	@RequestMapping("/Tier2/PendingTransaction")
    public String tier2PendingTransaction(final HttpServletRequest request, Model model) {
		
        return "Tier2PendingTransaction";
  
        
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
