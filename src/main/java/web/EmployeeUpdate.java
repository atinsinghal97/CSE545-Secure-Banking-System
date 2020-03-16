package web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import database.SessionManager;
import model.Account;
import model.Request;
import model.User;
import model.UserDetail;





@Controller
public class EmployeeUpdate {
	
	
	@RequestMapping("/Update")
    public String updatePage(final HttpServletRequest request, Model model) {
	    HttpSession session = request.getSession(false);
//	    if (session != null) {
//		    Object msg = session.getAttribute("username");
//	        model.addAttribute("empusername", session.getAttribute("username"));
//	        if (msg != null)
//	        	session.removeAttribute("msg");
//	    }
	    
	    Session s = SessionManager.getSession("");
		User u = null;
	
			u = s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", "test").getSingleResult();
		
		System.out.println("USER: " + u.getUsername());
		
		Integer uid = u.getUserId();
		System.out.println(uid);
		
		UserDetail ud = new UserDetail();
		ud = s.createQuery("FROM UserDetail WHERE user_id = :uid", UserDetail.class)
				.setParameter("uid", uid).getSingleResult();
		model.addAttribute("empusername", "test");
		model.addAttribute("Email",ud.getEmail());
		model.addAttribute("FirstName",ud.getFirstName());
		model.addAttribute("LastName",ud.getLastName());
		
		
		
		
		
        return "EmployeeUpdate";
    }
	
	
	
	
}

