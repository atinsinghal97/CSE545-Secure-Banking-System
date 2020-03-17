package web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Tier2Dashboard {
	
	
	@RequestMapping("/Tier2Dashboard")
    public String tier2Page(final HttpServletRequest request, Model model) {
		
        return "Tier2Dashboard";
        
    }
	
	@RequestMapping("/Tier2PendingTransaction")
    public String tier2PendingTransaction(final HttpServletRequest request, Model model) {
		
        return "Tier2PendingTransaction";
  
        
    }
	
	@RequestMapping("/Tier2UpdatePassword")
    public String tier2UpdatePassword(final HttpServletRequest request, Model model) {
		
        return "Tier2UpdatePassword";
  
        
    }
	
	
	@RequestMapping("/Tier2PendingAccounts")
    public String tier2PendingAccounts(final HttpServletRequest request, Model model) {
		
        return "Tier2PendingAccounts";
  
        
    }
	
	@RequestMapping("/Tier2SearchAccount")
    public String tier2SearchAccount(final HttpServletRequest request, Model model) {
		
        return "Tier2SearchAccount";
  
        
    }
	
	
	@RequestMapping("/Tier2DeleteAccount")
    public String tier2DeleteAccount(final HttpServletRequest request, Model model) {
		
        return "/Tier2DeleteAccount";
  
        
    }
	
	
	
	
	
	

}
