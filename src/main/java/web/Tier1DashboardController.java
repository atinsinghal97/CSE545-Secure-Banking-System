package web;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import web.AccountServicesImpl;
import web.TransactionServicesImpl;
import constants.Constants;
import forms.SearchForm;
import forms.TransactionSearchForm;

@Controller
public class Tier1DashboardController {
	@Resource(name = "transactionServiceImpl")
	private TransactionServicesImpl transactionServiceImpl;
	
	@RequestMapping(value = "/Tier1Dashboard")
	public String dashboard(final HttpServletRequest request) {
		return "Tier1Dashboard";
		
	}
	
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
	
	@RequestMapping(value = "/Tier1PendingTransactions")
	public String tier1PendingTransactions(HttpServletRequest request, Model model) {
		String message = getMessageFromSession("message", request.getSession());

		TransactionSearchForm transactionSearchForm = transactionServiceImpl.getPendingTransactions();
		if (transactionSearchForm == null)
			model.addAttribute("message", "No pending transactions found.");
		else
			model.addAttribute("transactionSearchForm", transactionSearchForm);

		if (message != null)
			model.addAttribute("message", message);

		return "Tier1PendingTransactions";
	}
	
	@RequestMapping(value = "/Tier1/AuthorizeTransaction", method = RequestMethod.POST)
    public ModelAndView tier1AuthorizeTransaction(HttpServletRequest request, @RequestParam(required = true, name="id") int id, @RequestParam(required = true, name="fromAccountNumber") String fromAccountNumber, @RequestParam(required = true, name="toAccountNumber") String toAccountNumber, @RequestParam(required = true, name="amount") BigDecimal amount, @RequestParam(required = true, name="transferType") String transferType) throws ParseException {		
		if(transactionServiceImpl.approveTransactions(id))
			return new ModelAndView("redirect:/Tier1PendingTransactions");  
		
		request.getSession().setAttribute("message", "Transaction can't be approved");
		return new ModelAndView("redirect:/Tier1PendingTransactions");
        
    }
	
	@RequestMapping(value = "/Tier1/DeclineTransaction", method = RequestMethod.POST)
    public ModelAndView tier1DeclineTransaction(HttpServletRequest request, @RequestParam(required = true, name="id") int id, @RequestParam(required = true, name="fromAccountNumber") String fromAccountNumber, @RequestParam(required = true, name="toAccountNumber") String toAccountNumber, @RequestParam(required = true, name="amount") BigDecimal amount, @RequestParam(required = true, name="transferType") String transferType) throws ParseException {
		if(transactionServiceImpl.declineTransactions(id))
			return new ModelAndView("redirect:/Tier1PendingTransactions");  
		
		request.getSession().setAttribute("message", "Transaction doesn't exist");
		return new ModelAndView("redirect:/Tier1PendingTransactions");
    }
	
	@RequestMapping(value = "/Tier1IssueCheque")
	public String issueCheque(HttpServletRequest request, Model model) {
		String message = getMessageFromSession("message", request.getSession());
		if (message != null)
			model.addAttribute("message", message);

		return "Tier1IssueCheque";
	}
	
	@RequestMapping(value = "/Tier1/IssueCheque")
	public ModelAndView tier1IssueCheque(HttpServletRequest request, @RequestParam(required = true, name="accountNumber") String accountNumber, @RequestParam(required = true, name="amount") BigDecimal amount){
		AccountServicesImpl accountServicesImpl = new AccountServicesImpl();
		String message = null;
		
		if (!accountServicesImpl.doesAccountExists(accountNumber)) {
			message = "Account doesn't exist";
		}
		
		else {
			int count = transactionServiceImpl.issueCheque(amount, accountNumber);
	
			if (count > 0) {
				if(amount.intValue() < Constants.THRESHOLD_AMOUNT.intValue())
					message = "The Cheque was issued successfully. Cheque Id = " + String.valueOf(count);
				else
					message = "The Cheque pending approval. Cheque Id = " + String.valueOf(count);
			}else
				message = "The Cheque was not issued";	
		}
		if (message != null)
			request.getSession().setAttribute("message", message);

		return new ModelAndView("redirect:/Tier1IssueCheque");
	}
	
	@RequestMapping(value = "/Tier1DepositCheque")
	public String depositCheque(HttpServletRequest request, Model model) {
		String message = getMessageFromSession("message", request.getSession());
		if (message != null)
			model.addAttribute("message", message);

		return "Tier1DepositCheque";
	}
	
	
	@RequestMapping(value = "/Tier1/DepositCheque")
	public ModelAndView tier1DepositCheque(HttpServletRequest request, @RequestParam(required = true, name="chequeId") int chequeId, @RequestParam(required = true, name="accountNumber") String accountNumber, @RequestParam(required = true, name="amount") BigDecimal amount) {
		AccountServicesImpl accountServicesImpl = new AccountServicesImpl();
		String message = null;

		if (!transactionServiceImpl.doesTransactionExists(chequeId, "cc"))
			message = "Cheque doesn't exist";
		
		else if (!accountServicesImpl.doesAccountExists(accountNumber))
			message = "Account doesn't exist";

		else if (transactionServiceImpl.depositCheque(chequeId, amount, accountNumber))
			if(amount.intValue() < Constants.THRESHOLD_AMOUNT.intValue())
				message = "The Cheque was deposited successfully";
			else
				message = "The Cheque pending approval";
		else
			message = "The Cheque was not deposited due to incorrect information";

		if (message != null)
			request.getSession().setAttribute("message", message);

		return new ModelAndView("redirect:/Tier1DepositCheque");
	}
	
