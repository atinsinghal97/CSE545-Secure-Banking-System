package web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import database.SessionManager;
import forms.EmployeeSearchForm;
import forms.SearchForm;
import forms.TransactionSearchForm;
import forms.UserProfileSearch;
import model.User;

@Controller
public class Tier2DashboardController {
	@Resource(name = "employeeServiceImpl")
	EmployeeServiceImpl employeeServiceImpl;
	
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
		
        return "Tier2DeleteAccount";
  
        
    }
	
	@RequestMapping("/Tier2/UpdateCustomer")
    public String tier2UpdateAccount(final HttpServletRequest request, Model model) {
		
        return "Tier2CustomerUpdate";
  
        
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
	
	@RequestMapping(value = "/Tier2/LockedProfiles", method = RequestMethod.GET)
    public String tier2ProfileSearch(final HttpServletRequest request, Model model) {
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		UserProfileSearch userProfileSearch = userServiceImpl.getLockedUserProfiles("customer");
		model.addAttribute("userSearch", userProfileSearch);

	    HttpSession session = request.getSession(false);
        if (session != null) {
            Object msg = session.getAttribute("msg");
            model.addAttribute("message", session.getAttribute("msg"));
            if (msg != null)
                session.removeAttribute("msg");
        }
		
		return "Tier2SearchProfile";
    }
	
	@RequestMapping(value = "/Tier2/UnlockProfile", method = RequestMethod.POST)
    public ModelAndView tier2UnlockProfile(final HttpServletRequest request,
    		@RequestParam(required = true, name="username") String username) {
		
		Session s = SessionManager.getSession("");
		Transaction txn = null;
		String message = null;
		
		try {
			User u = s.createQuery("FROM User WHERE username = :username AND status = 0", User.class)
				.setParameter("username", username)
				.getSingleResult();

			txn = s.beginTransaction();

			u.setIncorrectAttempts(0);
			u.setStatus(1);
			u.setModifiedDate(new Date());
			
			s.update(u);
			if (txn != null && txn.isActive()) txn.commit();
			message = "Account unlocked.";
		} catch (Exception e) {
			e.printStackTrace();
			if (txn != null && txn.isActive()) txn.rollback();
			message = "Could not unlock account.";
		} finally {
			s.close();
		}

	    HttpSession session = request.getSession(false);
	    if (session != null) {
	    	session.setAttribute("msg", message);
	    }

		return new ModelAndView("redirect:/Tier2/LockedProfiles");
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
	
	@RequestMapping(value = "/Tier2/UpdateSearch", method = RequestMethod.POST)
    public String tier2UpdateSearchPage(@RequestParam(required = true, name="username") String username, Model model) {
		EmployeeSearchForm employeeSearchForm=employeeServiceImpl.getEmployees(username);
		if(employeeSearchForm==null)
			return "Login";
		else
			if(employeeSearchForm.getEmployeeSearchs().size()==0)
			{
				model.addAttribute("message", "An username not found");
				return "Tier2CustomerUpdate";
			}		
			else
				{
				System.out.println("CAME HERE!!!!!!");
				System.out.println(employeeSearchForm.employeeSearchs.get(0).getEmail());
				model.addAttribute("userName", username);
				model.addAttribute("email",employeeSearchForm.employeeSearchs.get(0).getEmail());
				model.addAttribute("firstName",employeeSearchForm.employeeSearchs.get(0).getFirstName());
				model.addAttribute("lastName",employeeSearchForm.employeeSearchs.get(0).getLastName());
				model.addAttribute("middleName",employeeSearchForm.employeeSearchs.get(0).getMiddleName());
				model.addAttribute("phoneNumber",employeeSearchForm.employeeSearchs.get(0).getPhoneNumber());
				return "Tier2CustomerUpdate";
				}
    }
	
	@RequestMapping(value = "/Tier2/UpdateValues", method = RequestMethod.POST)
    public ModelAndView changeValue(
    		@RequestParam(required = true, name="userName") String userName,
    		@RequestParam(required = true, name="email") String email,
    		@RequestParam(required = true, name="firstName") String firstName,
    		@RequestParam(required = true, name="lastName") String lastName,
    		@RequestParam(required = true, name="middleName") String middleName,
    		@RequestParam(required = true, name="phoneNumber") String phoneNumber,
    		final HttpServletRequest request, Model model)  {
		


		Boolean flag=employeeServiceImpl.updateEmployees(userName, email, firstName, lastName, middleName, phoneNumber);
		
		if(flag==null)
			return new ModelAndView("Login");
		else
			if(flag)
				return new ModelAndView("Tier2CustomerUpdate" , "message", "The Info username was updated");
			else
				return new ModelAndView("Tier2CustomerUpdate" , "message", "An username not found");
    }
	
	
	
	
	

}
