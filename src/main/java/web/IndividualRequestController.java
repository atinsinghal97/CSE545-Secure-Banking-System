package web;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import database.SessionManager;
import forms.TransactionSearchForm;
import model.Account;
import model.User;
@Controller
public class IndividualRequestController {
	AccountServicesImpl accountservicesimpl = new AccountServicesImpl();
	TransactionServicesImpl transactionservicesimpl = new TransactionServicesImpl();
	CashiersCheckServices cashierscheckservices = new CashiersCheckServices(); 
	
	private String getMessageFromSession(String attr, HttpSession session) {
		String res = null;
        if (session != null) {
            Object msg = session.getAttribute(attr);
            if (msg != null && msg instanceof String) {
            	res = (String) msg;
                session.removeAttribute(attr);
            }
        }
        return res;
	}
	//for transaction
	@RequestMapping(value="/transactions", method = RequestMethod.POST)
	public ModelAndView transaction(HttpServletRequest request, HttpSession session) {
		ModelMap model = new ModelMap();
		 session = request.getSession(false);
		 Session s = SessionManager.getSession("");
			User user=null;
			try {
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", x.getName()).getSingleResult();
			/*Account account = new Account();
			account =user.getAccounts().stream().filter(
					ac->ac.getAccountNumber().equals(request.getParameter("accountid") ) &&
					!ac.getAccountType().equalsIgnoreCase("CrediCard")).findFirst().get();
			List<Transaction> transactions = new ArrayList<>();
					s.createQuery("FROM Transaction WHERE fromAccount = :fromAccount and approved = true", Transaction.class)
					.setParameter("fromAccount", account.getAccountNumber()).getResultList();
			model.addAttribute("transaction", transactions);
			model.addAttribute("accountid",request.getParameter("accountid"));
			if(account.getAccountType().equalsIgnoreCase("Savings"))model.addAttribute("accountType","Savings Account");
			if(account.getAccountType().equalsIgnoreCase("Checking"))model.addAttribute("accountType","Checking Account");
			if(account.getAccountType().equalsIgnoreCase("CreditCard")) {
				
				model.addAttribute("balance",10000.0-Integer.parseInt(account.getCurrentBalance().toString()));
			}
			else {
				model.addAttribute("balance", account.getCurrentBalance());
			}*/
			model.addAttribute("accountid",request.getParameter("accountid"));
			model.addAttribute("user",user.getUserDetail().getFirstName()+ " "+ user.getUserDetail().getLastName());
		
		s.close();
		 return new ModelAndView(("accounts/Transactions"), model);
	}catch(Exception e) {
		System.out.print(e);
		return new ModelAndView("Login");
	}
	}
	@RequestMapping(value="/ServiceRequest", method=RequestMethod.GET)
	public String ServiceRequest(){

		return "ServiceRequests/ServiceRequests";
	}
	
	@RequestMapping(value= {"/depositwithdrawal"}, method = RequestMethod.POST)
	public ModelAndView depositwithdrawal(HttpServletRequest request, HttpSession session){
		 session = request.getSession(false);
		ModelMap model = new ModelMap();
		try {
			Session s = SessionManager.getSession("");
			User user=null;
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", x.getName()).getSingleResult();	
			//Account accounts  = user.getAccounts().stream()
			//	.filter(a->a.getAccountNumber().equals(request.getParameter("accountid")) && a.getStatus()==1).findFirst().get();
			Account account = new Account();
			account.setAccountNumber("1111111");
			account.setInterest(new BigDecimal("1.25"));
			account.setAccountType("checkings");
			account.setCurrentBalance(new BigDecimal("10000"));
			model.addAttribute("balance",account.getCurrentBalance());
			model.addAttribute("accountid",account.getAccountNumber());
			session.setAttribute("SelectedAccount", account.getAccountNumber());
			if(account.getAccountType().equals("checking"))model.addAttribute("accType","checking");
			if(account.getAccountType().equals("savings"))model.addAttribute("accType","savings");
			s.close();
			return new ModelAndView(("accounts/DepositWithdrawal"), model);	
	}catch(Exception e) {
		System.out.print(e);
		return new ModelAndView("Login");
	}
		
	}
	
