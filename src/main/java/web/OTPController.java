package web;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import bankApp.application.Application;
import bankApp.repositories.UserDetailsImpl;
import communication.Mailer;
import database.SessionManager;
import model.User;
import model.UserDetail;


@Controller
public class OTPController {
    @Resource(name = "mailer")
    private Mailer mailer;
	
	OTPservice otpservice = new OTPservice();

	@RequestMapping(value ="/generateAccountOtp", method = RequestMethod.GET)
	public ModelAndView generateLoginOtp(HttpServletRequest request, HttpSession session){
		ModelMap model = new ModelMap();
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		
		Object validOtp = session.getAttribute("OtpValid");
		if (validOtp != null && (validOtp instanceof Integer)) {
			model.put("message", "Please wait for 5 minutes before requesting another OTP.");
			return new ModelAndView("OtpPage", model);
		}
		
		Integer oldOtp = otpservice.getOTP(x.getName());

		if (oldOtp != null && oldOtp != 0) {
			model.put("message", "Please wait for 5 minutes before requesting another OTP.");
			return new ModelAndView("OtpPage", model);
		}

		Integer otp = otpservice.generateNewOTP(x.getName());
		System.out.print("otp"+ otp);
		model.put("message", "An otp has been sent to the registered email.");
		
    	Application.executorService.submit(() -> {
    		Session sess = SessionManager.getSession("");
    		
    		try {
        		User user = sess.createQuery("FROM User WHERE username = :username AND status = 1", User.class)
            			.setParameter("username", x.getName())
            			.getSingleResult();
        		UserDetail details = user.getUserDetail();
    			
	    		System.out.println("Sending Email...");
		    	mailer.sendEmail(details.getEmail(),
		    			"BigPPBank: Your OTP for appointment", otp.toString());
				System.out.println("Sent!");
    			
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    		sess.close();
    	});

		return new ModelAndView("OtpPage", model);
	}
	@RequestMapping(value ="/generateAppointmentOtp", method = RequestMethod.GET)
	public ModelAndView generateAppointment(HttpServletRequest request, HttpSession session){
		ModelMap model = new ModelMap();
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		
		Object validOtp = session.getAttribute("OtpValid");
		if (validOtp != null && (validOtp instanceof Integer)) {
			model.put("message", "Please wait for 5 minutes before requesting another OTP.");
			return new ModelAndView("OtpPage", model);
		}
		
		Integer oldOtp = otpservice.getOTP(x.getName());

		if (oldOtp != null && oldOtp != 0) {
			model.put("message", "Please wait for 5 minutes before requesting another OTP.");
			return new ModelAndView("OtpPageForAppointment", model);
		}

		Integer otp = otpservice.generateNewOTP(x.getName());
		System.out.print("otp"+ otp);
		
		model.put("message", "An otp has been sent to the registered email.");
		
    	Application.executorService.submit(() -> {
    		Session sess = SessionManager.getSession("");
    		
    		try {
        		User user = sess.createQuery("FROM User WHERE username = :username AND status = 1", User.class)
        			.setParameter("username", x.getName())
        			.getSingleResult();
        		UserDetail details = user.getUserDetail();
    			
	    		System.out.println("Sending Email...");
		    	mailer.sendEmail(details.getEmail(),
		    			"BigPPBank: Your OTP for appointment", otp.toString());
				System.out.println("Sent!");
    			
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    		sess.close();
    	});
		
		return new ModelAndView("OtpPageForAppointment",model);
	}
	
	@RequestMapping(value ="/validateOtp", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> validateOtp(@RequestParam("otpnum") int otpnumber, HttpSession session){
		Authentication x = SecurityContextHolder.getContext().getAuthentication();
		Integer otprecieved = otpservice.getOTP(x.getName());
		final ResponseEntity<String> SUCCESS = ResponseEntity.status(200).body("Valid OTP");
		final ResponseEntity<String> FAILURE = ResponseEntity.status(405).body("Invalid OTP. Please try again!");
		if(otpnumber==otprecieved) {
			session.setAttribute("OtpValid", otprecieved);
			otpservice.removeOtp(x.getName());
			
			return SUCCESS;
		}
		return FAILURE;
			
	}
}