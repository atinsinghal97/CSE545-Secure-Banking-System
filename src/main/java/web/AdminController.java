package web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class AdminController {
	
	@RequestMapping("/AdminDashboard")
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

	@RequestMapping("/EmployeeView")
    public String employeeView(final HttpServletRequest request, Model model) {
		return "EmployeeView";
    }
	
	@RequestMapping("/EmployeeInsert")
    public String employeeInsert(final HttpServletRequest request, Model model) {
		return "EmployeeInsert";
    }
	
	@RequestMapping("/EmployeeUpdate")
    public String employeeUpdate(final HttpServletRequest request, Model model) {
		return "EmployeeUpdate";
    }
	
	@RequestMapping("/EmployeeDelete")
    public String employeeDelete(final HttpServletRequest request, Model model) {
		return "EmployeeDelete";
    }
	
	@RequestMapping("/SystemLogs")
    public String systemLogs(final HttpServletRequest request, Model model) {
		return "SystemLogs";
    }
	
}