	@RequestMapping(value = "/Tier1DepositMoney")
	public String depositAmount(HttpServletRequest request, Model model) {
		String message = getMessageFromSession("message", request.getSession());
		if (message != null)
			model.addAttribute("message", message);

		return "Tier1DepositMoney";
	}
	
	
	@RequestMapping(value = "/Tier1/DepositMoney")
	public ModelAndView tier1DepositMoney(HttpServletRequest request,@RequestParam(required = true, name="accountNumber") String accountNumber, @RequestParam(required = true, name="amount") BigDecimal amount) {
		AccountServicesImpl accountServicesImpl = new AccountServicesImpl();
		String message = null;
		
		
		if(!accountServicesImpl.doesAccountExists(accountNumber))
			message = "Account doesn't exist";
		else if(amount.intValue() < Constants.THRESHOLD_AMOUNT.intValue())
			if(transactionServiceImpl.depositMoney(amount, accountNumber))
				message = "Amount deposited successfully";
			else
				message = "Amount not deposited";
		else
			message = "You don't have authority to deposit amount greater than 1000";

		if (message != null)
			request.getSession().setAttribute("message", message);

		return new ModelAndView("redirect:/Tier1DepositMoney");
	}
	
	@RequestMapping(value = "/Tier1WithdrawMoney")
	public String withdrawAmount(HttpServletRequest request, Model model) {
		String message = getMessageFromSession("message", request.getSession());
		if (message != null)
			model.addAttribute("message", message);

		return "Tier1WithdrawMoney";
		
	}
	
	@RequestMapping(value = "/Tier1/WithdrawMoney")
	public ModelAndView tier1WithdrawMoney(HttpServletRequest request, @RequestParam(required = true, name="accountNumber") String accountNumber, @RequestParam(required = true, name="amount") BigDecimal amount) {
		AccountServicesImpl accountServicesImpl = new AccountServicesImpl();
		String message = null;
		
		if (!accountServicesImpl.doesAccountExists(accountNumber))
			message = "Account doesn't exist";
		else if (amount.intValue() < Constants.THRESHOLD_AMOUNT.intValue())
			if (transactionServiceImpl.withdrawMoney(amount, accountNumber))
				message = "Amount withdrawed successfully";
			else
				message = "Amount not withdrawed";
		else
			message = "You don't have authority to withdraw amount greater than 1000";

		if (message != null)
			request.getSession().setAttribute("message", message);

		return new ModelAndView("redirect:/Tier1WithdrawMoney");
	}

	@RequestMapping(value = "/Tier1UpdatePassword")
	public String updatePassword(HttpServletRequest request) {
		return "Tier1UpdatePassword";
	}
	
	@RequestMapping(value = "/Tier1CreateTransaction")
	public String createTransaction(HttpServletRequest request, Model model) {
		String message = getMessageFromSession("message", request.getSession());
		if (message != null)
			model.addAttribute("message", message);
		
		return "Tier1CreateTransaction";
		
	}
	
	@RequestMapping(value = "/Tier1/CreateTransaction")
	public ModelAndView tier1CreateTransaction(HttpServletRequest request, @RequestParam(required = true, name="fromAccountNumber") String fromAccountNumber, @RequestParam(required = true, name="toAccountNumber") String toAccountNumber, @RequestParam(required = true, name="amount") BigDecimal amount) {
		AccountServicesImpl accountServicesImpl = new AccountServicesImpl();
		HttpSession session = request.getSession();
		String message = null;
		
		if(!accountServicesImpl.doesAccountExists(fromAccountNumber))
			message = "From Account doesn't exist";

		else if(!accountServicesImpl.doesAccountExists(toAccountNumber))
			message = "To Account doesn't exist";

		else if(fromAccountNumber.equals(toAccountNumber))
			message = "From Account and To Account can't be same";

		else if(transactionServiceImpl.createTransaction(amount, fromAccountNumber, toAccountNumber))
			if(amount.intValue() < Constants.THRESHOLD_AMOUNT.intValue())
				message = "Transaction created successfully";
			else
				message = "Transaction pending approval";
		else
			message = "Transaction not created";

		if (message != null)
			session.setAttribute("message", message);

		return new ModelAndView("redirect:/Tier1CreateTransaction");
	}
	
	@RequestMapping(value = "/Tier1ViewAccounts")
	public String viewAccounts(HttpServletRequest request) {
		return "Tier1ViewAccounts";
	}
	
	@RequestMapping(value = "/Tier1/ViewAccounts")
	public ModelAndView viewAccounts(HttpServletRequest request, @RequestParam(required = true, name="accountNumber") String accountNumber) {
		
		AccountServicesImpl accountServicesImpl = new AccountServicesImpl();
		
		SearchForm searchForm = accountServicesImpl.getAccounts(accountNumber);
		
		if(searchForm == null)
			return new ModelAndView("Login");
		else
			if(searchForm.getSearchs().size()==0)
				return new ModelAndView("Tier1ViewAccounts" , "message", "An account not found");
			else
				return new ModelAndView("Tier1ViewAccounts" , "searchForm", searchForm);  

	}
}