	@RequestMapping(value="/accinfo", method = RequestMethod.GET)
	public ModelAndView accinfo(HttpServletRequest request, HttpSession session){
		ModelMap model = new ModelMap();
		return new ModelAndView(("redirect:/homepage"), model);
		
}
	
	
	@RequestMapping(value = "/PendingTransactions",method = { RequestMethod.GET, RequestMethod.POST })
	public String PendingTransactions(HttpServletRequest request, Model model) {
		String message = getMessageFromSession("message", request.getSession());
		try {
		TransactionSearchForm transactionSearchForm =transactionservicesimpl.getPendingTransactionsUser();
		if (null!=transactionSearchForm )
			model.addAttribute("transactionSearchForm", transactionSearchForm);
		else
			model.addAttribute("message", "No pending transactions found.");
		if (message != null)
			model.addAttribute("message", message);

		return "PendingTransactions";}
		catch(Exception e) {
	System.out.println("controller"+e);
		return "Login";
		}
	}
	
	@RequestMapping(value = "/AuthorizeTransaction", method = RequestMethod.POST)
    public ModelAndView AuthorizeTransaction(HttpServletRequest request, @RequestParam(required = true, name="id") int id, @RequestParam(required = true, name="fromAccountNumber") String fromAccountNumber, @RequestParam(required = true, name="toAccountNumber") String toAccountNumber, @RequestParam(required = true, name="id") BigDecimal amount) throws ParseException {		
		if(transactionservicesimpl.approveTransactionsUser(id))
			return new ModelAndView("redirect:/PendingTransactions");  
		
		request.getSession().setAttribute("message", "Transaction can't be approved");
		return new ModelAndView("redirect:/PendingTransactions");
        
    }
	
	@RequestMapping(value = "/DeclineTransaction", method = RequestMethod.POST)
    public ModelAndView DeclineTransaction(HttpServletRequest request, @RequestParam(required = true, name="id") int id, @RequestParam(required = true, name="fromAccountNumber") String fromAccountNumber, @RequestParam(required = true, name="toAccountNumber") String toAccountNumber, @RequestParam(required = true, name="id") BigDecimal amount) throws ParseException {
		if(transactionservicesimpl.declineTransactionsUser(id))
			return new ModelAndView("redirect:/PendingTransactions");  
		
		request.getSession().setAttribute("message", "Transaction doesn't exist");
		return new ModelAndView("redirect:/PendingTransactions");
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
			 accounts.add("Lets go nigga!!");
			 for(Account a:account) {
				 accounts.add(a.getAccountType());
			 }
			 model.addAttribute("accounts",accounts);
			 s.close();
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
		String firstname = (String)request.getParameter("Recipeint First Name");
		String middlename = null;
		if(!"".equals(middlename))middlename =(String)request.getParameter("Recipeint Middle Name");
		String lastname = (String)request.getParameter("Recipeint Last Name");
		String account = (String)request.getParameter("Account");
		String amountinString = (String)request.getParameter("amount");
		BigDecimal amount = new BigDecimal(amountinString);
		Optional<Account> AccountExists;
		boolean ordered =false;
		try {
			Session s = SessionManager.getSession("");
			User user=null;
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", x.getName()).getSingleResult();
			AccountExists = user.getAccounts().parallelStream().distinct().filter(a->
			{if(a.getAccountNumber().equals(account) && a.getAccountType().equalsIgnoreCase("CreditCard"))
				return true;
			else return false;}).findFirst();
			
			
			if(AccountExists.isPresent() && Integer.parseInt(amountinString)>0) {
				ordered =cashierscheckservices.orderCashiersCheck(firstname,middlename,lastname,amount,AccountExists.get());
				
			}
			if(!ordered) {
				request.getSession().setAttribute("message", "cannot do it");
				return new ModelAndView("redirect:ServiceRequests/CashiersCheckOrder");			}
				
		}catch(Exception e) {
			return new ModelAndView("Login");
		}
		
		return new ModelAndView(("redirect:/accinfo"), model);
	}
	
