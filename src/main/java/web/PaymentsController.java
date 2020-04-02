package web;

import java.math.BigDecimal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bankApp.repositories.UserDetailsImpl;
import database.SessionManager;
import model.Account;
import model.User;


@Controller
public class PaymentsController {
	AccountServicesImpl accountservicesimpl = new AccountServicesImpl();
	TransactionServicesImpl transactionservicesimpl = new TransactionServicesImpl();

	@RequestMapping(value="/payments", method = RequestMethod.POST)
	public ModelAndView payments(HttpServletRequest request, HttpSession session,
    		@RequestParam(required = true, name="accountid") String accountNumber){
		session.setAttribute("accountNumber", accountNumber);
		return new ModelAndView("redirect:/payments_page");
	}

	@RequestMapping(value= {"/payments_page"}, method = RequestMethod.GET)
	public ModelAndView paymentsPage(HttpServletRequest request, HttpSession session){
		Object data = session.getAttribute("accountNumber");
		System.out.println("DATA " + (String) data);
		
		if (data == null || !(data instanceof String)) {
			System.out.println("Moving back, didnt find");
			return new ModelAndView("redirect:/homepage");
		}
		
		String accNumber = (String) data;
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        
        ModelAndView response = new ModelAndView();
		ModelMap model = response.getModelMap();

        if (session != null) {
            Object msg = session.getAttribute("message");
            model.addAttribute("message", session.getAttribute("message"));
            if (msg != null)
                session.removeAttribute("message");
        }

        Session s = SessionManager.getSession("");
        try {
        	
        	Account acc = s.createQuery("FROM Account WHERE account_number = :account_number AND status = 1 AND user_id = :user_id", Account.class)
        		.setParameter("account_number", accNumber)
        		.setParameter("user_id", user.getId())
        		.getSingleResult();
        	
        	model.put("acctype", acc.getAccountType());
        	model.put("accountid", acc.getAccountNumber());
        	model.put("balance", acc.getCurrentBalance());

        	response.setViewName("accounts/Payments");
        } catch (Exception e) {
			System.out.println("Moving back, error");
        	e.printStackTrace();
        	response.getModelMap().clear();
        	response.setViewName("redirect:/homepage");
        } finally {
        	s.close();
        }
		
		return response;
	}
	
	@RequestMapping(value= {"/payment/transfer"}, method = RequestMethod.POST)
    public ModelAndView paymentTransfer(HttpServletRequest request, HttpSession session,
    		@RequestParam(required = true, name="to_acc_number") String toAccountNumber,
    		@RequestParam(required = true, name="rec_last_name") String recipientLastName,
    		@RequestParam(required = true, name="amount") BigDecimal amount) throws Exception {
		Object data = session.getAttribute("accountNumber");
		if (data == null || !(data instanceof String)) {
			return new ModelAndView("redirect:/homepage");
		}
		
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
		
        ModelAndView resp = new ModelAndView("redirect:/payments_page");
		if (transactionservicesimpl.transferToAccountByUser(user, (String) data, toAccountNumber, amount)) {
			session.setAttribute("message", "Transfer successful!");
		} else {
			session.setAttribute("message", "Transfer not successful!");
		}

		return resp;
	}

	@RequestMapping(value= {"/payment/transfer_by_default"}, method = RequestMethod.POST)
    public ModelAndView paymentTransferByEmailOrPhone(HttpServletRequest request, HttpSession session,
    		@RequestParam(required = false, name="email") String email,
    		@RequestParam(required = false, name="phone") String phone,
    		@RequestParam(required = true, name="amount") BigDecimal amount) throws Exception {
		Object data = session.getAttribute("accountNumber");
		if (data == null || !(data instanceof String)) {
			return new ModelAndView("redirect:/homepage");
		}
		
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        ModelAndView resp = new ModelAndView("redirect:/payments_page");
        
        if ((email == null || email.isEmpty()) && (phone == null || phone.isEmpty())) {
			session.setAttribute("message", "Unable to locate the target account!");
			return resp;
        }
        
		if (transactionservicesimpl.transferToAccountByUserDefaults(user, (String) data, email, phone, amount)) {
			session.setAttribute("message", "Transfer successful!");
		} else {
			session.setAttribute("message", "Transfer not successful!");
		}

		return resp;
	}
	
