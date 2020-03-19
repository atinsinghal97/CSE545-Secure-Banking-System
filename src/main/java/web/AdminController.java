package web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.hibernate.AdminId;
import com.example.hibernate.Customer;
import com.example.hibernate.CustomerId;
import com.example.hibernate.MerchantId;
import com.example.hibernate.SessionManager;
import com.example.hibernate.Tier1Id;
import com.example.hibernate.Tier2Id;
import com.example.hibernate.User;
import com.example.hibernate.UserDetails;
import com.example.hibernate.UserDetailsId;


@Controller
public class AdminController {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping("/adminDashboard")
    public String hello(final HttpServletRequest request, Model model) {
	    HttpSession session = request.getSession(false);
	    
	    if (session != null) {
		    Object msg = session.getAttribute("msg");
	        model.addAttribute("message", session.getAttribute("msg"));
	        if (msg != null)
	        	session.removeAttribute("msg");
	    }
        return "AdminDashboard";
    }

	@RequestMapping("/adminViewAccounts")
    public String registration(final HttpServletRequest request, Model model) {
		return "EmployeeView";
    }
	
	@RequestMapping("/adminCreateAccounts")
    public String registration(final HttpServletRequest request, Model model) {
		return "EmployeeInsert";
    }
	
	@RequestMapping("/adminModifyAccounts")
    public String registration(final HttpServletRequest request, Model model) {
		return "EmployeeUpdate";
    }
	
	@RequestMapping("/systemLogs")
    public String registration(final HttpServletRequest request, Model model) {
		return "SystemLogs";
    }
	
}