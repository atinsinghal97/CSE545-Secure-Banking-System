package web;

import java.math.BigDecimal;
import java.text.ParseException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import constants.Constants;
import forms.TransactionSearchForm;

@Controller
public class Tier1DashboardController extends HttpServlet {
	
	@RequestMapping(value = "/Tier1Dashboard")
	public String dashboard(final HttpServletRequest request) {
		return "Tier1Dashboard";
		
	}
	
	@RequestMapping(value = "/Tier1PendingTransactions")
	public ModelAndView tier1PendingTransactions(HttpServletRequest request) {
		TransactionServicesImpl transactionService = new TransactionServicesImpl();
		TransactionSearchForm transactionSearchForm = transactionService.getPendingTransactions();
		if(transactionSearchForm==null)
			return new ModelAndView("/Tier1PendingTransactions","message","No Pending Transactions");

		else {
			return new ModelAndView("/Tier1PendingTransactions", "transactionSearchForm", transactionSearchForm);
		}
		
	}
	
	@RequestMapping(value = "/Tier1/AuthorizeTransaction", method = RequestMethod.POST)
    public ModelAndView tier1AuthorizeTransaction(HttpServletRequest request, @RequestParam(required = true, name="id") int id, @RequestParam(required = true, name="fromAccountNumber") String fromAccountNumber, @RequestParam(required = true, name="toAccountNumber") String toAccountNumber, @RequestParam(required = true, name="id") BigDecimal amount) throws ParseException {
		
		TransactionServicesImpl transactionServicesImpl = new TransactionServicesImpl();
		
		if(transactionServicesImpl.approveTransactions(id))
			return new ModelAndView("redirect:/Tier1PendingTransactions");  
		
		else
			return new ModelAndView("/Tier1PendingTransactions","message","Transaction can't be approved");
        
    }
	
	@RequestMapping(value = "/Tier1/DeclineTransaction", method = RequestMethod.POST)
    public ModelAndView tier1DeclineTransaction(HttpServletRequest request, @RequestParam(required = true, name="id") int id, @RequestParam(required = true, name="fromAccountNumber") String fromAccountNumber, @RequestParam(required = true, name="toAccountNumber") String toAccountNumber, @RequestParam(required = true, name="id") BigDecimal amount) throws ParseException {
		
		TransactionServicesImpl transactionServicesImpl = new TransactionServicesImpl();
		
		if(transactionServicesImpl.declineTransactions(id))
			return new ModelAndView("redirect:/Tier1PendingTransactions");  
		
		else
			return new ModelAndView("/Tier1PendingTransactions","message","Transaction doesn't exist");
        
    }
	
	@RequestMapping(value = "/Tier1IssueCheque")
	public String issueCheque(HttpServletRequest request, HttpSession session, Model model) {
		return "Tier1IssueCheque";
		
	}
	
	@RequestMapping(value = "/Tier1/IssueCheque")
	public ModelAndView tier1IssueCheque(HttpServletRequest request, @RequestParam(required = true, name="accountNumber") String accountNumber, @RequestParam(required = true, name="amount") BigDecimal amount){
		TransactionServicesImpl transactionService = new TransactionServicesImpl();
		
		AccountServicesImpl accountServicesImpl = new AccountServicesImpl();
		
		if(!accountServicesImpl.doesAccountExists(accountNumber))
			return new ModelAndView("Tier1IssueCheque","message","Account doesn't exist");

		if(amount.intValue() <= Constants.THRESHOLD_AMOUNT.intValue()) {
			if(transactionService.issueCheque(amount, accountNumber))
					return new ModelAndView("Tier1IssueCheque","message","The Cheque was issued successfully");
			else
				return new ModelAndView("Tier1IssueCheque","message","The Cheque was not issued");
		}
		else {
			return new ModelAndView("Tier1IssueCheque","message","You don't have authority to issue cheque of amount greater than 1000");
		}		
	}
	
	@RequestMapping(value = "/Tier1DepositCheque")
	public String depositCheque(HttpServletRequest request, HttpSession session, Model model) {
		return "Tier1DepositCheque";
		
	}
	
	
	@RequestMapping(value = "/Tier1/DepositCheque")
	public ModelAndView tier1DepositCheque(HttpServletRequest request, @RequestParam(required = true, name="chequeId") int chequeId, @RequestParam(required = true, name="accountNumber") String accountNumber, @RequestParam(required = true, name="amount") BigDecimal amount) {
		TransactionServicesImpl transactionService = new TransactionServicesImpl();
		if(!transactionService.doesTransactionExists(chequeId, "cc"))
			return new ModelAndView("Tier1DepositCheque","message","Cheque doesn't exist");

		AccountServicesImpl accountServicesImpl = new AccountServicesImpl();
		
		if(!accountServicesImpl.doesAccountExists(accountNumber))
			return new ModelAndView("Tier1DepositCheque","message","Account doesn't exist");

		if(amount.intValue() <= Constants.THRESHOLD_AMOUNT.intValue()) {
			if(transactionService.depositCheque(chequeId, amount, accountNumber))
				return new ModelAndView("Tier1DepositCheque","message","The Cheque was deposited successfully");
			else
				return new ModelAndView("Tier1DepositCheque","message","The Cheque was not deposited due to incorrect information");
		}
		else {
			return new ModelAndView("Tier1DepositCheque","message","You don't have authority to deposit cheque of amount greater than 1000");
		}		
	}
	
