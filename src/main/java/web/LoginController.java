package web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bankApp.application.Application;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import communication.Mailer;
import communication.Messager;
import database.SessionManager;
import model.Otp;
import model.User;
import model.UserDetail;
import security.OtpUtils;

@Controller
public class LoginController {
	@Value("${app.url}")
	private String appUrl;
	
	@Resource(name = "otpUtils")
	private OtpUtils otpUtils;
	
	@Resource(name = "mailer")
	private Mailer mailer;
	
	@Resource(name = "messager")
	private Messager messager;
	
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

	@RequestMapping("/forgot_password")
	public ModelAndView forgotPassword(
			final HttpServletRequest request,
    		@RequestParam(required = false, name="username") String username,
    		@RequestParam(required = false, name="mode") Integer mode) {
	    ModelAndView response = new ModelAndView();
	    Map<String, Object> model = response.getModel();
	    
		if (username == null) {
		    HttpSession session = request.getSession(false);
		    
		    if (session != null) {
			    Object msg = session.getAttribute("msg");
		        model.put("message", (String) msg);
		        if (msg != null)
		        	session.removeAttribute("msg");
		    }
		    
		    response.setViewName("ForgotPassword");
			return response;
		}

	    HttpSession session = request.getSession(false);
	    
	    Session s = null;
	    
	    try {
	    	s = SessionManager.getSession("");

		    User u = s.createQuery("FROM User WHERE username = :username", User.class)
		    		.setParameter("username", username)
		    		.getSingleResult();
		    
		    if (u != null) {
		    	Boolean emailPending = s.createNamedQuery("FindPendingTransactionsByUser", Boolean.class)
		    		.setParameter("user_id", u.getId())
		    		.setParameter("offset", OtpUtils.EXPIRE_TIME)
		    		.uniqueResult();

		    	if (emailPending) {
		    		model.put("message", "Password reset instructions has already been sent to the email/phone associated with this account. Please wait for some more time before requesting another reset link.");
				    response.setViewName("ForgotPassword");
		    		return response;
		    	}
		    	
		    	UserDetail details = u.getUserDetail();
		    	// Don't wait, ask them to request again later
		    	Application.executorService.submit(() -> {
		    		Session sess = SessionManager.getSession("");
		    		Transaction tx = null;
		    		Otp otp = null;
		    		
		    		try {
		    			tx = sess.beginTransaction();

			    		otp = otpUtils.generateOtp(u, request.getRemoteHost(), mode);
			    		sess.save(otp);
			    		
			    		if (mode == 1) {
				    		System.out.println("Sending Email...");
					    	mailer.sendEmail(details.getEmail(),
					    			"BigPPBank: Your link to reset your password.",
					    			appUrl + "/reset_password?token=" + otp.getOtpKey());
			    		} else if (mode == 0) {
				    		System.out.println("Sending Message...");
				    		messager.sendSms(details.getPhone(), otp.getOtpKey());
			    		}
						System.out.println("Sent!");
		    			
		    			if (tx.isActive())
		    			    tx.commit();
		    			
		    		} catch (Exception e) {
		    			e.printStackTrace();
		    			if (tx != null) tx.rollback();
		    		} finally {
		    			sess.close();
		    		}
		    		
		    	});

		    }
		    
	    } catch (NoResultException e) {
	    	// Let this through
	    	e.printStackTrace();
	    } catch (Exception e) {
			e.printStackTrace();
		} finally {		    
		    s.close();
	    }
	    

	    if (mode == 0) {
	    	model.put("message", "If your username exists in the database, you'll recieve a message to reset your password.");
		    response.setViewName("ForgotPassword");
	    } else {
		    session.setAttribute("msg", "If your username exists in the database, you'll recieve a message to reset your password.");
	    	response.setViewName("redirect:/login");
	    }

	    return response;
    }
	
	@RequestMapping("/reset_password")
	public ModelAndView resetPassword(
			final HttpServletRequest request,
			Model model,
    		@RequestParam(required = true, name="token") String otp) {
		// Close and open link again?
		// We have to unset the session status
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			auth.setAuthenticated(false);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		
	    User u = otpUtils.validateOtp(otp, request.getRemoteHost());

	    if (u != null) {
	        auth = new UsernamePasswordAuthenticationToken(
	          u, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
    	    SecurityContextHolder.getContext().setAuthentication(auth);
	    	return new ModelAndView("redirect:/change_password");
	    }
	    
	    System.out.println("NULL!");
		return new ModelAndView("redirect:/login");
    }

	@RequestMapping("/change_password")
    public ModelAndView changePassword(final HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(required = false, name="newpassword") String newpassword,
    		@RequestParam(required = false, name="confirmpassword") String confirmpassword) {
		if (newpassword == null || confirmpassword == null) {
			return new ModelAndView("NewPassword");
		}
		
		User user = (User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
		Session s = SessionManager.getSession("");
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
		    user.setPassword(passwordEncoder.encode(newpassword));
		    s.update(user);
			if (tx.isActive())
			    tx.commit();
		
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			s.close();
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		auth.setAuthenticated(false);
		SecurityContextHolder.getContext().setAuthentication(null);
		return new ModelAndView("redirect:/login");
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
			
			// Requires validation
			Date date = new SimpleDateFormat("mm-dd-yyyy").parse(dateOfBirth);

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
			userDetail.setZip(100L);
			userDetail.setQuestion1(secquestion1);
			userDetail.setQuestion2(secquestion2);
			s.save(userDetail);
			
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