	@RequestMapping(value= {"/paymentactionacc"}, method = RequestMethod.POST)
    public ModelAndView paymentactionacc(HttpServletRequest request, HttpSession session) throws Exception {
		 session = request.getSession(false);
		 
		ModelMap model = new ModelMap();
		String deposit = (String)request.getParameter("Deposit");
		String withdraw = (String)request.getParameter("Withdraw");
		String recipientaccnum = (String)request.getParameter("Reciepient Account Number");
		String lastname= (String)request.getParameter("Reciepient Last Name");
		String amo = request.getParameter("Amount").toString();
		BigDecimal amount = new BigDecimal(amo);
		String payeracc = session.getAttribute("SelectedAccount").toString();
		boolean accountExists =false;
		boolean depo=false,with=false, successfulTransactoin=false;
		try {
			Session s = SessionManager.getSession("");
			User user=null;
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", x.getName()).getSingleResult();
			accountExists = user.getAccounts().stream().distinct().anyMatch(f->{
				if(f.getAccountNumber().equals(payeracc) && !f.getAccountType().contentEquals("credit"))
						return true;
				else 
							return false;
				});
			
			if(accountExists&& Integer.parseInt(amo)>0) {
				if("choicemade".equalsIgnoreCase(deposit)) {
					 depo = transactionservicesimpl.depositMoneyCustomer(amount,payeracc);
				}
				else if("choicemade".equalsIgnoreCase(withdraw)) {
					 with = transactionservicesimpl.withdrawMoneyCustomer(amount,payeracc);
				}
				else if(accountservicesimpl.findByAccountNumberAndLastName(recipientaccnum,lastname)) {
					 successfulTransactoin = transactionservicesimpl.trasactionAcc(payeracc,recipientaccnum,amount);
				}
				
			}
		
		}catch(Exception e) {
			return new ModelAndView("Login");
		}
		
		if(depo || with||successfulTransactoin) {
			return new ModelAndView(("redirect:/accinfo"), model);
		}
		else
			throw new Exception("no such emailid or phonenumber"); 
		
		
}
	
	
	
	

	@RequestMapping(value= {"/paymentactionemph"}, method = RequestMethod.POST)
    public ModelAndView paymentactionemph(HttpServletRequest request, HttpSession session) throws Exception {
		ModelMap model = new ModelMap();
		String emailID = (String)request.getParameter("Email Address");
		String phno = (String)request.getParameter("Phone Number");
		String payeracc =session.getAttribute("SelectedAccount").toString();
		double amount = Double.parseDouble(request.getParameter("Amount").toString());
		boolean isPresent = false;
		try {
		Session s = SessionManager.getSession("");
		User user=null;
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		user=s.createQuery("FROM User WHERE username = :username", User.class)
				.setParameter("username", x.getName()).getSingleResult();	
		isPresent = user.getAccounts().stream().distinct().anyMatch(f->{
			if(f.getAccountNumber().equals(payeracc) && !f.getAccountType().contentEquals("credit"))
					return true;
			else 
						return false;
			});
		s.close();
		}catch(Exception e) {
			return new ModelAndView("Login");
		}
		if(amount>0 && (isPresent && (emailID!=null && !"".equals(emailID)) || (phno!=null && !"".equals(phno)))) {
		boolean successfulTransactoin = transactionservicesimpl.trasactionEmPh(payeracc,emailID,phno,amount);
		if(successfulTransactoin)
		model.addAttribute("accountid",session.getAttribute("SelectedAccount"));
		else
			throw new Exception("no such emailid or phonenumber"); 
		}
		else {
			return new ModelAndView("Login");
		}
		return new ModelAndView(("redirect:/accinfo"), model);
		
	}
	
	
	
	@RequestMapping(value= {"/OpenPayments"}, method = RequestMethod.POST)
    public ModelAndView paymentactionCC(HttpServletRequest request, HttpSession session) throws Exception {
		ModelMap model = new ModelMap();
		boolean isPresent = false;
		String account = request.getParameter("accountid");
		try {
		Session s = SessionManager.getSession("");
		User user=null;
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		user=s.createQuery("FROM User WHERE username = :username", User.class)
				.setParameter("username", x.getName()).getSingleResult();	
		isPresent = user.getAccounts().stream().distinct().anyMatch(f->{
			if(f.getAccountNumber().equals(account) && f.getAccountType().equalsIgnoreCase("credit"))
					return true;
			else 
						return false;
			});
		s.close();
		}catch(Exception e) {
			return new ModelAndView("Login");
		}
		if(isPresent) {
		session.setAttribute("SelectedAccount",account);
		return new ModelAndView("accounts/CreditCardPayments",model);
		}else {
		request.getSession().setAttribute("message", "no creditcard account");
		return new ModelAndView("redirect:/homepage");
		}
	}
	
	@RequestMapping(value= {"/paymentactioncc"}, method = RequestMethod.POST)
    public ModelAndView paymentactionCCard(HttpServletRequest request, HttpSession session) throws Exception {
		ModelMap model = new ModelMap();
		String amount = request.getParameter("Amount").toString();
		String FromAcc =session.getAttribute("SelectedAccount").toString();
		String ToAcc = request.getParameter("Account");
		Optional<Account> AccExists ;
		boolean transfer =false;
		try {
			Session s = SessionManager.getSession("");
			User user=null;
			Authentication x = SecurityContextHolder.getContext().getAuthentication();
			user=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", x.getName()).getSingleResult();	
			AccExists = user.getAccounts().stream().distinct().filter(f->{
				if(f.getAccountNumber().equals(FromAcc) && f.getAccountType().equals("credit")
						)return true;
				else return false;
			}).findFirst();
			Account account = new Account();
			account=s.createQuery("FROM Account WHERE account_number = :accountNumber", Account.class)
					.setParameter("accountNumber", ToAcc).getSingleResult();
			if(AccExists.isPresent() && Integer.parseInt(amount)>0 && account!=null) {
				transfer = transactionservicesimpl.creditcardtransfer(FromAcc,ToAcc,amount);
			}
			if(transfer)return new ModelAndView("redirect:/homepage");
			else return new ModelAndView("error");
		}catch(Exception e) {
			return new ModelAndView("error");
		}
	}
	
}