	@RequestMapping(value = "/Tier1DepositMoney")
	public String depositAmount(HttpServletRequest request) {
		return "Tier1DepositMoney";
		
	}
	
	
	@RequestMapping(value = "/Tier1/DepositMoney")
	public ModelAndView tier1DepositMoney(HttpServletRequest request,@RequestParam(required = true, name="accountNumber") String accountNumber, @RequestParam(required = true, name="amount") BigDecimal amount) {
		TransactionServicesImpl transactionService = new TransactionServicesImpl();
		
		AccountServicesImpl accountServicesImpl = new AccountServicesImpl();
		
		if(!accountServicesImpl.doesAccountExists(accountNumber))
			return new ModelAndView("Tier1DepositMoney","message","Account doesn't exist");

		
		if(amount.intValue() <= Constants.THRESHOLD_AMOUNT.intValue()) {
			if(transactionService.depositMoney(amount, accountNumber))
				return new ModelAndView("Tier1DepositMoney","message","Amount deposited successfully");
			else
				return new ModelAndView("Tier1DepositMoney","message","Amount not deposited");
		}
		else {
			return new ModelAndView("Tier1DepositMoney","message","You don't have authority to deposit amount greater than 1000");
		}		
	}
	
	@RequestMapping(value = "/Tier1WithdrawMoney")
	public String withdrawAmount(HttpServletRequest request) {
		return "Tier1WithdrawMoney";
		
	}
	
	@RequestMapping(value = "/Tier1/WithdrawMoney")
	public ModelAndView tier1WithdrawMoney(HttpServletRequest request, @RequestParam(required = true, name="accountNumber") String accountNumber, @RequestParam(required = true, name="amount") BigDecimal amount) {
		TransactionServicesImpl transactionService = new TransactionServicesImpl();
		
		AccountServicesImpl accountServicesImpl = new AccountServicesImpl();
		
		if(!accountServicesImpl.doesAccountExists(accountNumber))
			return new ModelAndView("Tier1WithdrawMoney","message","Account doesn't exist");

		
		if(amount.intValue() <= Constants.THRESHOLD_AMOUNT.intValue()) {
			if(transactionService.withdrawMoney(amount, accountNumber))
				return new ModelAndView("Tier1WithdrawMoney","message","Amount withdrawed successfully");
			else
				return new ModelAndView("Tier1WithdrawMoney","message","Amount not withdrawed");
		}
		else {
			return new ModelAndView("Tier1WithdrawMoney","message","You don't have authority to withdraw amount greater than 1000");
		}		
	}

	@RequestMapping(value = "/Tier1UpdatePassword")
	public String updatePassword(HttpServletRequest request) {
		return "Tier1UpdatePassword";
		
	}
	
	@RequestMapping(value = "/Tier1CreateTransaction")
	public String createTransaction(HttpServletRequest request) {
		return "Tier1CreateTransaction";
		
	}
	
	@RequestMapping(value = "/Tier1/CreateTransaction")
	public ModelAndView tier1CreateTransaction(HttpServletRequest request, @RequestParam(required = true, name="fromAccountNumber") String fromAccountNumber, @RequestParam(required = true, name="toAccountNumber") String toAccountNumber, @RequestParam(required = true, name="amount") BigDecimal amount) {
		TransactionServicesImpl transactionService = new TransactionServicesImpl();
		
		AccountServicesImpl accountServicesImpl = new AccountServicesImpl();
		
		if(!accountServicesImpl.doesAccountExists(fromAccountNumber))
			return new ModelAndView("Tier1CreateTransaction","message","From Account doesn't exist");

		if(!accountServicesImpl.doesAccountExists(toAccountNumber))
			return new ModelAndView("Tier1CreateTransaction","message","To Account doesn't exist");

		if(fromAccountNumber.equals(toAccountNumber))
			return new ModelAndView("Tier1CreateTransaction","message","From Account and To Account can't be same");
		
		if(amount.intValue() <= Constants.THRESHOLD_AMOUNT.intValue()) {
			if(transactionService.createTransaction(amount, fromAccountNumber, toAccountNumber))
				return new ModelAndView("Tier1CreateTransaction","message","Transaction created successfully");
			else
				return new ModelAndView("Tier1CreateTransaction","message","Transaction not created");
		}
		else {
			return new ModelAndView("Tier1CreateTransaction","message","You don't have authority to create transaction having amount greater than 1000");
		}		
	}
	
	@RequestMapping(value = "/Tier1ViewAccounts")
	public String viewAccounts(HttpServletRequest request) {
		return "Tier1ViewAccounts";
		
	}
	
	@RequestMapping(value = "/Logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
		    session.invalidate();
		    session.setMaxInactiveInterval(1); 
		}
		return new ModelAndView("/Logout");
		
	}
}