	@RequestMapping(value= {"/ccheckDepositAction"}, method = RequestMethod.POST)
	public ModelAndView ccheckDepositAction(HttpServletRequest request, HttpSession session) {
		ModelMap model = new ModelMap();
		String checknumber = (String)request.getParameter("Cashier's Check Number");
		String accountnumber = (String)request.getParameter("Account");
		Optional<Account> AccountExists;
		boolean deposited =false;
		try {
			Session s = SessionManager.getSession("");
			User user=null;
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", x.getName()).getSingleResult();
			AccountExists = user.getAccounts().parallelStream().distinct().filter(a->
			{if(a.getAccountNumber().equals(accountnumber) && a.getAccountType().equalsIgnoreCase("CreditCard"))
				return true;
			else return false;}).findFirst();
			
			if(AccountExists.isPresent()) {
				deposited = cashierscheckservices.depositCashiersCheck(user.getUserDetail().getFirstName(),user.getUserDetail().getMiddleName(),user.getUserDetail().getLastName(),checknumber,AccountExists.get());
				
			}
			if(!deposited) {
				request.getSession().setAttribute("message", "chequeNumber doesnt exist");
				return new ModelAndView("redirect:ServiceRequests/CashiersCheckOrder");
			}
		}catch(Exception e) {
			return new ModelAndView("Login");
		}
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
		return new ModelAndView(("NewAccount"), model);
		}catch(Exception e) {
			return new ModelAndView("Login");
		}
	}
	
	@RequestMapping(value= {"/opennewaccount"}, method = RequestMethod.POST)
	public ModelAndView openNewAccount(HttpServletRequest request, HttpSession session){
		Session s = SessionManager.getSession("");
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			User user=null;
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", x.getName()).getSingleResult();
			Date date = new Date();
			String accountType = request.getParameter("accountType");
			System.out.print(accountType);
			Account account = new Account();
			String Query = "SELECT floor(random()*99999) as numbers where 'numbers' not  in (select accountNumber from Account )";
			String accountNumber = (String) s.createQuery(Query).getSingleResult();
			System.out.println(accountNumber);
			account.setAccountNumber(accountNumber);
			account.setAccountType(accountType);
			account.setApprovalDate(date);
			account.setCreatedDate(date);
			
			if(accountType.equals("Savings")) {
			account.setInterest(new BigDecimal(0.0));
			account.setCurrentBalance(new BigDecimal(5.0));
			}
			else if(accountType.equals("Checking"))  {
				account.setInterest(new BigDecimal(0.0));
				account.setCurrentBalance(new BigDecimal(5.0));
			}
			else {
				account.setInterest(new BigDecimal(10000.0));
				account.setCurrentBalance(new BigDecimal(-10.0));
			}
			account.setDefaultFlag(0);
			account.setStatus(0);
			account.setUser(user);
			user.addAccount(account);
			s.save(user);
			if (tx.isActive())
			    tx.commit();

			return new ModelAndView("redirect:/homepage");
			
		}catch(Exception e) {
			System.out.print(e);
			return new ModelAndView("Login");
		}
	}
	

	@RequestMapping(value= {"/setprimary"}, method= RequestMethod.POST)
	public ModelAndView setAccountAsPrimary(HttpServletRequest request, HttpSession session) {
		try {
			ModelMap model = new ModelMap();
			Integer account = Integer.parseInt(request.getParameter("Account"));
			Session s = SessionManager.getSession("");
			User user=null;
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", x.getName()).getSingleResult();	
			
			Boolean prime = accountservicesimpl.findAccount(account);
			
			if(null!=prime) {
				if(accountservicesimpl.setPrimaryAccount(account,user)) {
					return new ModelAndView("/homepage",model);
				}
				else {
					throw new Exception();
				}
			}
			s.close();
		}catch(Exception e) {
			return new ModelAndView("Login");
		}
		return new ModelAndView("Login");
	}
	
	@RequestMapping(value= {"/PrimeAccount"}, method=RequestMethod.GET)
	public ModelAndView setDefaultAccount(HttpServletRequest request, HttpSession session) {
		ModelMap model = new ModelMap();
		try {
			Session s = SessionManager.getSession("");
			List<User> user=null;
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", x.getName()).getResultList();	
			
			 List<Account> account = user.get(0).getAccounts();
			 List<Integer> accounts = new ArrayList<>();
			 accounts.add(11111);
			 accounts.add(11112);
			 accounts.add(11113);

			 for(Account a:account) {
				 accounts.add(Integer.parseInt(a.getAccountNumber()));
				 if(a.getDefaultFlag()==1)model.addAttribute("prime_account",Integer.parseInt(a.getAccountNumber()));
			 }
			 model.addAttribute("prime_account",11111);
			 model.addAttribute("accounts",accounts);
			 System.out.print("here we go" + model.getAttribute("prime_account"));
			 return new ModelAndView("ServiceRequests/PrimaryAccount",model);
		}catch(Exception e){
			return new ModelAndView("Login");
		}
		
		
	}
}