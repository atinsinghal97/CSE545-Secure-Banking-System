package web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bankApp.application.Application;
import bankApp.repositories.UserDetailsImpl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import communication.Mailer;
import communication.Messager;
import database.SessionManager;
import forms.CustomerForm;
import forms.NewPassword;
import forms.PasswordChange;
import model.Account;
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
	    
	    String ipAddress = WebSecurityConfig.getClientIP(request);
	    if (ipAddress == null || ipAddress.isEmpty()) {
	    	session.setAttribute("msg", "Please try again later.");
	    	response.setViewName("redirect:/forgot_password");
	    }
	    
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

			    		otp = otpUtils.generateOtp(u, ipAddress, mode);
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
        WebSecurityConfig.forceLogout();
		
	    User u = otpUtils.validateOtp(otp, request.getRemoteHost());

	    if (u != null) {
	    	Authentication auth = new UsernamePasswordAuthenticationToken(
	          new UserDetailsImpl(u), null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
    	    SecurityContextHolder.getContext().setAuthentication(auth);
	    	return new ModelAndView("redirect:/change_password");
	    }
	    
        request.getSession().setAttribute("message", "Please request a new otp");
        return new ModelAndView("redirect:/login");
    }
	
    @RequestMapping(value = "/change_password", method = RequestMethod.GET)
    public String changePasswordPage(final HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object msg = session.getAttribute("msg");
            model.put("message", session.getAttribute("msg"));
            if (msg != null)
                session.removeAttribute("msg");
        }

        model.put("passwordForm", new PasswordChange());
        return "NewPassword";
    }

    @RequestMapping(value = "/change_password", method = RequestMethod.POST)
    public ModelAndView changePassword(final HttpServletRequest request, HttpServletResponse response,
    		@Valid @ModelAttribute("passwordForm") NewPassword passwordForm, BindingResult result,
            Map<String, Object> model) {
    	if (result.hasErrors()) {
    		return new ModelAndView("NewPassword");
    	}

        Session s = SessionManager.getSession("");
        Transaction tx = null;

        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            User user = userDetails.getUser();

            tx = s.beginTransaction();

            user.setPassword(passwordEncoder.encode(passwordForm.getPassword()));
            s.update(user);

            if (tx.isActive()) tx.commit();

        	request.getSession().setAttribute("msg", "Password changed successfully!");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        	request.getSession().setAttribute("msg", "Password not changed!");
            s.close();
        } finally {
            s.close();
        }

        WebSecurityConfig.forceLogout();
        return new ModelAndView("redirect:/login");
    }
	
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(Map<String, Object> model) {
        CustomerForm user = new CustomerForm();
        model.put("userForm", user);
        return "RegistrationExternal";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView doRegister(@Valid @ModelAttribute("userForm") CustomerForm userForm, BindingResult result,
            Map<String, Object> model) {

        if (result.hasErrors()) {
            return new ModelAndView("RegistrationExternal");
        }

        Session session = SessionManager.getSession("");
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            User user = userForm.createUser(passwordEncoder);
            session.save(user);
            session.save(user.getUserDetail());

            if (tx.isActive()) tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            model.put("message", "Unable to register. Please contact the bank.");
            return new ModelAndView("RegistrationExternal");
        } finally {
            session.close();
        }

        return new ModelAndView("redirect:/login");
    }

	@RequestMapping("/homepage")
    public String home(final HttpServletRequest request, Model model) {
		
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(x.getName());
		Session s = SessionManager.getSession("");
		List<User> user=null;
		user=s.createQuery("FROM User WHERE username = :username", User.class)
				.setParameter("username", x.getName()).getResultList();	
		List<Account> account = new ArrayList<Account>();
		Account dummy = new Account();
		dummy.setAccountNumber("1111111");
		dummy.setInterest(new BigDecimal("1.25"));
		dummy.setAccountType("checkings");
		dummy.setCurrentBalance(new BigDecimal("10000"));
		
		account.add(dummy);
		model.addAttribute("users",x.getName());
		System.out.print("here we go" + model.getAttribute("users"));
		model.addAttribute("checking",account);
		model.addAttribute("savings",account);
		model.addAttribute("creditcards",account);
		model.addAttribute("role",user.get(0).getRole());
		s.close();
		return "CustomerDashboard";
    }
}
