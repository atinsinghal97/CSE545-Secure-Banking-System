package web;

import java.math.BigDecimal;
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

import database.SessionManager;
import model.Account;
import model.Request;
import model.User;
import model.UserDetail;

@Controller
public class LoginController {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping("/login")
    public String hello(final HttpServletRequest request, Model model) {
	    HttpSession session = request.getSession(false);
	    
	    if (session != null) {
		    Object msg = session.getAttribute("msg");
	        model.addAttribute("message", session.getAttribute("msg"));
	        if (msg != null)
	        	session.removeAttribute("msg");
	    }
        return "Login";
    }

	@RequestMapping("/register")
    public String registration(final HttpServletRequest request, Model model) {
		return "RegistrationExternal";
    }
	
	@RequestMapping(value = "/externalregister", method = RequestMethod.POST)
    public ModelAndView register(
    		@RequestParam(required = true, name="designation") String userType,
    		@RequestParam(required = true, name="firstname") String firstname,
    		@RequestParam(required = true, name="middlename") String middlename,
    		@RequestParam(required = true, name="lastname") String lastname,
    		@RequestParam(required = true, name="username") String username,
    		@RequestParam(required = true, name="password") String password,
    		@RequestParam(required = true, name="email") String email,
    		@RequestParam(required = true, name="address") String address,
    		@RequestParam(required = true, name="phone") String phone,
    		@RequestParam(required = true, name="date_of_birth") String dateOfBirth,
    		@RequestParam(required = true, name="ssn") String ssn,
    		@RequestParam(required = true, name="secquestion1") String secquestion1,
    		@RequestParam(required = true, name="secquestion2") String secquestion2) {
		
		Session s = SessionManager.getSession("");
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			User user = new User();
			user.setUsername(username);
			user.setPassword(passwordEncoder.encode(password));
			user.setRole(userType);
			user.setStatus(0);
			s.save(user);
			UserDetail userDetail;
			Date date = new SimpleDateFormat("mm-dd-yyyy").parse(dateOfBirth);

			Integer uid = user.getId();
			System.out.println("UID AFTER SAVE: " + uid);
			userDetail = new UserDetail();
			userDetail.setUser(user);
			userDetail.setFirstName(firstname);
			userDetail.setMiddleName(middlename);
			userDetail.setLastName(lastname);
			userDetail.setEmail(email);
			userDetail.setPhone(phone);
			userDetail.setAddress1(address);
			userDetail.setAddress2("");
			userDetail.setCity("");
			userDetail.setDateOfBirth(date);
			userDetail.setProvince("");
			userDetail.setSsn(ssn);
			userDetail.setTier("");
			userDetail.setZip(100L);
			userDetail.setQuestion1(secquestion1);
			userDetail.setQuestion2(secquestion2);
			s.save(userDetail);
			
			Request r = new Request();
			r.setUser2(user);
			r.setTypeOfRequest("user_create");
			r.setApprovalLevelRequired("tier2");
			s.save(r);
			
			Account a = new Account();
			a.setUser(user);
			a.setAccountNumber("1234");
			a.setAccountType("savings");
			a.setStatus(0);
			a.setInterest(new BigDecimal(0.8));
			a.setCreatedDate(new Date());
			a.setCurrentBalance(new BigDecimal(100000));
			s.save(a);
			
			if (tx.isActive())
			    tx.commit();
			s.close();
		
		} catch (ParseException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		}
		finally {
			s.close();
		}
		
		return new ModelAndView("redirect:/login");
    }

	@RequestMapping("/homepage")
    public String home(final HttpServletRequest request, Model model) {
		
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(x.getName());
		return "CustomerDashboard";
    }
}
