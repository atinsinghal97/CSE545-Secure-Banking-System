package web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Tier1DashboardController {
	
	@RequestMapping(value = "/Tier1Dashboard")
	public String dashboard(final HttpServletRequest request, HttpSession session, Model model) {
		return "Tier1Dashboard";
	}
	
	@RequestMapping(value = "/Tier1PendingTransactions")
	public String pendingTransactions(HttpServletRequest request, HttpSession session, Model model) {
		return "Tier1PendingTransactions";
	}
	
	@RequestMapping(value = "/IssueCheque")
	public String issueCheque(HttpServletRequest request, HttpSession session, Model model) {
		return "IssueCheque";
	}
	
	@RequestMapping(value = "/Tier1DepositMoney")
	public String depositAmount(HttpServletRequest request, HttpSession session, Model model) {
		return "Tier1DepositMoney";
	}
	
	@RequestMapping(value = "/Tier1WithdrawMoney")
	public String withdrawAmount(HttpServletRequest request, HttpSession session, Model model) {
		return "Tier1WithdrawMoney";
	}

	@RequestMapping(value = "/Tier1UpdatePassword")
	public String updatePassword(HttpServletRequest request, HttpSession session, Model model) {
		return "Tier1UpdatePassword";
	}
	
}
