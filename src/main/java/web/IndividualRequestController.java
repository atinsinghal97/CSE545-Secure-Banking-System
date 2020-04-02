package web;

import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import bankApp.repositories.UserDetailsImpl;
import constants.Constants;
import database.SessionManager;
import forms.TransactionSearch;
import forms.TransactionSearchForm;
import model.Account;
import model.Transaction;
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
			Account account = new Account();
			account =user.getAccounts().stream().filter(
					ac->ac.getAccountNumber().equals(request.getParameter("accountid") ) &&
					!ac.getAccountType().equalsIgnoreCase("CrediCard")).findFirst().get();
			
			List<Transaction> transactions  = null;
			TransactionSearchForm transactionSearchForm = new TransactionSearchForm();
			
			try{
				transactions = s.createQuery("FROM Transaction WHERE (from_account = :from_account or to_account = :to_account) and approval_status = :approval_status", Transaction.class)
					.setParameter("from_account", account.getAccountNumber()).setParameter("to_account", account.getAccountNumber()).setParameter("approval_status", true).getResultList();
			
			}catch(NoResultException e){
				e.printStackTrace();
			}
			
			List<TransactionSearch> transactionSearch = transactions.stream()
	              .map(temp -> new TransactionSearch(temp.getId(), temp.getFromAccount(), temp.getToAccount(), temp.getAmount(), temp.getTransactionType()))
	              .collect(Collectors.toList());
			
			transactionSearchForm.setTransactionSearches(transactionSearch);
			model.addAttribute("transactionSearchForm", transactionSearchForm);
			
			model.addAttribute("accountid",request.getParameter("accountid"));
			if(account.getAccountType().equalsIgnoreCase("Savings"))model.addAttribute("accountType","Savings Account");
			if(account.getAccountType().equalsIgnoreCase("Checking"))model.addAttribute("accountType","Checking Account");
			if(account.getAccountType().equalsIgnoreCase("CreditCard")) {
				
				model.addAttribute("balance",10000.0-Integer.parseInt(account.getCurrentBalance().toString()));
			}
			else {
				model.addAttribute("balance", account.getCurrentBalance());
			}
			//model.addAttribute("accountid",request.getParameter("accountid"));
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
			Account account  = user.getAccounts().stream()
			.filter(a->a.getAccountNumber().equals(request.getParameter("accountid")) && a.getStatus()==1).findFirst().get();
			
			model.addAttribute("balance",account.getCurrentBalance());
			model.addAttribute("accountid",account.getAccountNumber());
			session.setAttribute("SelectedAccount", account.getAccountNumber());
			System.out.print("setting session"+session.getAttribute("SelectedAccount"));
			if(account.getAccountType().equals("checking"))model.addAttribute("accType","checking");
			if(account.getAccountType().equals("savings"))model.addAttribute("accType","savings");
			
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
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        TransactionSearchForm transactionSearchForm = transactionservicesimpl.getPendingTransactionsUser(user);
        if (transactionSearchForm != null) {
			model.addAttribute("transactionSearchForm", transactionSearchForm);
        } else {
			model.addAttribute("message", "No pending transactions found.");
        }

        return "PendingTransactions";
	}
	
	@RequestMapping(value = "/AuthorizeTransaction", method = RequestMethod.POST)
    public ModelAndView AuthorizeTransaction(HttpServletRequest request,
    		@RequestParam(required = true, name="id") int transactionId,
    		@RequestParam(required = true, name="fromAccountNumber") String fromAccountNumber,
    		@RequestParam(required = true, name="toAccountNumber") String toAccountNumber,
    		@RequestParam(required = true, name="amount") BigDecimal amount) throws ParseException {		
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
		
		if(transactionservicesimpl.approveDeclineTransactionsUser(user, transactionId, true)) {
			request.getSession().setAttribute("message", "Transaction approved.");
			return new ModelAndView("redirect:/PendingTransactions");  
		}
		
		request.getSession().setAttribute("message", "Transaction can't be approved");
		return new ModelAndView("redirect:/PendingTransactions");
        
    }
	
	@RequestMapping(value = "/DeclineTransaction", method = RequestMethod.POST)
    public ModelAndView DeclineTransaction(HttpServletRequest request,
    		@RequestParam(required = true, name="id") int transactionId,
    		@RequestParam(required = true, name="fromAccountNumber") String fromAccountNumber,
    		@RequestParam(required = true, name="toAccountNumber") String toAccountNumber,
    		@RequestParam(required = true, name="amount") BigDecimal amount) throws ParseException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
		
		if(transactionservicesimpl.approveDeclineTransactionsUser(user, transactionId, false))
			return new ModelAndView("redirect:/PendingTransactions");  

		request.getSession().setAttribute("message", "Transaction can't be declined");
		return new ModelAndView("redirect:/PendingTransactions");
    }
	
	@RequestMapping(value="/CashiersCheck", method=RequestMethod.GET)
	public ModelAndView CashiersCheck(HttpServletRequest request, HttpSession session){
		ModelMap model = new ModelMap();
		
	    if (session != null) {
		    Object msg = session.getAttribute("message");
	        if (msg != null) {
		        model.addAttribute("message", msg);
	        	session.removeAttribute("message");
	        }
	    }
		
		try {
			Session s = SessionManager.getSession("");
			List<User> user=null;
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", x.getName()).getResultList();	
			
			 List<Account> account = user.get(0).getAccounts();
			 List<String> accounts = new ArrayList<>();
			 accounts.add("Lets go!!");
			 for(Account a:account) {
				 accounts.add(a.getAccountNumber());
			 }
			 model.addAttribute("accounts",accounts);
			 s.close();
		}catch(Exception e) {
			System.out.print(e);
			return new ModelAndView("Login");
		}

		return new ModelAndView("ServiceRequests/CashiersCheckOrder", model);
	}

	@RequestMapping(value="/updateAccInfo", method = RequestMethod.POST)
	public ModelAndView transactions(HttpServletRequest request, HttpSession session) {
		 session = request.getSession(false);
		ModelMap model = new ModelMap();
		return new ModelAndView(("MainCustomerPage"), model);
	}
	
	@RequestMapping(value= {"/CCheckOrderAction"}, method = RequestMethod.POST)
	public ModelAndView ccheckOrderAction(HttpServletRequest request, HttpSession session,
			@RequestParam(required = true, name="rec_first_name") String recFirstName,
			@RequestParam(required = false, name="rec_middle_name") String recMiddleName,
			@RequestParam(required = true, name="rec_last_name") String recLastName,
			@RequestParam(required = true, name="from_account") String account,
			@RequestParam(required = true, name="amount") BigDecimal amount){

		Integer checkId = transactionservicesimpl.issueCheque(amount, account);
		if (checkId == 0) {
			request.getSession().setAttribute("message", "Unable to Issue Cashier's Cheque.");
			return new ModelAndView("redirect:/CashiersCheck");
		}

		request.getSession().setAttribute("message", 
				amount.compareTo(Constants.THRESHOLD_AMOUNT) > 0 ?
						"Cashier's Cheque Issue pending approval. Cheque ID: " + checkId :
						"Cashier's Check Issued! Cheque ID: " + checkId);
		return new ModelAndView("redirect:/CashiersCheck");
	}
	
	@RequestMapping(value= {"/ccheckDepositAction"}, method = RequestMethod.POST)
	public ModelAndView ccheckDepositAction(HttpServletRequest request, HttpSession session,
			@RequestParam(required = true, name="check_number") Integer checkNumber,
			@RequestParam(required = false, name="to_account") String toAccount) {
		BigDecimal value = transactionservicesimpl.depositCheque(checkNumber, toAccount);
		if (value == null) {
			request.getSession().setAttribute("message", "Unable to Deposit Cashier's Cheque.");
			return new ModelAndView("redirect:/CashiersCheck");
		}

		request.getSession().setAttribute("message", 
				value.compareTo(Constants.THRESHOLD_AMOUNT) > 0 ?
						"Cashier's Cheque Deposit pending approval." :
						"Cashier's Cheque Deposit complete!");
		return new ModelAndView("redirect:/CashiersCheck");
	}
	
	@RequestMapping(value= {"/OpenAccount"}, method = RequestMethod.POST)
	public ModelAndView openAccountAfterOtp(HttpServletRequest request, HttpSession session){
		Object validOtp = session.getAttribute("OtpValid");
		if (validOtp == null || !(validOtp instanceof Integer)) {
			return new ModelAndView("redirect:/homepage"); 
		}

		ModelMap model = new ModelMap();
		try {
			return new ModelAndView(("NewAccount"), model);
		}catch(Exception e) {
			return new ModelAndView("error");
		}
	}
	
	@RequestMapping(value= {"/opennewaccount"}, method = RequestMethod.POST)
	public ModelAndView openNewAccount(HttpServletRequest request, HttpSession session){
		Object validOtp = session.getAttribute("OtpValid");
		if (validOtp == null || !(validOtp instanceof Integer)) {
			return new ModelAndView("redirect:/homepage"); 
		}
		
		Session s = SessionManager.getSession("");
		org.hibernate.Transaction tx = null;
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
			Random random =new Random();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String accountNumber = user.getId()+Long.toString(timestamp.getTime())+String.valueOf((random.nextInt(90) + 10));
			System.out.println(accountNumber);
			account.setAccountNumber(accountNumber);
			account.setAccountType(accountType);
			account.setApprovalDate(date);
			account.setCreatedDate(date);
			
			if(accountType.equals("savings")) {
			account.setInterest(new BigDecimal(0.0));
			account.setCurrentBalance(new BigDecimal(5.0));
			}
			else if(accountType.equals("checking"))  {
				account.setInterest(new BigDecimal(0.0));
				account.setCurrentBalance(new BigDecimal(5.0));
			}
			else {
				account.setInterest(new BigDecimal(-10.0));
				account.setCurrentBalance(new BigDecimal(10000.0));
			}
			account.setDefaultFlag(0);
			account.setStatus(0);
			account.setUser(user);
			user.addAccount(account);
			s.update(user);
			s.save(account);
			if (tx.isActive())
			    tx.commit();

			session.removeAttribute("OtpValid");
			session.setAttribute("message", "Account approval pending by Bank Employees.");
			return new ModelAndView("redirect:/homepage");
		} catch(Exception e) {
			e.printStackTrace();
			session.removeAttribute("OtpValid");
			return new ModelAndView("error");
		}
	}
	
	@RequestMapping(value= {"/setprimary"}, method= RequestMethod.POST)
	public ModelAndView setAccountAsPrimary(HttpServletRequest request, HttpSession session) {
		try {
			ModelMap model = new ModelMap();
			String account = request.getParameter("Account");
			Session s = SessionManager.getSession("");
			User user=null;
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", x.getName()).getSingleResult();	

			Boolean prime = accountservicesimpl.findAccount(account);
			System.out.print(prime);
			if(prime!=false) {
				if(accountservicesimpl.setPrimaryAccount(account,user)) {
					session.setAttribute("message", "Primary account successfully changed!");
					return new ModelAndView("redirect:/homepage");
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
			User user=null;
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", x.getName()).getSingleResult();	
			
			 List<Account> account = user.getAccounts();
			 List<String> accounts = new ArrayList<>();
			 
			 for(Account a:account) {
				 if (a.getStatus() != 1) continue;
				 
				 if ((a.getDefaultFlag() == null || a.getDefaultFlag() != 1) && !a.getAccountType().equalsIgnoreCase("credit"))
					 accounts.add(a.getAccountNumber());
				 
				 if (a.getDefaultFlag() != null && a.getDefaultFlag() == 1)
					 model.addAttribute("prime_account", a.getAccountNumber());
			 }

			 model.addAttribute("accounts",accounts);
			 return new ModelAndView("ServiceRequests/PrimaryAccount",model);
		}catch(Exception e){
			e.printStackTrace();
			return new ModelAndView("Login");
		}
		
		
	}
}
