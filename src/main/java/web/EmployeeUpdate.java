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
import model.User;
import model.UserDetail;





@Controller
public class EmployeeUpdate {
	
	
	@RequestMapping("/Update")
    public String updatePage(final HttpServletRequest request, Model model) {
	    HttpSession session = request.getSession(false);

	   //Should Uncomment it after user creation and http session has a username to get the data for a customer
	    
//	    Session s = SessionManager.getSession("");
//		User u = null;
//	
//			u = s.createQuery("FROM User WHERE username = :username", User.class)
//					.setParameter("username", "test").getSingleResult();
//		
//		System.out.println("USER: " + u.getUsername());
//		
//		Integer uid = u.getUserId();
//		//System.out.println(uid);
//		
//		UserDetail ud = new UserDetail();
//		ud = s.createQuery("FROM UserDetail WHERE user_id = :uid", UserDetail.class)
//				.setParameter("uid", uid).getSingleResult();
//		model.addAttribute("empusername", "test");
//		model.addAttribute("Email",ud.getEmail());
//		model.addAttribute("FirstName",ud.getFirstName());
//		model.addAttribute("LastName",ud.getLastName());
		
		
		
		
		
        return "EmployeeUpdate";
    }
	
	@RequestMapping(value = "/Search", method = RequestMethod.POST)
    public String updateSearch(final HttpServletRequest request, Model model) {
		String username=request.getParameter("username_search");
//	    HttpSession session = request.getSession(false);
//	    String h=request.getParameter("emp_update_search");

//	    if (session != null) {
//		    Object msg = session.getAttribute("username");
//	        model.addAttribute("empusername", session.getAttribute("username"));
//	        if (msg != null)
//	        	session.removeAttribute("msg");
//	    }

//	    
	    Session s = SessionManager.getSession("");
		User u = null;
	
		
			u=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", username).getSingleResult();
			
		System.out.println("USER: " + u.getUsername());
	
			Integer uid = u.getId();
		
		System.out.println(uid);
		
		UserDetail ud = new UserDetail();
		ud = s.createQuery("FROM UserDetail WHERE user_id = :uid", UserDetail.class)
				.setParameter("uid", uid).getSingleResult();
		model.addAttribute("empusername", username);
		model.addAttribute("Email",ud.getEmail());
		model.addAttribute("FirstName",ud.getFirstName());
		model.addAttribute("LastName",ud.getLastName());
		model.addAttribute("MiddleName",ud.getMiddleName());
		model.addAttribute("Phone",ud.getPhone());
		model.addAttribute("DOB",ud.getDateOfBirth());
		
		
		
		
		
		
		
        return "EmployeeUpdate";
    }
	
	@RequestMapping(value = "/ChangeValue", method = RequestMethod.POST)
    public ModelAndView changeValue(final HttpServletRequest request, Model model)  {
		System.out.println(request);
		String username=request.getParameter("empusername");
		String email=request.getParameter("email");
		String firstName=request.getParameter("firstname");
		String lastName=request.getParameter("lastname");
		String middleName=request.getParameter("middlename");
		String phone=request.getParameter("phone");
		//String dateOfBirth=request.getParameter("DOB");
		
		System.out.println(username);
		System.out.println(email);
		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(middleName);
		System.out.println(phone);
		//System.out.println(dateOfBirth);
		
		
		//Date date = new SimpleDateFormat("mm-dd-yyyy").parse(dateOfBirth);
		
		 Session s = SessionManager.getSession("");
		 
		 User u = null;
		
			u=s.createQuery("FROM User WHERE username = :username", User.class)
					.setParameter("username", username).getSingleResult();
			
			System.out.println("USER: " + u.getUsername());
			
			
	
		
			Integer uid = u.getId();
		
		System.out.println(uid);
		Transaction tx = null;
		tx = s.beginTransaction();
		UserDetail ud = new UserDetail();
		ud = s.createQuery("FROM UserDetail WHERE user_id = :uid", UserDetail.class)
		.setParameter("uid", uid).getSingleResult();
		
		ud.setEmail(email);
		ud.setFirstName(firstName);
		ud.setLastName(lastName);
		ud.setMiddleName(middleName);
		ud.setPhone(phone);
		//ud.setDateOfBirth(date);
		System.out.println(ud.getEmail());
	
		
		s.saveOrUpdate(ud);
		
		
		

		if (tx.isActive())
		    tx.commit();
		s.close();
    
		
	   
			

		
		
		
		
		
		
		return new ModelAndView("redirect:/homepage");
    }
	
	
	
	
}

